package netcracker.edu.ishop.api.persistence;

import com.google.gson.*;

import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.commands.engine.CommandEngine;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DAOInMemoryJSON<T extends AbstractBusinessObject> extends DAO<T> {

    /*
    Check out for JSON serialization (bi-directional) of Java objects.
    It's faster than the JDK's ObjectInputStream and ObjectOutputStream, and it's
    format is human readable JSON. JSON does not require your objects to
    implement 'Serializable'. This is a major limitation to the JDK's
    serialization because it limits it's usefulness when you do not control the
    code for all the classes of objects you wish to serialize.
    */

    public static final Logger log = Logger.getLogger(DAOInMemoryJSON.class);

    private Map<Class, Map<BigInteger, T>> dataMap;
    private Map<Class<T>, Map<BigInteger, T>> dataMapTest;

    public DAOInMemoryJSON() {

        this.dataMap = new HashMap<>();

        Map userMap = jsonDeserializeBObjectByType(User.class, SerializationConstants.SERIALIZED_USERMAP_FILE_PATH);
        if (userMap != null) {
            dataMap.put(User.class, userMap);
        } else {
            dataMap.put(User.class, new HashMap<BigInteger, T>());
        }

        Map folderMap = jsonDeserializeBObjectByType(Folder.class, SerializationConstants.SERIALIZED_FOLDERMAP_FILE_PATH);
        if (folderMap != null) {
            dataMap.put(Folder.class, folderMap);
        } else {
            dataMap.put(Folder.class, new HashMap<BigInteger, T>());
        }

        Map itemMap = jsonDeserializeBObjectByType(Item.class, SerializationConstants.SERIALIZED_ITEMMAP_FILE_PATH);
        if (itemMap != null) {
            dataMap.put(Item.class, itemMap);
        } else {
            dataMap.put(Item.class, new HashMap<BigInteger, T>());
        }

        Map orderMap = jsonDeserializeBObjectByType(Order.class, SerializationConstants.SERIALIZED_ORDERMAP_FILE_PATH);
        if (orderMap != null) {
            dataMap.put(Order.class, orderMap);
        } else {
            dataMap.put(Order.class, new HashMap<BigInteger, T>());
        }


        //dataMapTest = jsonDeserializeEntireDataStructure(SerializationConstants.SERIALIZED_DATAMAP_FILE_PATH);
        log.info(dataMapTest);


    }

//    public T create(Class<T> abObjType) {
//
//        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();
//
//        if (User.class.isAssignableFrom(abObjType)) {
//            BigInteger id = UIDGenerator.getID();
//            User user = new User(id);
//            //log.info(user.toString());
//            return (T) user;
//        }
//
//        if (Folder.class.isAssignableFrom(abObjType)) {
//            BigInteger id = UIDGenerator.getID();
//            Folder folder = new Folder(id);
//            //log.info(folder.toString());
//            return (T) folder;
//        }
//
//        if (Item.class.isAssignableFrom(abObjType)) {
//            BigInteger id = UIDGenerator.getID();
//            Item item = new Item(id);
//            //log.info(item.toString());
//            return (T) item;
//        }
//
//        if (Order.class.isAssignableFrom(abObjType)) {
//            BigInteger id = UIDGenerator.getID();
//            Order order = new Order(id);
//            //log.info(order.toString());
//            return (T) order;
//        }
//
//        if (abObjType == null) {
//            throw new NullPointerException("Can't create instance for:" + abObjType);
//        }
//        return null;
//    }


    public T create(Class<T> abObjType) {
        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();

        if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
            return spawnBusinessObjectInstanceByReflection(abObjType, UIDGenerator.getID());
        }
        return null;
    }

    private T spawnBusinessObjectInstanceByReflection(Class<T> abObjType, BigInteger newId) {
        T newBusinessObjectInstance = null;
        try {
            Constructor<T> constructor = abObjType.getConstructor(BigInteger.class);
            newBusinessObjectInstance = constructor.newInstance(newId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException exceptionMessage) {
            log.info(exceptionMessage);
        }
        return newBusinessObjectInstance;
    }


    @Override
    public T load() {
        return null;
    }


    @Override
    public void save(T abObj) {
        try {
            Map mapShard = dataMap.get(abObj.getClass());
            if (mapShard != null) {

//                if (User.class.isAssignableFrom(abObj.getClass())) {
//                    for ()
//
//                }
                //log.info(mapShard.getClass().getName());
                //log.info(mapShard.keySet().iterator().next().getClass().getName());
                //log.info(mapShard.values().iterator().next().getClass().getName());
                //log.info(mapShard);
                //log.info(abObj.getId());
                mapShard.put(abObj.getId(), abObj);
                //log.info(mapShard);
            } else {
                log.info("It seems that main serialized data structure was broken during serialization or loading");
            }
            //log.info(Arrays.toString(mapShard.entrySet().toArray()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void delete(T aboObj) {
        Map shardMap;
        if (User.class.isAssignableFrom(aboObj.getClass())) {
            shardMap = dataMap.get(User.class);
            shardMap.remove(aboObj.getId());
        }

        if (Item.class.isAssignableFrom(aboObj.getClass())) {
            shardMap = dataMap.get(Item.class);
            shardMap.remove(aboObj.getId());
        }

        if (Folder.class.isAssignableFrom(aboObj.getClass())) {
            shardMap = dataMap.get(Folder.class);
            shardMap.remove(aboObj.getId());
        }

        if (Order.class.isAssignableFrom(aboObj.getClass())) {
            shardMap = dataMap.get(Order.class);
            shardMap.remove(aboObj.getId());
        }


    }

    @Override
    public Map getMapShardByABOName(Class<T> abObjType) {
        //log.info(abObjType);
        //log.info(dataMap.get(abObjType));
        return dataMap.get(abObjType);
    }


    public User findUserByName(String userName) {

        Map userMap = dataMap.get(User.class);
        for (Iterator<User> userIter = userMap.values().iterator(); userIter.hasNext(); ) {
            User user = userIter.next();
            if (user.getName().equals(userName.toLowerCase())) {
                //foundFlag = true;
                return user;
            }
        }

//        if (!foundFlag) {
//                log.info("findUserByName didn't find any user with given username " + userName);
//        }
        return null;
    }


    @Override
    public void DAOExit() {

        /*
        Serialization.
        Have to serialize following types:
        ***
        User.class
        Folder.class
        Item.class
        Order.class
        ***
        */

        jsonSerializeBObjectByType(User.class, SerializationConstants.SERIALIZED_USERMAP_FILE_PATH);
        jsonSerializeBObjectByType(Folder.class, SerializationConstants.SERIALIZED_FOLDERMAP_FILE_PATH);
        jsonSerializeBObjectByType(Item.class, SerializationConstants.SERIALIZED_ITEMMAP_FILE_PATH);
        jsonSerializeBObjectByType(Order.class, SerializationConstants.SERIALIZED_ORDERMAP_FILE_PATH);

        //here we serialize last ID number to use it as initial value for next launch of the application
        saveLastID();

        //test: entire json serialization\deserialization
        //jsonSerializeEntireDataStructure(SerializationConstants.SERIALIZED_DATAMAP_FILE_PATH);


        //not cool, each instantiation of CommandEngine creates DAO-instance for himself (should dao be singleton in threaded app?)
        CommandEngine comEngine = CommandEngine.getInstance();

        for (Iterator<User> userIter = CurrentSessionState.getAllSignedInUsers().iterator(); userIter.hasNext(); ) {
            User user = userIter.next();
            try {
                comEngine.executeCommand("sign_out " + user.getName());
            } catch (AccessDeniedException ADE) {
                log.info(ADE);
            }

        }


    }

    private void jsonSerializeBObjectByType(Class abObjType, String serializedFileName) {
        Map mapShard = dataMap.get(abObjType);

        Type mapType = new TypeToken<Map<BigInteger, T>>() {
        }.getType();

        Gson gson = new Gson();
        String json = gson.toJson(mapShard, mapType);
        try {
            FileWriter writer = new FileWriter(serializedFileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void jsonSerializeEntireDataStructure(String serializedFileName) {

        Type mapType = new TypeToken<Map<Class<T>, Map<BigInteger, T>>>() {}.getType();

        Gson gson = new Gson();

        String json = gson.toJson(dataMap, mapType);
        try {
            FileWriter writer = new FileWriter(serializedFileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Map jsonDeserializeBObjectByType(Class abObjType, String serializedFileName) {
        File serializedObjectFile = new File(serializedFileName);
        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
            Gson gson = new Gson();
            try {

                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));

                if (User.class.isAssignableFrom(abObjType)) {
                    Type typeOfMap = new TypeToken<Map<BigInteger, User>>() {
                    }.getType();
                    Map<BigInteger, User> mapShard = gson.fromJson(br, typeOfMap);
                    return mapShard;
                }

                if (Folder.class.isAssignableFrom(abObjType)) {
                    Type typeOfMap = new TypeToken<Map<BigInteger, Folder>>() {
                    }.getType();
                    Map<BigInteger, Folder> mapShard = gson.fromJson(br, typeOfMap);
                    return mapShard;
                }


                if (Item.class.isAssignableFrom(abObjType)) {
                    Type typeOfMap = new TypeToken<Map<BigInteger, Item>>() {
                    }.getType();
                    Map<BigInteger, Item> mapShard = gson.fromJson(br, typeOfMap);
                    return mapShard;
                }

                if (Order.class.isAssignableFrom(abObjType)) {
                    Type typeOfMap = new TypeToken<Map<BigInteger, Order>>() {
                    }.getType();
                    Map<BigInteger, Order> mapShard = gson.fromJson(br, typeOfMap);
                    return mapShard;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }




//    private Map jsonDeserializeBObjectByType2(T aboObj, String serializedFileName) {
//        File serializedObjectFile = new File(serializedFileName);
//        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
//            Gson gson = new Gson();
//            try {
//                Class c = aboObj.getClass();
//                Class<?> cl = Class.forName(aboObj.getClass().getSimpleName());
//                String name = aboObj.getClass().getSimpleName();
//
//                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));
//
//                if (AbstractBusinessObject.class.isAssignableFrom(c)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, name>>() {}.getType();
//                    Map<BigInteger, cl> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return null;
//    }


    private Map jsonDeserializeEntireDataStructure(String serializedFileName) {



        Map<Class<T>, Map<BigInteger, T>> emptyDataMap = new HashMap<>();
        File serializedObjectFile = new File(serializedFileName);
        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
            //GsonBuilder gsonBuilder = new GsonBuilder();
            ///gsonBuilder.registerTypeAdapter(, );
            Gson gson = new Gson();
            try {

                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));
                Type mapType = new TypeToken<Map<Class<T>, Map<BigInteger, T>>>(){}.getType();

                Map<Class<T>, Map<BigInteger, T>> dataMap = gson.fromJson(br, mapType);
                return dataMap;


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return emptyDataMap;
    }


    private void saveLastID() {
        BigInteger lastID = UniqueIDGenerator.getInstance().getID();
        log.info("Last unused ID number:" + lastID);
        Gson gson = new Gson();
        Type varType = new TypeToken<BigInteger>() {
        }.getType();
        String jsonLastID = gson.toJson(lastID, varType);

        try {
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_LAST_ID_FILE_PATH);
            writer.write(jsonLastID);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//
//    public class TAdapter implements JsonSerializer<T> {
//        @Override
//        public JsonElement serialize(T t, Type type, JsonSerializationContext jsc) {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("class", t.getClass().getSimpleName());
//            return jsonObject;
//        }
//
//
//    }




}

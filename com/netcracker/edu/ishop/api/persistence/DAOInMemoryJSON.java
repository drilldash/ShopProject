package netcracker.edu.ishop.api.persistence;

import com.google.gson.*;

import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.commands.engine.CommandEngine;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.DAOUtils;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.util.*;

public class DAOInMemoryJSON extends DAO {

    private static DAOInMemoryJSON INSTANCE = new DAOInMemoryJSON();

    public static final Logger log = Logger.getLogger(DAOInMemoryJSON.class);

    private HeterogeneousTypeSafeContainer dataMemoryStorage = new HeterogeneousTypeSafeContainer();

    private DAOInMemoryJSON() {

        //log.info("Loaded DAO instance: " + getClass());
        for (Class cls : DAOUtils.getListofAbsBusinessObjTypes()) {
            restoreDataMemoryMapPartsByBusinessObjType(cls);
        }
    }

    public static DAOInMemoryJSON getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DAOInMemoryJSON();
        }
        return INSTANCE;
    }

    @Override
    public <T extends AbstractBusinessObject> T create(Class<T> abObjType) {
        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();
        if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
            return spawnBusinessObjectInstanceByReflection(abObjType, UIDGenerator.getID());
        }
        return null;
    }

    @Override
    public <T extends AbstractBusinessObject> T load() {
        return null;
    }


    @Override
    public <T extends AbstractBusinessObject> void save(T abObj) {
        try {
            //log.info("Save is disabled");
            //HashMap mapShard = dataMemoryStorage.getHashMapByType(abObj.getClass());

            Map<BigInteger, Object> mapShard = dataMemoryStorage.getHashMapByType(abObj.getClass());

            if (mapShard != null) {
                if (AbstractBusinessObject.class.isAssignableFrom(abObj.getClass())){
                    mapShard.put(abObj.getId(), abObj);
                }
            } else {
                log.info("It seems that main serialized data structure was broken during serialization or loading");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    @Override
    public <T extends AbstractBusinessObject> void delete(T aboObj) {
        if (AbstractBusinessObject.class.isAssignableFrom(aboObj.getClass())) {
           dataMemoryStorage.getHashMapByType(User.class).remove(aboObj.getId());
        }
    }

    public <T extends AbstractBusinessObject> Map getDataMapByABOName(Class<T> abObj) {
        return dataMemoryStorage.getHashMapByType(abObj);
    }


    public User findUserByName(String userName) {

        Map userMap = dataMemoryStorage.getHashMapByType(User.class);
        //log.info(userMap);
        for (Iterator<User> userIterator = userMap.values().iterator(); userIterator.hasNext(); ) {
            User user = userIterator.next();
            if (user.getName().equals(userName.toLowerCase())) {
                return user;
            }
        }
        return null;
    }


    public Folder findFolderInstanceByName(String nameABO) {
        Map shardMap = dataMemoryStorage.getHashMapByType(Folder.class);
        //log.info(shardMap);
        for (Iterator<Folder> aboIterator = shardMap.values().iterator(); aboIterator.hasNext(); ) {
            Folder aboObj = aboIterator.next();
            if (aboObj.getName().equals(nameABO)) {
                return aboObj;
            }

            //log.info(aboObj.getName());

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractBusinessObject> T findAbstractBusinessObjByName(Class<T> cls, String nameABO) {
        Map shardMap = dataMemoryStorage.getHashMapByType(cls);
        //log.info(shardMap);

        for (Iterator<T> aboIterator = shardMap.values().iterator(); aboIterator.hasNext(); ) {
            T aboObj = aboIterator.next();
            if (aboObj.getName().equals(nameABO)) {
                return aboObj;
            }

            //log.info(aboObj.getName());

        }
        return null;
    }







    @Override
    public <T extends AbstractBusinessObject> T findABOInstanceById(Class<T> cls, BigInteger id) {
        Map shardMap = dataMemoryStorage.getHashMapByType(cls);

        BigInteger searchedId = BigInteger.ZERO;

        if (shardMap.containsKey(id)) {
            return (T) shardMap.get(id);

        } else {
            return null;
        }

    }

    @Override
    public List<Folder> findAllFoldersWithGivenParentId(BigInteger givenParentId) {
        Map shardMap = dataMemoryStorage.getHashMapByType(Folder.class);

        List<Folder> folderList = new ArrayList<>();

        for (Iterator<Folder> aboIterator = shardMap.values().iterator(); aboIterator.hasNext(); ) {
            Folder aboObj = aboIterator.next();

            //excluding root;
            if (aboObj.getParentFolderId() != null) {
                if (aboObj.getParentFolderId().equals(givenParentId)) {
                    folderList.add(aboObj);
                    //log.info("Added: " + aboObj.getName());
                }
            }

        }
        return folderList;

    }

    @Override
    public Folder findParentFoldersWithGivenParentId(BigInteger givenParentId) {
        Map shardMap = dataMemoryStorage.getHashMapByType(Folder.class);
        Folder parentFolder = (Folder) shardMap.get(givenParentId);
        return parentFolder;
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
        ItemProperty.Class
        ***
        */

//        jsonSerializeBObjectByType(User.class);
//        jsonSerializeBObjectByType(Folder.class);
//        jsonSerializeBObjectByType(Item.class);
//        jsonSerializeBObjectByType(Order.class);

        for (Class cls : DAOUtils.getListofAbsBusinessObjTypes()) {
            jsonSerializeBObjectByType(cls);
        }

        //here we serialize last ID number to use it as initial value for next launch of the application
        saveLastID();

        //test: entire json serialization\deserialization
        //jsonSerializeEntireDataStructure(SerializationConstants.SERIALIZED_DATAMAP_FILE_PATH);
        //not cool, each instantiation of CommandEngine creates DAO-instance for himself (should dao be singleton in multithreaded app?)

        CommandEngine comEngine = CommandEngine.getInstance();

        for (Iterator<User> userIterator = CurrentSessionState.getAllSignedInUsers().iterator(); userIterator.hasNext(); ) {
            User user = userIterator.next();
            try {
                comEngine.executeCommand("sign_out " + user.getName());
            } catch (AccessDeniedException ADE) {
                log.info(ADE);
            }

        }


    }

    private <T extends AbstractBusinessObject> T spawnBusinessObjectInstanceByReflection(Class<T> abObjType, BigInteger newId) {
        T newBusinessObjectInstance = null;
        try {
            Constructor<T> constructor = abObjType.getConstructor(BigInteger.class);
            newBusinessObjectInstance = constructor.newInstance(newId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException exceptionMessage) {
            log.info(exceptionMessage);
        }
        return newBusinessObjectInstance;
    }




    //    private <T extends AbstractBusinessObject> void jsonSerializeBObjectByType(Class<T> abObjType) {
//        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
//        Map mapShard = dataMemoryStorage.getHashMapByType(abObjType);
//        Type mapType = new TypeToken<Map<BigInteger, T>>() {}.getType();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(mapShard, mapType);
//        try {
//            FileWriter writer = new FileWriter(serializedFileName);
//            writer.write(json);
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private <T extends AbstractBusinessObject> HashMap jsonDeserializeBObjectByType(Class<T> abObjType) {
//        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
//        File serializedObjectFile = new File(serializedFileName);
//        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
//            Gson gson = new Gson();
//            try {
//
//                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));
//
//
//                if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, T>>() {
//                    }.getType();
//                    HashMap<BigInteger, T> mapShard = gson.fromJson(br, HashMap.class);
//                    return mapShard;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return null;
//    }

    private Gson prepareGson() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }


    private <T extends AbstractBusinessObject> void jsonSerializeBObjectByType(Class<T> abObjType) {
        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
        Map mapShard = dataMemoryStorage.getHashMapByType(abObjType);

        Type mapType = new TypeToken<Map<BigInteger, T>>() {
        }.getType();

        Gson gson = prepareGson();
        String json = gson.toJson(mapShard, mapType);
        try {
            FileWriter writer = new FileWriter(serializedFileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<BigInteger, Object> jsonDeserializeBObjectByType(Class abObjType) {
        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
        File serializedObjectFile = new File(serializedFileName);
        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
            Gson gson = prepareGson();
            try {

                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));

                return gson.fromJson(br,
                        $Gson$Types.newParameterizedTypeWithOwner(null, Map.class, BigInteger.class, abObjType));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }


//    private HashMap jsonDeserializeBObjectByType(Class abObjType) {
//        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
//        File serializedObjectFile = new File(serializedFileName);
//        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
//            Gson gson = prepareGson();
//            try {
//
//                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));
//
//                if (User.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, User>>() {
//                    }.getType();
//                    HashMap<BigInteger, User> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//                if (Folder.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, Folder>>() {
//                    }.getType();
//                    HashMap<BigInteger, Folder> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//
//                if (Item.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, Item>>() {
//                    }.getType();
//                    HashMap<BigInteger, Item> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//                if (Order.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, Order>>() {
//                    }.getType();
//                    HashMap<BigInteger, Order> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return null;
//    }


    //generified method breaks data-structure with Error: com.google.gson.internal.LinkedTreeMap cannot be cast to ...
    //the reason is run-time type erasure

//    private <T extends AbstractBusinessObject> HashMap jsonDeserializeBObjectByType(Class<T> abObjType) {
//        String serializedFileName = DAOUtils.getSerializeFilePathByAbsBObjType(abObjType);
//        File serializedObjectFile = new File(serializedFileName);
//        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
//            Gson gson = new Gson();
//            try {
//
//                BufferedReader br = new BufferedReader(new FileReader(serializedFileName));
//
//                if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
//                    Type typeOfMap = new TypeToken<Map<BigInteger, T>>() {
//                    }.getType();
//                    HashMap<BigInteger, Order> mapShard = gson.fromJson(br, typeOfMap);
//                    return mapShard;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return null;
//    }


    private void saveLastID() {
        BigInteger lastID = UniqueIDGenerator.getInstance().getID();
        log.info("Last unused ID number:" + lastID);
        Gson gson = new Gson();
        Type varType = new TypeToken<BigInteger>(){}.getType();
        String jsonLastID = gson.toJson(lastID, varType);

        try {
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_LAST_ID_FILE_PATH);
            writer.write(jsonLastID);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private <T extends AbstractBusinessObject> void restoreDataMemoryMapPartsByBusinessObjType(Class<T> abObjType) {
        if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
            Map<BigInteger, Object> shardMap = jsonDeserializeBObjectByType(abObjType);
            if (shardMap != null) {
                //log.info(shardMap);
                dataMemoryStorage.putFavorite(abObjType, shardMap);
            } else {
                dataMemoryStorage.putFavorite(abObjType, new HashMap<>());
            }
        }
    }




}

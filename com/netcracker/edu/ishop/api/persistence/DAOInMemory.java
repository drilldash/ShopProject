package netcracker.edu.ishop.api.persistence;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;

@SuppressWarnings("unchecked")
public class DAOInMemory<T extends AbstractBusinessObject> extends DAO<T> {

    public static final Logger log = Logger.getLogger(DAOInMemory.class);

    private Map<Class, Map<BigInteger, T>> dataMap;


    public DAOInMemory() {

        this.dataMap = new HashMap<>();

        //Explicit enumeration of all Business Objects! Manual work is a bad solution
        List<Class> typesList = new ArrayList<>(Arrays.asList(User.class, Folder.class, Order.class, Item.class));
        Gson gson = new Gson();

        for (Class type: typesList) {
            String serializedObjectPath = FilenameUtils.concat(SerializationConstants.SERIALIZED_OBJECT_FOLDER_PATH, type.getName() + ".json");
            File serializedObjectFile = new File(serializedObjectPath);

            if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(serializedObjectPath));
                    //ClassCastException!

                    final Type serializedType = new TypeToken<Map<BigInteger, T>>(){}.getType();
                    Map<BigInteger, T> shardMap =  gson.fromJson(br, serializedType);
                    dataMap.put(type, shardMap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (dataMap.size() != 0) {
            log.info(dataMap.get(User.class));
        }


        if (dataMap.size() == 0) {
            dataMap.put(User.class, new HashMap<BigInteger, T>());
            dataMap.put(Folder.class, new HashMap<BigInteger, T>());
            dataMap.put(Item.class, new HashMap<BigInteger, T>());
            dataMap.put(Order.class, new HashMap<BigInteger, T>());
        }

    }

    public T create(Class<T> abObj) {

        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();

        if (User.class.isAssignableFrom(abObj)) {
            BigInteger id = UIDGenerator.getID();
            User user = new User(id);
            System.out.println(user.toString());
            return (T) user;
        }

        if (Folder.class.isAssignableFrom(abObj)) {
            BigInteger id = UIDGenerator.getID();
            Folder folder = new Folder(id);
            System.out.println(folder.toString());
            return (T) folder;
        }

        if (Item.class.isAssignableFrom(abObj)) {
            BigInteger id = UIDGenerator.getID();
            Item item = new Item(id);
            System.out.println(item.toString());
            return (T) item;
        }

        if (Order.class.isAssignableFrom(abObj)) {
            BigInteger id = UIDGenerator.getID();
            Order order = new Order(id);
            System.out.println(order.toString());
            return (T) order;
        }

        if (abObj == null) {
            throw new NullPointerException("Can't create instance for" + abObj);
        }
        return null;
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
                mapShard.put(abObj.getId(), abObj);
                log.info("Putting " + abObj.getClass().getName() + " into the shard " + mapShard.getClass().getName() + " successful" );
            }
            else {
                log.info("It seems that main serialized data structure was broken during serialization or loading");
            }
            //log.info(Arrays.toString(mapShard.entrySet().toArray()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void delete() {

    }

    @Override
    public Map getMapShardByABOName(Class<T> abObj) {
        return null;
    }

    @Override
    public void DAOExit() {
        Gson gson = new Gson();
//        String json = gson.toJson(dataMap);
//        log.info(json);
//
//        try {
//            //write converted json data to a file named "CountryGSON.json"
//            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_OBJECT_FILE_PATH);
//            writer.write(json);
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        for (Map.Entry<Class, Map<BigInteger, T>> entry : dataMap.entrySet()) {
            Class type = entry.getKey();

            Map<BigInteger, T> mapShard= entry.getValue();
            log.info(mapShard);
            String json = gson.toJson(mapShard);
            log.info(json);

            try {
                //write converted json data to a file named "<typename>.json"

                String serializedObjectPath = FilenameUtils.concat(SerializationConstants.SERIALIZED_OBJECT_FOLDER_PATH, type.getName() + ".json");
                File serializedObjectFile = new File(serializedObjectPath);


                if (!serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
                    FileWriter writer = new FileWriter(serializedObjectPath);
                    writer.write(json);
                    writer.close();
                }

                if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
                    serializedObjectFile.delete();
                    serializedObjectFile.createNewFile();

                    FileWriter writer = new FileWriter(serializedObjectPath);
                    writer.write(json);
                    writer.close();
                    //register_user u2 p2
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }


}

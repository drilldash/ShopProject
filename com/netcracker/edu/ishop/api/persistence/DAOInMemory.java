package netcracker.edu.ishop.api.persistence;
import com.google.gson.Gson;

import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DAOInMemory<T extends AbstractBusinessObject> extends DAO<T> {

    public static final Logger log = Logger.getLogger(DAOInMemory.class);

    private Map<Class, Map<BigInteger, T>> dataMap;

    public DAOInMemory() {

        File serializedObjectFile = new File(SerializationConstants.SERIALIZED_OBJECT_FILE_PATH);
        if (serializedObjectFile.exists() && !serializedObjectFile.isDirectory()) {
            Gson gson = new Gson();
            try {
                BufferedReader br = new BufferedReader(new FileReader(SerializationConstants.SERIALIZED_OBJECT_FILE_PATH));
                Map<Class, Map<BigInteger, T>> dataMap = gson.fromJson(br, HashMap.class);
                this.dataMap = dataMap;
                log.info("It seems that system succeeded in deserialization of dataMap");

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            this.dataMap = new HashMap<>();
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
        String json = gson.toJson(dataMap);
        log.info(json);

        try {
            //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_OBJECT_FILE_PATH);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

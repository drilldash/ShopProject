package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DAOInMemory<T extends AbstractBusinessObject> extends DAO<T> {

    public static final Logger log = Logger.getLogger(DAOInMemory.class);

    private Map<Class, Map<BigInteger, T>> dataMap;

    public DAOInMemory() {
        this.dataMap = new HashMap<>();
        dataMap.put(User.class, new HashMap<BigInteger, T>());
        dataMap.put(Folder.class, new HashMap<BigInteger, T>());
        dataMap.put(Item.class, new HashMap<BigInteger, T>());
        dataMap.put(Order.class, new HashMap<BigInteger, T>());



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
        dataMap.get(abObj.getClass()).put(abObj.getId(), abObj);
        log.info(Arrays.toString(dataMap.get(abObj.getClass()).entrySet().toArray()));


    }


    @Override
    public void delete() {

    }

    @Override
    public Map getMapShardByABOName(Class<T> abObj) {
        return null;
    }
}
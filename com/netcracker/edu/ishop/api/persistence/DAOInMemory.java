package netcracker.edu.ishop.api.persistence;
import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class  DAOInMemory<T extends AbstractBusinessObject>  extends DAO<T> {

    private Map<Class<T>, Map<BigInteger, Class<T>>> dataMap;

    private Map<BigInteger, User> userShard = new HashMap<>();


    public DAOInMemory() {
        this.dataMap = new HashMap<>();

        dataMap.put( User.class, userShard );

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

    public void save(Class<T> abObj) {
        if (User.class.isAssignableFrom(abObj)) {
            userShard.put(abObj, abObj);

    }
    }

    @Override
    public void delete() {

    }

    @Override
    public Map getMapShardByABOName(Class<T> abObj) {
        return null;
    }
}

package netcracker.edu.ishop.api.persistence;


import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.utils.UniqueIDGenerator;

import java.math.BigInteger;

public class DAOInMemory extends DAO {
    @Override
    public <T extends AbstractBusinessObject> T create(Class<T> abObj) {

        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();

        if (User.class.isAssignableFrom(abObj)) {
            BigInteger id = UIDGenerator.getID();
            User user = new User(id);
            System.out.println(user.toString());

            return (T) user;
        }

        if (abObj == null) {
            throw new NullPointerException("Can't create instance for" + abObj);
        }

        System.out.println();
        return null;
    }

    @Override
    public AbstractBusinessObject load() {
        return null;
    }

    @Override
    public AbstractBusinessObject save() {
        return null;
    }

    @Override
    public AbstractBusinessObject delete() {
        return null;
    }
}

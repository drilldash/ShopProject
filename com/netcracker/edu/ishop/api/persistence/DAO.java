package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.User;

import java.util.HashMap;
import java.util.Map;

abstract public class DAO <T extends AbstractBusinessObject> {

    // CRUD: create, read, update, delete -> create, load, save, delete

    public abstract T create(Class<T> abObj);

    public abstract T load();

    public abstract void save(T abObj);

    public abstract void delete(T abObj);

    public abstract Map getMapShardByABOName(Class<T> abObj);

    public abstract User findUserByName(String username);

    public abstract void DAOExit();



}

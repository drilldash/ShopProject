package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.User;

import java.util.HashMap;
import java.util.Map;

abstract public class DAO {

    // CRUD: create, read, update, delete -> create, load, save, delete

    public abstract <T extends AbstractBusinessObject>  T create(Class<T> abObj);

    public abstract <T extends AbstractBusinessObject>  T load();

    public abstract <T extends AbstractBusinessObject>  void save(T abObj);

    public abstract <T extends AbstractBusinessObject>  void delete(T abObj);

    public abstract <T extends AbstractBusinessObject>  Map getMapShardByABOName(Class<T> abObj);

    public abstract User findUserByName(String username);

    public abstract void DAOExit();



}

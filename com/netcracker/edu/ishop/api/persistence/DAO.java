package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;

abstract public class DAO {

    // CRUD: create, read, update, delete -> create, load, save, delete

    public abstract <T extends AbstractBusinessObject> T create(Class<T> abObj);

    public abstract AbstractBusinessObject load();

    public abstract AbstractBusinessObject save();

    public abstract AbstractBusinessObject delete();

}

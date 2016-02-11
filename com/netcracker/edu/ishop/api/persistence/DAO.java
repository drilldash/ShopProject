package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.User;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class DAO {

    // CRUD: create, read, update, delete -> create, load, save, delete

    public abstract <T extends AbstractBusinessObject>  T create(Class<T> abObj);

    public abstract <T extends AbstractBusinessObject>  T load();

    public abstract <T extends AbstractBusinessObject>  void save(T abObj);

    public abstract <T extends AbstractBusinessObject>  void delete(T abObj);

    public abstract <T extends AbstractBusinessObject>  Map getDataMapByABOName(Class<T> abObj);

    public abstract User findUserByName(String username);

    public abstract  <T extends AbstractBusinessObject> T findAbstractBusinessObjByName(Class<T> cls, String username);

    public abstract Folder findFolderInstanceByName(String folderName);

    public abstract Folder findParentFoldersWithGivenParentId(BigInteger givenParentId);

    public abstract List<Folder> findAllFoldersWithGivenParentId(BigInteger givenParentId);

    public abstract  <T extends AbstractBusinessObject> T findABOInstanceById(Class<T> cls, BigInteger id);

    public abstract void DAOExit();



}

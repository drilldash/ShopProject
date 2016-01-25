package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;
import java.util.ArrayList;

//http://stackoverflow.com/a/12431359/2938167 Composition implementation

public class Folder extends AbstractBusinessObject{
    private String name;
    private BigInteger parentFolderId;


    public Folder(BigInteger id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public BigInteger getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(BigInteger parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + "', " +
                "id = " + getId() +
                ", parentFolderId=" + parentFolderId +
                '}';
    }
}





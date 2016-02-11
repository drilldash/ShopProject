package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;
import java.util.ArrayList;

//http://stackoverflow.com/a/12431359/2938167 Composition implementation

public class Folder extends AbstractBusinessObject{
    private BigInteger parentFolderId;


    public Folder(BigInteger id) {
        super(id);
    }


    public BigInteger getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(BigInteger parentFolderId) {
        this.parentFolderId = parentFolderId;
    }


    @Override
    public String toString() {
        return "Folder{" +
                "name='" + this.name + "', " +
                "id = " + getId() +
                ", parentFolderId=" + parentFolderId +
                '}';
    }
}





package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;


// http://stackoverflow.com/a/7660563/2938167 Unique int ID

public abstract class AbstractBusinessObject {
    private BigInteger id;

    public AbstractBusinessObject(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
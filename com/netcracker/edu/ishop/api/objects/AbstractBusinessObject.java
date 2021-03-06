package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;


// http://stackoverflow.com/a/7660563/2938167 Unique int ID

public abstract class AbstractBusinessObject {
    private BigInteger id;
    protected String name;

    public AbstractBusinessObject(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
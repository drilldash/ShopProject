package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;

public class ItemProperty extends AbstractBusinessObject {
    private String itemPropName;

    public ItemProperty(BigInteger id) {
        super(id);
        //this. itemPropName = charName;
    }

    public String getItemPropName() {
        return itemPropName;
    }

    @Override
    public String toString() {
        return this.name;
    }
}


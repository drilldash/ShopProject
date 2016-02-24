package netcracker.edu.ishop.api.objects;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Item extends AbstractBusinessObject {

    public static final Logger log = Logger.getLogger(Item.class);

    private String itemType;
    private BigInteger folderId;

    private List<ItemPropertyValue> charsVals = new LinkedList<>();

    public Item(BigInteger id) {
        super(id);
    }

    public void setFolderId(BigInteger folderId) {
        this.folderId = folderId;
    }

    public BigInteger getFolderId() {
        return folderId;
    }


    public void printCharValues() {
        for (ItemPropertyValue elem : charsVals) {
            log.info(name + " " + elem.toString());
        }
    }

    public List<ItemPropertyValue> getCharsVals() {
        return charsVals;
    }






    @Override
    public String toString() {
        return "In folder with id " + folderId + " there is " + name + " " + charsVals.toString();
    }

    public String printItemWithProperties() {
        return name + " " + charsVals.toString();
    }


    public void addPropertyWithVal(ItemPropertyValue propValue) {
        this.charsVals.add(propValue);
    }




}

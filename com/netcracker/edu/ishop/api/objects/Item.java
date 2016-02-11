package netcracker.edu.ishop.api.objects;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;

public class Item extends AbstractBusinessObject {

    public static final Logger log = Logger.getLogger(Item.class);

    private String itemType;
    private BigInteger folderId;
    private BigInteger itemNum = BigInteger.ZERO;

    public BigInteger getItemNum() {
        return itemNum;
    }

    public void setItemNum(BigInteger itemNum) {

        this.itemNum = itemNum;
    }

    private LinkedList<ItemPropertyValue> charsVals = new LinkedList<>();

    public Item(BigInteger id) {
        super(id);
    }

    public void setFolderId(BigInteger folderId) {
        this.folderId = folderId;
    }

    public BigInteger getFolderId() {
        return folderId;
    }


    public void getCharValues() {
        for (ItemPropertyValue elem : charsVals) {
            log.info(name + " " + elem.toString());
        }
    }

    @Override
    public String toString() {
        return "In folder with id " + folderId + " there is " + name + " " + charsVals.toString() + " number: " + itemNum;
    }

    public void addPropertyWithVal(ItemPropertyValue propValue) {
        this.charsVals.add(propValue);
    }

}

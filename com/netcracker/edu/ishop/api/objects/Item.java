package netcracker.edu.ishop.api.objects;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;

public class Item extends AbstractBusinessObject {

    public static final Logger log = Logger.getLogger(Item.class);

    private String itemType;
    private int folderId;
    private ArrayList<CharacteristicValue> charsVals = new ArrayList<>();

    public Item(BigInteger id) {
        super(id);
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getFolderId() {
        return folderId;
    }


    public String getItemType() {
        return itemType;
    }

    public void getCharValues() {
        for (CharacteristicValue elem : charsVals) {
            log.info(itemType + " " + elem.toString());
        }
    }

    @Override
    public String toString() {
        return "In folder with id " + folderId + " there is " + itemType + " " + charsVals;
    }

}

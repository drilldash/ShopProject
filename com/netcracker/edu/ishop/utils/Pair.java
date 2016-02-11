package netcracker.edu.ishop.utils;
import netcracker.edu.ishop.api.objects.ItemProperty;

public class Pair{
    private ItemProperty charKey;
    private  String charVal;

    public Pair(ItemProperty key, String val) {
        this.charKey = key;
        this.charVal = val;
    }

    public ItemProperty getKey() {
        return charKey;
    }

    public String getVal() {
        return charVal;
    }
}

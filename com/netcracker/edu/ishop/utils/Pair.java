package netcracker.edu.ishop.utils;
import netcracker.edu.ishop.api.objects.Characteristic;

public class Pair{
    private  Characteristic charKey;
    private  String charVal;

    public Pair(Characteristic key, String val) {
        this.charKey = key;
        this.charVal = val;
    }

    public Characteristic getKey() {
        return charKey;
    }

    public String getVal() {
        return charVal;
    }
}

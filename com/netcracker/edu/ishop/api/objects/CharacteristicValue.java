package netcracker.edu.ishop.api.objects;

//import javafx.util.Pair;
import netcracker.edu.ishop.utils.Pair;
import org.apache.log4j.Logger;

//http://stackoverflow.com/questions/2973041/a-keyvaluepair-in-java

public class CharacteristicValue {
    public static final Logger log = Logger.getLogger(CharacteristicValue.class);
    private Pair charWithValue;

    public CharacteristicValue(Characteristic charInstance, String charValue) {
        this.charWithValue = new Pair(charInstance, charValue);
    }

    public void getCharPair() {
        log.info(charWithValue.getKey().getCharName() + " " + charWithValue.getVal());
    }

    @Override public String toString()
    {
        String exp = charWithValue.getKey().getCharName() + " " + charWithValue.getVal();
        return exp;
    }

}

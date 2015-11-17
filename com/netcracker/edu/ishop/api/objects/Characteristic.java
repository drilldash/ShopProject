package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;

public class Characteristic extends AbstractBusinessObject {
    private String charName;

    public Characteristic(BigInteger id, String charName) {
        super(id);
        this.charName = charName;
    }

    public String getCharName() {
        return charName;
    }
}

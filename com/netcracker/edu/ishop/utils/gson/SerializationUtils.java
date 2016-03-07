package netcracker.edu.ishop.utils.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.persistence.DAOInMemoryJSON;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;

public class SerializationUtils {

    private static final Logger log = Logger.getLogger(SerializationUtils.class);

    public static void saveLastID() {
        BigInteger lastID = UniqueIDGenerator.getInstance().getID();
        log.info("Last unused ID number:" + lastID);
        Gson gson = new Gson();
        Type varType = new TypeToken<BigInteger>(){}.getType();
        String jsonLastID = gson.toJson(lastID, varType);

        try {
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_LAST_ID_FILE_PATH);
            writer.write(jsonLastID);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

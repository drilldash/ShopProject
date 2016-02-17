package netcracker.edu.ishop.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.objects.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/*ID Generator must be singleton, thread-safe and it must generate ID from last saved Id-value not from zero */

public class UniqueIDGenerator {
    private static UniqueIDGenerator INSTANCE = null;
    private static AtomicInteger uniqueId;

    private UniqueIDGenerator() {
        BigInteger lastID = restoreLastID();
        uniqueId = new AtomicInteger(lastID.intValue());
    }

    public static UniqueIDGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UniqueIDGenerator();
        }
        return INSTANCE;
    }

    public BigInteger getID() {
        int id = uniqueId.getAndIncrement();
        return BigInteger.valueOf(id);
    }

    public void decrementID() {
        uniqueId.getAndDecrement();
    }

    public void incrementID() {
        uniqueId.getAndIncrement();
    }

    private BigInteger restoreLastID() {
        File serializedLastIDFile = new File(SerializationConstants.SERIALIZED_LAST_ID_FILE_PATH);
        if (serializedLastIDFile.exists() && !serializedLastIDFile.isDirectory()) {
            Gson gson = new Gson();
            try {
                BufferedReader br = new BufferedReader(new FileReader(serializedLastIDFile));
                Type varType = new TypeToken<BigInteger>() {}.getType();
                BigInteger lastID = gson.fromJson(br, varType);
                return lastID;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BigInteger.ONE;
    }


}
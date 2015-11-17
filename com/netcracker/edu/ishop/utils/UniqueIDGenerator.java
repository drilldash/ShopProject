package netcracker.edu.ishop.utils;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

/*ID Generator must be singleton, thread-safe and it must generate ID from last saved Id-value not from zero */

public class UniqueIDGenerator {
    private static UniqueIDGenerator INSTANCE = null;
    private static AtomicInteger uniqueId = new AtomicInteger();
    private static int id;

    private UniqueIDGenerator() {
    }

    public static UniqueIDGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UniqueIDGenerator();
        }
        return INSTANCE;
    }

    public BigInteger getID() {
        id = uniqueId.getAndIncrement();
        return BigInteger.valueOf(id);
    }
}
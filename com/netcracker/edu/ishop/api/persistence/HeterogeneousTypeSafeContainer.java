package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HeterogeneousTypeSafeContainer {

    private Map<Class<?>, Map> favorites = new ConcurrentHashMap<>();

    public <T> void putFavorite(Class type, Map instance) {
        if (type == null)
            throw new NullPointerException("Type is null");
        //favorites.put(type,  new HashMap<>(instance));
        favorites.put(type,  instance);
    }

//    public <T> T getFavorite(Class<T> type) {
//        return type.cast(favorites.get(type));
//    }

    public <T> Map getHashMapByType(Class<T> type) {
        //
        // return new HashMap<>(favorites.get(type));
        return favorites.get(type);
    }

}




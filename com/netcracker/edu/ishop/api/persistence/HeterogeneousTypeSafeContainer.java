package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.AbstractBusinessObject;

import java.util.HashMap;
import java.util.Map;

public class HeterogeneousTypeSafeContainer {

    private Map<Class<?>, HashMap> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, HashMap instance) {
        if (type == null)
            throw new NullPointerException("Type is null");
        favorites.put(type, instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }

    public <T> HashMap getHashMapByType(Class<T> type) {
        return favorites.get(type);
    }

}
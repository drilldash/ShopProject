//package netcracker.edu.ishop.api.persistence;
//
//import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
//
//import java.math.BigInteger;
//import java.util.HashMap;
//import java.util.Map;
//
//public class HomogeneousTypeSafeContainer<T extends AbstractBusinessObject> {
//
//    private Map<BigInteger, T> favorites = new HashMap<>();
//
//    public  void putSafe(T abObj) {
//        favorites.put(abObj.getId(), abObj);
//    }
//
//    public Map<BigInteger, T> getMapByType() {
//        return favorites;
//    }
//
//}

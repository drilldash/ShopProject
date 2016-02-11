package netcracker.edu.ishop.utils;

import netcracker.edu.ishop.api.objects.*;

import java.util.*;

public class DAOUtils {

    private static Map<String, Class> mapAbsBusinessObjNameToType = new HashMap<>();

    private static Map<Class, String> mapAbsBusinessObjTypeToSerializeFilePath = new HashMap<>();

    private static List<Class> listofAbsBusinessObjTypes;

    static {
        mapAbsBusinessObjNameToType.put("User", User.class);
        mapAbsBusinessObjNameToType.put("Folder", Folder.class);
        mapAbsBusinessObjNameToType.put("Item", Item.class);
        mapAbsBusinessObjNameToType.put("Order", Order.class);
        mapAbsBusinessObjNameToType.put("ItemProperty", ItemProperty.class);

        mapAbsBusinessObjTypeToSerializeFilePath.put(User.class, SerializationConstants.SERIALIZED_USERMAP_FILE_PATH);
        mapAbsBusinessObjTypeToSerializeFilePath.put(Folder.class, SerializationConstants.SERIALIZED_FOLDERMAP_FILE_PATH);
        mapAbsBusinessObjTypeToSerializeFilePath.put(Item.class, SerializationConstants.SERIALIZED_ITEMMAP_FILE_PATH);
        mapAbsBusinessObjTypeToSerializeFilePath.put(Order.class, SerializationConstants.SERIALIZED_ORDERMAP_FILE_PATH);
        mapAbsBusinessObjTypeToSerializeFilePath.put(ItemProperty.class, SerializationConstants.SERIALIZED_ITEM_PROPERTY_MAP_FILE_PATH);

        listofAbsBusinessObjTypes =  Arrays.asList(User.class, Folder.class, Item.class, Order.class, ItemProperty.class);

    }

    public static Class getClassByAbsBusinessObjName(String AbsBusinessObjName) {
        return mapAbsBusinessObjNameToType.get(AbsBusinessObjName);
    }

    public static List<Class> getListofAbsBusinessObjTypes() {
        return listofAbsBusinessObjTypes;
    }

    public static String getSerializeFilePathByAbsBObjType(Class cls){
        return mapAbsBusinessObjTypeToSerializeFilePath.get(cls);
    }

}

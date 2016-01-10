package netcracker.edu.ishop.utils;

import org.apache.commons.io.FilenameUtils;

public class SerializationConstants {
    // J. Bloch EPJ, article 19
    public static final String SERIALIZED_OBJECT_FOLDER_NAME = "serialization";
    public static final String SERIALIZED_OBJECT_FOLDER_PATH = FilenameUtils.concat(System.getProperty("user.dir"),
            SERIALIZED_OBJECT_FOLDER_NAME);

    //assuming separate directory location of serialized objects under project's root
    public static final String SERIALIZED_OBJECT_FILE_NAME = "dataMap.json";
    public static final String SERIALIZED_OBJECT_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_OBJECT_FILE_NAME);

    public static final String SERIALIZED_USERMAP_FILE_NAME = "userMap.json";
    public static final String SERIALIZED_USERMAP_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_USERMAP_FILE_NAME);

    public static final String SERIALIZED_FOLDERMAP_FILE_NAME = "folderMap.json";
    public static final String SERIALIZED_FOLDERMAP_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_FOLDERMAP_FILE_NAME);

    public static final String SERIALIZED_ITEMMAP_FILE_NAME = "itemMap.json";
    public static final String SERIALIZED_ITEMMAP_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_ITEMMAP_FILE_NAME);

    public static final String SERIALIZED_ORDERMAP_FILE_NAME = "orderMap.json";
    public static final String SERIALIZED_ORDERMAP_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_ORDERMAP_FILE_NAME);

    //////

    public static final String SERIALIZED_LAST_ID_FILE_NAME = "lastID.json";
    public static final String SERIALIZED_LAST_ID_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_LAST_ID_FILE_NAME);

    //////
    public static final String SERIALIZED_FIRST_LAUNCH_FILE_NAME = "firstLaunch.json";
    public static final String SERIALIZED_FIRST_LAUNCH_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH,
            SERIALIZED_FIRST_LAUNCH_FILE_NAME);


}

package netcracker.edu.ishop.utils;

import org.apache.commons.io.FilenameUtils;

public class SerializationConstants {
    // J. Bloch EPJ, article 19
    public static final String SERIALIZED_OBJECT_FOLDER_NAME = "serialization";

    //assuming separate directory location of serialized objects under project's root
    public static final String SERIALIZED_OBJECT_FILE_NAME = "dataMap.json";
    public static final String SERIALIZED_OBJECT_FOLDER_PATH = FilenameUtils.concat(System.getProperty("user.dir"), SERIALIZED_OBJECT_FOLDER_NAME);
    public static final String SERIALIZED_OBJECT_FILE_PATH = FilenameUtils.concat(SERIALIZED_OBJECT_FOLDER_PATH, SERIALIZED_OBJECT_FILE_NAME);


}

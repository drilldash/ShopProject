package netcracker.edu.ishop.utils;
import org.apache.commons.io.FilenameUtils;

public class ScenarioConstants {
    // J. Bloch EPJ, article 19
    public static final boolean PERMIT_SCENARIO_EXECUTION = true;

    //assuming separate directory location of custom user scripts under project's root
    public static final String SCENARIO_FOLDER_NAME = "scenario";
    public static final String SCENARIO_FILE_NAME = "command_sequence.script";
    public static final String SCENARIO_FOLDER_PATH= FilenameUtils.concat(System.getProperty("user.dir"), SCENARIO_FOLDER_NAME);
    public static final String SCENARIO_FILE_PATH= FilenameUtils.concat(SCENARIO_FOLDER_PATH, SCENARIO_FILE_NAME);



}

package netcracker.edu.ishop.api.persistence;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOFactory {
    public static final Logger log = Logger.getLogger(DAOFactory.class);

    private static final DAO DAO_INSTANCE;

    static {

        InputStream input;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException e) {
            log.error("Error: no config properties file found!");
            e.printStackTrace();
        }

        switch (prop.getProperty("USING_DATA_ACCESS_OBJECT_ENGINE")) {
            case "DAOJsonInMemory":
                //log.info("We are here");
                DAO_INSTANCE = DAOInMemoryJSON.getInstance();
                log.info("Loaded DAO instance: " + DAO_INSTANCE.getClass());
                break;
            case "DAOJsonInMemoryConcurrent":
                //log.info("We are here");
                DAO_INSTANCE = DAOInMemoryJSONConcurrent.getInstance();
                log.info("Loaded DAO instance: " + DAO_INSTANCE.getClass());
                break;


            default:
                DAO_INSTANCE = null;
                log.error("Error: Invalid value of DAO.");
                System.exit(0);
        }
    }

    public static DAO getDAO() {
        return DAO_INSTANCE;
    }
}
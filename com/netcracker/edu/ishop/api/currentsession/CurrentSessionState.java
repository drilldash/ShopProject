package netcracker.edu.ishop.api.currentsession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAOFactory;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


public class CurrentSessionState {

    public static final Logger log = Logger.getLogger(CurrentSessionState.class);
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<UserGroupTypes> userGroupTypeLocal = new ThreadLocal<>();
    private static final Set<User> activeUsers = new HashSet<>();

    private static final ThreadLocal<Folder> currentFolder = new ThreadLocal<>();

    private static Gson gson = new Gson();

    static {
        if (checkIfFirstLaunch()) {
            userGroupTypeLocal.set(UserGroupTypes.ADMIN);
            saveFirstLaunchStatus();

        } else {
            userGroupTypeLocal.set(UserGroupTypes.GUEST);
        }

        setInitialCurrentFolder();

    }

    public static void setNoUser() {
        userGroupTypeLocal.set(UserGroupTypes.GUEST);
        userThreadLocal.set(null);
    }

    public static Folder getCurrentFolder() {
        return currentFolder.get();
    }

    public static void setInitialCurrentFolder() {
        //log.info("we are here");
        Folder rootFolder = DAOFactory.getDAO().findFolderInstanceByName("ROOT");
        if ( rootFolder!= null) {
            currentFolder.set(rootFolder);

        } else {
            Folder folder = DAOFactory.getDAO().create(Folder.class);
            folder.setName("ROOT");
            currentFolder.set(folder);
            DAOFactory.getDAO().save(folder);
            //UniqueIDGenerator.getInstance().incrementID();
        }
    }

    public static void setCurrentFolder(Folder folder) {
        currentFolder.set(folder);
    }


    public static UserGroupTypes getUserGroupTypeLocal() {
        return userGroupTypeLocal.get();
    }

    public static User getSignedInUser() {
        return userThreadLocal.get();
    }

    public static Set getAllSignedInUsers() {
        return activeUsers;
    }

    public static void setSignedInUser(User user) {

        if (activeUsers.contains(user)) {
            log.info("User already signed in");
        } else {
            activeUsers.add(user);
            userThreadLocal.set(user);
            userGroupTypeLocal.set(user.getGroup());
        }
    }

    public static void removeUserFromSignedInUsers() {
        activeUsers.remove(userThreadLocal.get());
    }


    private static boolean checkIfFirstLaunch() {
        File serializedFirstLaunchFile = new File(SerializationConstants.SERIALIZED_FIRST_LAUNCH_FILE_PATH);
        if (serializedFirstLaunchFile.exists() && !serializedFirstLaunchFile.isDirectory()) {
            //Gson gson = new Gson();
            try {
                BufferedReader br = new BufferedReader(new FileReader(serializedFirstLaunchFile));
                Type varType = new TypeToken<Boolean>() {}.getType();
                Boolean firstLaunch = gson.fromJson(br, varType);
                return firstLaunch;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static void saveFirstLaunchStatus() {
        final Boolean firstLaunchStatus = false;
        //Gson gson = new Gson();
        Type varType = new TypeToken<Boolean>() {}.getType();
        String firstLaunchStatusJSON = gson.toJson(firstLaunchStatus, varType);

        try {
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_FIRST_LAUNCH_FILE_PATH);
            writer.write(firstLaunchStatusJSON);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

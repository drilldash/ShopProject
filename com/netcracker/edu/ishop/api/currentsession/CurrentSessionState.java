package netcracker.edu.ishop.api.currentsession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAOFactory;
import netcracker.edu.ishop.utils.SerializationConstants;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CurrentSessionState {

    public static final Logger log = Logger.getLogger(CurrentSessionState.class);

    private static CurrentSessionState INSTANCE = new CurrentSessionState();

    private static final Set<BigInteger> activeUserIds = Collections.synchronizedSet(new HashSet<>());

    private static Gson gson = new Gson();

    static class SessionState {
        User localUser;
        UserGroupTypes localUserType;
        Folder localCurrentFolder;

        // we need to elevate privileges from GUEST to ADMIN on the very first launch of the project
        public SessionState() {
            if (checkIfFirstLaunch()) {
                localUserType = UserGroupTypes.ADMIN;
                saveFirstLaunchStatus();
        }}


        private User getLocalUser() {
            return this.localUser;
        }

        public UserGroupTypes getLocalUserType() {
            return localUserType;
        }

        public Folder getLocalCurrentFolder() {
            return localCurrentFolder;
        }

        public void setLocalUser(User localUser) {
            this.localUser = localUser;
        }

        public void setLocalUserType(UserGroupTypes localUserType) {
            this.localUserType = localUserType;
        }

        public void setLocalCurrentFolder(Folder localCurrentFolder) {
            this.localCurrentFolder = localCurrentFolder;
        }
    }

    private static final ThreadLocal<SessionState> sessionStateThreadLocal = new ThreadLocal<SessionState>() {
        @Override
        protected SessionState initialValue() {
            return new SessionState();
        }
     };


    public static CurrentSessionState getCurrentSession() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentSessionState();
        }
        return INSTANCE;
    }

    public void setNoUser() {
        sessionStateThreadLocal.get().setLocalUserType(UserGroupTypes.GUEST);
        sessionStateThreadLocal.get().setLocalUser(null);
    }

    public Folder getCurrentFolder() {
        Folder currFolder = sessionStateThreadLocal.get().getLocalCurrentFolder();
        if (currFolder == null) {
            setInitialCurrentFolder();
        }
        return sessionStateThreadLocal.get().getLocalCurrentFolder();
    }

    public void setInitialCurrentFolder() {
        Folder rootFolder = DAOFactory.getDAO().findFolderInstanceByName("ROOT");
        if (rootFolder != null) {
            sessionStateThreadLocal.get().setLocalCurrentFolder(rootFolder);

        } else {
            Folder folder = DAOFactory.getDAO().create(Folder.class);
            folder.setName("ROOT");
            sessionStateThreadLocal.get().setLocalCurrentFolder(folder);
            DAOFactory.getDAO().save(folder);
        }
    }

    public void setCurrentFolder(Folder folder) {
        sessionStateThreadLocal.get().setLocalCurrentFolder(folder);
    }


    public UserGroupTypes getUserGroupTypeLocal() {

        UserGroupTypes localUserType = sessionStateThreadLocal.get().getLocalUserType();
        if (localUserType == null) {
            sessionStateThreadLocal.get().setLocalUserType(UserGroupTypes.GUEST);
        }
        return sessionStateThreadLocal.get().getLocalUserType();
    }

    public User getSignedInUser() {
        return sessionStateThreadLocal.get().getLocalUser();
    }

    public Set<BigInteger> getAllSignedInUsers() {
        return activeUserIds;
    }

    public void setSignedInUser(User user) {



        if (activeUserIds.contains(user.getId())) {
            log.info("User already signed in");
        } else {
            activeUserIds.add(user.getId());
            sessionStateThreadLocal.get().setLocalUser(user);
            sessionStateThreadLocal.get().setLocalUserType(user.getGroup());
        }
    }

    public void removeUserFromSignedInUsers() {
        activeUserIds.remove(sessionStateThreadLocal.get().getLocalUser().getId());
    }


    private static boolean checkIfFirstLaunch() {
        File serializedFirstLaunchFile = new File(SerializationConstants.SERIALIZED_FIRST_LAUNCH_FILE_PATH);
        if (serializedFirstLaunchFile.exists() && !serializedFirstLaunchFile.isDirectory()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(serializedFirstLaunchFile));
                Type varType = new TypeToken<Boolean>() {
                }.getType();
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
        Type varType = new TypeToken<Boolean>() {
        }.getType();
        String firstLaunchStatusJSON = gson.toJson(firstLaunchStatus, varType);

        try {
            //new File(SerializationConstants.SERIALIZED_FIRST_LAUNCH_FILE_PATH.getParent()).mkdirs();
            FileUtils.forceMkdir(new File(SerializationConstants.SERIALIZED_OBJECT_FOLDER_NAME));
            FileWriter writer = new FileWriter(SerializationConstants.SERIALIZED_FIRST_LAUNCH_FILE_PATH);
            writer.write(firstLaunchStatusJSON);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

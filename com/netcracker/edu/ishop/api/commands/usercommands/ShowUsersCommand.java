package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Map;

public class ShowUsersCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowUsersCommand.class);

    public ShowUsersCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "show_users";
    }

    @Override
    public String getDescription() {
        return "This command shows saved users in current implementation of data structure";
    }


    @Override
    public String execute(String[] cmdArgs) {

        //daoInstance.findUserByName("u1");

        Map userMap = daoInstance.getDataMapByABOName(User.class);

        if (userMap != null && userMap.size() > 0) {
//            for (Object obj : userMap.values()) {
//                log.info(obj);
//            }
            //log.info(userMap.keySet());
            //setStatusMessage(userMap.values().toString());
            //log.info(getStatusMessage());

            String msg = userMap.values().toString();
            return CommandFormat.build("OK", "U010", msg);
            

        }
        else {
            //setStatusMessage("No users were found for showing. Try to add at least one user by 'register_user' command");
            //log.info(getStatusMessage());

            String msg = "No users were found for showing. Try to add at least one user by 'register_user' command";
            return CommandFormat.build("ERROR", "U011", msg);
            

        }

    }

    @Override
    public String toString() {
        return null;
    }
}

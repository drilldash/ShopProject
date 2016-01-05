package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import org.apache.log4j.Logger;

import java.util.Map;

public class ShowUsersCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(ShowUsersCommand.class);

    public ShowUsersCommand(DAO daoInstance) {
        super(daoInstance);
    }

    @Override
    public String getName() {
        return "show_users";
    }

    @Override
    public String getDescription() {
        return "This command shows saved users in current implementation of data structure\n";
    }

    @Override
    public void execute(String[] cmdArgs) {
        Map userMap = daoInstance.getMapShardByABOName(User.class);

        if (userMap.size() > 0) {
//            for (Object obj : userMap.values()) {
//                log.info(obj);
//            }
            //log.info(userMap.keySet());
            log.info(userMap.values());
        }
        else {
            log.info("No users were found for showing. Try to add at least one user by 'register_user' command");
        }

    }

    @Override
    public String toString() {
        return null;
    }
}

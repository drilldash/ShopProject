package netcracker.edu.ishop.api.commands;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;

public class DeleteUserCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(DeleteUserCommand.class);

    public DeleteUserCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAdminAccessGroup();
    }


    @Override
    public String getName() {
        return "delete_user";
    }

    @Override
    public String getDescription() {
        return "Usage: delete_user [username]";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if (cmdArgs.length > 1 || cmdArgs.length < 1) {
            log.info("Wrong number of arguments in " + "\"" + getName() + "\"");
        } else {

            String username = cmdArgs[0];

            User user = daoInstance.findUserByName(username);

            if (user != null) {
                daoInstance.delete(user);
                log.info(username + " has been deleted from data-structure!");
            } else {
                log.info("Username " + username + " not found for deleting");
            }
        }
    }


    @Override
    public String toString() {
        return null;
    }
}

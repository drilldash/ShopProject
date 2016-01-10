package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;

public class RenameUserCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(RenameUserCommand.class);

    public RenameUserCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAdminAccessGroup();
    }

    @Override
    public String getName() {
        return "rename_user";
    }

    @Override
    public String getDescription() {
        return "Usage: rename_user [old_name] [new_name]";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
            log.info("Wrong number of arguments in " + "\"" + getName() + "\"");
        } else {

            String oldUsername = cmdArgs[0];
            String newUsername = cmdArgs[1];

            // class cast!!!
            User user = daoInstance.findUserByName(oldUsername);
            //User abo = daoInstance.create(User.class); why this doesn't work?

            if (user == null){
                log.info("No such username has been found in data-structure");
            }
            else if (daoInstance.findUserByName(newUsername) != null) {
                log.info("Cannot rename username " + oldUsername + " to " + newUsername + ". It's already taken.");
            }
            else if (user != null && daoInstance.findUserByName(newUsername) == null) {
                user.setName(newUsername);
                log.info("Username has been successfully updated from \"" + oldUsername + "\" to \"" + newUsername + "\"");
            }

        }


    }

    @Override
    public String toString() {
        return null;
    }
}

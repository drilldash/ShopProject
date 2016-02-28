package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

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
    public String execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
            return CommandFormat.build("ERROR", "----", "Wrong number of arguments in " + "\"" + getName() + "\"");

        } else {

            String oldUsername = cmdArgs[0];
            String newUsername = cmdArgs[1];

            // class cast!!!
            User user = daoInstance.findUserByName(oldUsername);
            //User abo = daoInstance.create(User.class); why this doesn't work?

            if (user == null){
                //setStatusMessage("No such username has been found in data-structure");
                //log.info(getStatusMessage());

                String msg = "No such username has been found in data-structure";
                return CommandFormat.build("ERROR", "U007", msg);
                

            }
            else if (daoInstance.findUserByName(newUsername) != null) {
                //setStatusMessage("Cannot rename username " + oldUsername + " to " + newUsername + ". It's already taken.");
                //log.info(getStatusMessage());

                String msg = "Cannot rename username " + oldUsername + " to " + newUsername + ". It's already taken.";
                return CommandFormat.build("ERROR", "U008", msg);
                

            }
            else if (user != null && daoInstance.findUserByName(newUsername) == null) {
                user.setName(newUsername);

                //setStatusMessage("Username has been successfully updated from \"" + oldUsername + "\" to \"" + newUsername + "\"");
                //log.info(getStatusMessage());

                String msg = "Username has been successfully updated from \"" + oldUsername + "\" to \"" + newUsername + "\"";
                return CommandFormat.build("ERROR", "U009", msg);
                


            }

        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");

    }

    @Override
    public String toString() {
        return null;
    }
}

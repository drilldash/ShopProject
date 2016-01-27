package netcracker.edu.ishop.api.commands.usercommands;
import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

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
            setStatusMessage("Wrong number of arguments in " + "\"" + getName() + "\"");
            log.info(getStatusMessage());
        } else {

            String username = cmdArgs[0];

            User user = daoInstance.findUserByName(username);

            if (user != null) {
                daoInstance.delete(user);
                //setStatusMessage(username + " has been deleted from data-structure!");
                //log.info(getStatusMessage());

                String msg = username + " has been deleted from data-structure!";
                setAllCmdData("OK", "U001", msg);
                log.info(getCmdContent());


            } else {
                //setStatusMessage("Username " + username + " not found for deleting");
                //log.info(getStatusMessage());

                String msg = "Username " + username + " not found for deleting";
                setAllCmdData("ERROR", "U002", msg);
                log.info(getCmdContent());
            }
        }
    }


    @Override
    public String toString() {
        return null;
    }
}

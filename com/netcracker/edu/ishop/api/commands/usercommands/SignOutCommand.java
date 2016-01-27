package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class SignOutCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(SignInCommand.class);

    public SignOutCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "sign_out";
    }

    @Override
    public String getDescription() {
        return "Usage: sign_out [username]";
    }

    @Override
    public void execute(String[] cmdArgs) throws IllegalArgumentException {

        if (cmdArgs.length > 1 || cmdArgs.length < 1) {
            throw new IllegalArgumentException("Wrong number of arguments in " + "\"" + getName() + "\"");
        }

        else {
            String username = cmdArgs[0];
            User user = daoInstance.findUserByName(username);
            if (user != CurrentSessionState.getSignedInUser()) {

                //setStatusMessage("No such username found in data-structure, you should register first");
                //log.info(getStatusMessage());

                String msg ="Entered username doesn't match the actual signed in username";
                setAllCmdData("ERROR", "U016", msg);
                log.info(getCmdContent());


            } else {
                CurrentSessionState.removeUserFromSignedInUsers();
                CurrentSessionState.setNoUser();

                //setStatusMessage("User " + username + " has been successfully signed out");
                //log.info(getStatusMessage());

                String msg = "User " + username + " has been successfully signed out";
                setAllCmdData("OK", "U017", msg);
                log.info(getCmdContent());

            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}


package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;

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
            if (user == null) {
                setStatusMessage("No such username found in data-structure, you should register first");
                log.info(getStatusMessage());
            } else {
                CurrentSessionState.removeUserFromSignedInUsers();
                CurrentSessionState.setNoUser();
                setStatusMessage("User " + username + " has been successfully signed out");
                log.info(getStatusMessage());
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}


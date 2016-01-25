package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;
import java.util.Arrays;

public class SignInCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(SignInCommand.class);

    public SignInCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "sign_in";
    }

    @Override
    public String getDescription() {
        return "Usage: sign_in [username] [password]";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if ((cmdArgs.length > 2 || cmdArgs.length < 2) && (CurrentSessionState.getSignedInUser() == null)) {
            //log.info("Wrong number of arguments in " + "\"" + getName() + "\"");
            throw new IllegalArgumentException("Wrong number of arguments in " + "\"" + getName() + "\"");
        }
        else if (CurrentSessionState.getSignedInUser() != null) {
            setStatusMessage("User " + CurrentSessionState.getSignedInUser().getName() + " should sign out first");
            log.info(getStatusMessage());
        }
        else {
            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();

            User user = daoInstance.findUserByName(username);

            if (user == null) {
                setStatusMessage("No such username found in data-structure, you should register first");
                log.info(getStatusMessage());
            } else if (user != null && !Arrays.equals(user.getPassword(), password)) {
                setStatusMessage("Passwords are not matching each other");
                log.info(getStatusMessage());
            } else {
                CurrentSessionState.setSignedInUser(user);
                setStatusMessage("User \"" + username + "\" has been successfully signed in!");
                log.info(getStatusMessage());
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}

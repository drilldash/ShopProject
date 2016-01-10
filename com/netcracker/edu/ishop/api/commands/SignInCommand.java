package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.EnumSet;

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
            log.info("User " + CurrentSessionState.getSignedInUser().getName() + " should sign out first");
        }
        else {
            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();

            User user = daoInstance.findUserByName(username);

            if (user == null) {
                log.info("No such username found in data-structure, you should register first");
            } else if (user != null && !Arrays.equals(user.getPassword(), password)) {
                log.info("Passwords are not matching each other");
            } else {
                CurrentSessionState.setSignedInUser(user);
                log.info("User \"" + username + "\" has been successfully signed in!");
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}

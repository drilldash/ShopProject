package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class SignOutCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(SignInCommand.class);

    public SignOutCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "sign_out";
    }

    @Override
    public String getDescription() {
        return "Usage: sign_out";
    }

    @Override
    public String execute(String[] cmdArgs) throws IllegalArgumentException {

        if (cmdArgs.length > 0) {

            return CommandFormat.build("ERROR", "----", "Wrong number of arguments in " + "\"" + getName() + "\"");
        }

        else {
            //String username = cmdArgs[0];
            //User user = daoInstance.findUserByName(username);
            if (CurrentSessionState.getCurrentSession().getSignedInUser() == null) {

                //setStatusMessage("No such username found in data-structure, you should register first");
                //log.info(getStatusMessage());

                String msg ="You're a not signed in as user.";
                return CommandFormat.build("ERROR", "----", msg);
                


            } else {
                String loggedUsername = CurrentSessionState.getCurrentSession().getSignedInUser().getName();
                CurrentSessionState.getCurrentSession().removeUserFromSignedInUsers();
                CurrentSessionState.getCurrentSession().setNoUser();

                //setStatusMessage("User " + username + " has been successfully signed out");
                //log.info(getStatusMessage());

                String msg = "User " + loggedUsername + " has been successfully signed out";
                return CommandFormat.build("OK", "----", msg);
                

            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}


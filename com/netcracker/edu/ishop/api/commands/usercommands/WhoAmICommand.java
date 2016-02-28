package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class WhoAmICommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(WhoAmICommand.class);

    public WhoAmICommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    public boolean checkAccess(UserGroupTypes groupType) throws ClassCastException, NullPointerException {
        return defaultLevelAccess.contains(groupType);
    }

    @Override
    public String getName() {
        return "whoami";
    }

    @Override
    public String getDescription() {
        return "This command shows currently logged username";
    }

    @Override
    public String execute(String[] cmdArgs) {

            try {
                String currentName = CurrentSessionState.getCurrentSession().getSignedInUser().getName();

                //setStatusMessage("I am " + currentName);
                //log.info(getStatusMessage());

                String msg = "I am " + currentName;
                return CommandFormat.build("OK", "----", msg);
                

            } catch (NullPointerException NP) {

                //setStatusMessage("You're not signed in (GUEST)");
                //log.info(getStatusMessage());

                String msg = "You're not signed in (" + CurrentSessionState.getCurrentSession().getUserGroupTypeLocal().toString() + ")";
                return CommandFormat.build("INFO", "----", msg);
                

            }
        }
    @Override
    public String toString() {
        return null;
    }
}

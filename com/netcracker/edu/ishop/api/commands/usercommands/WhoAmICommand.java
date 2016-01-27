package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
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
    public void execute(String[] cmdArgs) {

            try {
                String currentName = CurrentSessionState.getSignedInUser().getName();

                //setStatusMessage("I am " + currentName);
                //log.info(getStatusMessage());

                String msg = "I am " + currentName;
                setAllCmdData("OK", "U020", msg);
                log.info(getCmdContent());

            } catch (NullPointerException NP) {

                //setStatusMessage("You're not signed in (GUEST)");
                //log.info(getStatusMessage());

                String msg = "You're not signed in (GUEST)";
                setAllCmdData("ERROR", "U021", msg);
                log.info(getCmdContent());

            }
        }
    @Override
    public String toString() {
        return null;
    }
}

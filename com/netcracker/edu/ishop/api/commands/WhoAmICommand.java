package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;

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
                setStatusMessage("I am " + currentName);
                log.info(getStatusMessage());
            } catch (NullPointerException NP) {
                setStatusMessage("You're not signed in (GUEST)");
                log.info(getStatusMessage());
            }
        }
    @Override
    public String toString() {
        return null;
    }
}

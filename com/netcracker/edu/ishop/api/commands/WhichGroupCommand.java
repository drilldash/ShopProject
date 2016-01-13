package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;


public class WhichGroupCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(WhichGroupCommand.class);

    public WhichGroupCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "which_group";
    }

    @Override
    public String getDescription() {
        return "Shows current group to which current signed-in user belongs otherwise shows 'GROUP:GUEST'";
    }

    @Override
    public void execute(String[] cmdArgs) {
        try {
            String currentGroup = CurrentSessionState.getUserGroupTypeLocal().toString();
            log.info("GROUP:" + currentGroup);
        }
        catch (NullPointerException NP) {
            log.info(NP);
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
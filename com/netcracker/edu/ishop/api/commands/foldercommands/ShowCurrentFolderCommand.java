package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class ShowCurrentFolderCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowCurrentFolderCommand.class);

    public ShowCurrentFolderCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "current_folder";
    }

    @Override
    public String getDescription() {
        return "Shows current folder.";
    }

    @Override
    public void execute(String[] cmdArgs) {

        try {
            String msg = CurrentSessionState.getCurrentFolder().toString();
            setAllCmdData("OK", "F009", msg);
            log.info(getCmdContent());


        } catch (NullPointerException npe) {
            log.info(npe);
            CurrentSessionState.setInitialCurrentFolder();
        }
    }

    @Override
    public String toString() {
        return null;
    }
}

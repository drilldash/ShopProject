package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
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
    public String execute(String[] cmdArgs) {
        try {
            String msg = CurrentSessionState.getCurrentSession().getCurrentFolder().toString();
            return CommandFormat.build("OK", "----", msg);


        } catch (NullPointerException npe) {
            log.info(npe);
            CurrentSessionState.getCurrentSession().setInitialCurrentFolder();
            return CommandFormat.build("ERROR", "----", npe.toString());
        }
    }

    @Override
    public String toString() {
        return null;
    }
}

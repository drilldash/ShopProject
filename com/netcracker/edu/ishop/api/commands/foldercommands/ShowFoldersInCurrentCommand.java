package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowFoldersInCurrentCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowFoldersInCurrentCommand.class);

    public ShowFoldersInCurrentCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "ls_folders";
    }

    @Override
    public String getDescription() {
        return "Shows folders in current folder(useful when using \"change_folder\" command)";
    }

    @Override
    public void execute(String[] cmdArgs) {

        Folder currFolder = CurrentSessionState.getCurrentFolder();

        List<Folder> listChildFolders = daoInstance.findAllFoldersWithGivenParentId(currFolder.getId());

        String folderNames = "Possible variants to change directory to are:\n";
        for (Folder folder: listChildFolders) {
            folderNames += folder.getName() + "\n";
        }
        //setStatusMessage(folderNames);
        //log.info(getStatusMessage());
        if (listChildFolders.size() > 0) {
            String msg = folderNames;
            setAllCmdData("OK", "F013", msg);
            log.info(getCmdContent());
        }
        else {
            String msg = "There are no possible variants to change directory to";
            setAllCmdData("ERROR", "F014", msg);
            log.info(getCmdContent());
        }

    }

    @Override
    public String toString() {
        return null;
    }
}

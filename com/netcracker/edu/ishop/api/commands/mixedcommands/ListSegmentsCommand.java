package netcracker.edu.ishop.api.commands.mixedcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.commands.itemcommands.AddItemCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class ListSegmentsCommand extends AbstractCommand {

    private static final Logger log = Logger.getLogger(AddItemCommand.class);

    public ListSegmentsCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return "'ls' stands for 'list segments'. It's a Linux command showing folders and files in current directory.";
    }

    @Override
    public void execute(String[] cmdArgs) {

        Folder currFolder = CurrentSessionState.getCurrentFolder();

        List<String> listOfSegments = daoInstance.findOnlyItemsAndFoldersWithGivenParentId(currFolder.getId());

        String cmdOutput = "There are next items and folders in current directory:\n";
        for (String record: listOfSegments) {
            cmdOutput += record + "\n";
        }
        //setStatusMessage(folderNames);
        //log.info(getStatusMessage());
        if ( listOfSegments.size() > 0) {
            String msg = cmdOutput;
            setAllCmdData("OK", "MS01", msg);
            log.info(getCmdContent());
        }
        else {
            String msg = "There are no content to show in current directory";
            setAllCmdData("ERROR", "MF01", msg);
            log.info(getCmdContent());
        }

    }

    @Override
    public String toString() {
        return null;
    }
}

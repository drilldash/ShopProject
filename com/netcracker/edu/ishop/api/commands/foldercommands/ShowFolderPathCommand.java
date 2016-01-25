package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.math.BigInteger;

public class ShowFolderPathCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowFolderPathCommand.class);

    public ShowFolderPathCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "pwd";
    }

    @Override
    public String getDescription() {
        return "Show current folder and all folders above";
    }

    @Override
    public void execute(String[] cmdArgs) {

        Folder folder = CurrentSessionState.getCurrentFolder();

        BigInteger parentRefId = BigInteger.ZERO;

        String tab = "--";
        String filePathTreeStructure = folder.getName() + "\n";

        while (parentRefId != null) {

            folder = daoInstance.findABOInstanceById(Folder.class, folder.getParentFolderId());
            //log.info(folder.getName());

            //System.exit(0);
            filePathTreeStructure += tab + folder.getName() + "\n";
            tab+= "--";
            parentRefId = folder.getParentFolderId();


        }
        setStatusMessage("Result:\n" + filePathTreeStructure);
        log.info(getStatusMessage());

    }

    @Override
    public String toString() {
        return null;
    }
}

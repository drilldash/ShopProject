package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public String execute(String[] cmdArgs) {

        Folder folder = CurrentSessionState.getCurrentSession().getCurrentFolder();

        BigInteger parentRefId = BigInteger.ZERO;

        List<String> filePathList = new ArrayList<String>();
        filePathList.add(CurrentSessionState.getCurrentSession().getCurrentFolder().getName());

        if (folder.getParentFolderId() != null) {
            while (parentRefId != null) {

                log.info(folder);
                folder = daoInstance.findABOInstanceById(Folder.class, folder.getParentFolderId());
                //log.info(folder.getName());

                //System.exit(0);
                if (folder != null) {
                    filePathList.add(folder.getName());
                    parentRefId = folder.getParentFolderId();
                } else {
                    break;
                }

            }
        }
        //setStatusMessage("Result:\n" + filePathTreeStructure);
        //log.info(getStatusMessage());
        Collections.reverse(filePathList);

        //reversing the String
        //String msg = "Result:\n" + new StringBuilder(filePathTreeStructure).reverse().toString();
        String msg = "Result:\n" + printFilePath(filePathList);


        return CommandFormat.build("OK", "F010", msg);


    }

    @Override
    public String toString() {
        return null;
    }

    private String printFilePath(List<String> filePathList) {
        String output = "/";
        for (String record : filePathList) {
            output += record + "/";

        }
        return output;
    }

}

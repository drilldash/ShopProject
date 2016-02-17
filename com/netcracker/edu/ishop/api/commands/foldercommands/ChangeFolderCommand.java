package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class ChangeFolderCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(CreateFolderCommand.class);


    public ChangeFolderCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String getDescription() {
        return "Changes directory into one of child directories. Use also \"ls_folders\".";
    }

    @Override
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length > 1 || cmdArgs.length < 1) {
            setStatusMessage("Wrong number of arguments in " + "\"" + getName() + "\"");
            log.info(getDescription() + "\n" + getStatusMessage());

        } else {

            if (!cmdArgs[0].equals("../")) {
                String folderName = cmdArgs[0];

                Folder currFolder = CurrentSessionState.getCurrentFolder();

                log.info(currFolder.getId());
                List<Folder> listChildFolders = daoInstance.findAllFoldersWithGivenParentId(currFolder.getId());
                Folder givenFolder = daoInstance.findFolderInstanceByName(folderName);

                //if there are folders which we can change to
                if (givenFolder != null && listChildFolders.size() > 0) {
                    if (listChildFolders.contains(givenFolder)) {
                        CurrentSessionState.setCurrentFolder(givenFolder);

                        String msg = ("You're in folder \"" + folderName + "\"!");
                        setAllCmdData("OK", "F001", msg);

                        log.info(getCmdContent());

                        //there are no folders in current folders. Changing is impossible.
                    } else if (listChildFolders.size() == 0) {

                        String msg = "There is no child folders in current folder \"" + currFolder.getName() + "\"";
                        setAllCmdData("ERROR", "F002", msg);

                        log.info(getCmdContent());
                    }

                    //entered name of folder doesn't match any folder
                } else if (givenFolder == null) {

                    String msg = "No such folder exists " + "\nTry to use \'ls_folders\' command";
                    setAllCmdData("ERROR", "F003", msg);

                    log.info(getCmdContent());

                    // entered folder name matches some folder, but this folder doesn't belong to this folder
                    // OR entered folder name matches to ROOT folder which (hopefully) exists but its parentID is null.

                } else if ((givenFolder != null) && (givenFolder.getParentFolderId() == null
                        || !givenFolder.getParentFolderId().equals(currFolder.getId()))) {

                    String msg = "No such folder exists in current folder \"" + currFolder.getName() +
                            "\"" + "\nTry to use \'ls_folders\' command";

                    setAllCmdData("ERROR", "F004", msg);

                    log.info(getCmdContent());
                }


            } else {
                Folder currFolder = CurrentSessionState.getCurrentFolder();
                Folder targetFolder = daoInstance.findParentFoldersWithGivenParentId(currFolder.getParentFolderId());
                if (targetFolder != null) {
                    CurrentSessionState.setCurrentFolder(targetFolder);


                    String msg = "Successfully changed to one-up folder \"" + targetFolder.getName() + "\"!";
                    setAllCmdData("OK", "F005", msg);

                    log.info(getCmdContent());

                } else {
                    String msg = "Can't change into parent folder for folder \"" + currFolder.getName() + "\"";
                    setAllCmdData("ERROR", "F006", msg);

                    log.info(getCmdContent());
                }

            }


        }
    }


    @Override
    public String toString() {
        return null;
    }
}

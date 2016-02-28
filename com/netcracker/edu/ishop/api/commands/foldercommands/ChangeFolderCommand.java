package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
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
    public String execute(String[] cmdArgs) {
        String message;
        if (cmdArgs.length > 1 || cmdArgs.length < 1) {
            message = "Wrong number of arguments in " + "\"" + getName() + "\"";
            return CommandFormat.build("ERROR", "----", message);

        } else {

            if (!cmdArgs[0].equals("../")) {
                String folderName = cmdArgs[0];

                Folder currFolder = CurrentSessionState.getCurrentSession().getCurrentFolder();

                //log.info(currFolder.getId());
                List<Folder> listChildFolders = daoInstance.findAllFoldersWithGivenParentId(currFolder.getId());
                Folder givenFolder = daoInstance.findFolderInstanceByName(folderName);

                //if there are folders which we can change to
                if (givenFolder != null && listChildFolders.size() > 0) {
                    if (listChildFolders.contains(givenFolder)) {
                        CurrentSessionState.getCurrentSession().setCurrentFolder(givenFolder);

                        String msg = ("You're in folder \"" + folderName + "\"!");
                        return CommandFormat.build("OK", "----", msg);

                    //there are no folders in current folders. Changing is impossible.
                    } else if (listChildFolders.size() == 0) {

                        String msg = "There is no child folders in current folder \"" + currFolder.getName() + "\"";
                        return CommandFormat.build("ERROR", "----", msg);
                    }

                //entered name of folder doesn't match any folder
                } else if (givenFolder == null) {

                    String msg = "No such folder exists " + "\nTry to use \'ls_folders\' command";
                    return CommandFormat.build("ERROR", "----", msg);

                // entered folder name matches some folder, but this folder doesn't belong to this folder
                // OR entered folder name matches to ROOT folder which (hopefully) exists but its parentID is null.

                } else if ((givenFolder != null) && (givenFolder.getParentFolderId() == null
                        || !givenFolder.getParentFolderId().equals(currFolder.getId()))) {

                    String msg = "No such folder exists in current folder \"" + currFolder.getName() +
                            "\"" + "\nTry to use \'ls_folders\' command";

                    return CommandFormat.build("ERROR", "----", msg);

                }


            } else {
                Folder currFolder = CurrentSessionState.getCurrentSession().getCurrentFolder();
                Folder targetFolder = daoInstance.findParentFoldersWithGivenParentId(currFolder.getParentFolderId());
                if (targetFolder != null) {
                    CurrentSessionState.getCurrentSession().setCurrentFolder(targetFolder);


                    String msg = "Successfully changed to one-up folder \"" + targetFolder.getName() + "\"!";
                    return CommandFormat.build("OK", "----", msg);



                } else {
                    String msg = "Can't change into parent folder for folder \"" + currFolder.getName() + "\"";
                    return CommandFormat.build("ERROR", "----", msg);


                }

            }
        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");
    }


    @Override
    public String toString() {
        return null;
    }
}

package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class CreateFolderCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(CreateFolderCommand.class);

    public CreateFolderCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "create_folder";
    }

    @Override
    public String getDescription() {
        return "Creates folder. Usage: create_folder [folder_name]";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if (cmdArgs.length > 1 || cmdArgs.length < 1) {
            setStatusMessage("Wrong number of arguments in " + "\"" + getName() + "\"");
            log.info(getStatusMessage());

        } else {

            String folderName = cmdArgs[0];

            Folder folder = daoInstance.create(Folder.class);

            if (daoInstance.findFolderInstanceByName(folderName) == null) {

                folder.setName(folderName);

                if (CurrentSessionState.getCurrentFolder() == null) {
                    folder.setParentFolderId(null);
                    CurrentSessionState.setCurrentFolder(folder);
                } else {
                    folder.setParentFolderId(CurrentSessionState.getCurrentFolder().getId());
                    //CurrentSessionState.setCurrentFolder(folder);
                }

                daoInstance.save(folder);
                String msg = "Folder \"" + folder.getName() + "\" has been saved in data-structure!";
                setAllCmdData("OK", "F007", msg);
                log.info(getCmdContent());

            } else {

                String msg = "Folder \"" + folder.getName() + "\" already exists in data-structure!";
                setAllCmdData("ERROR", "F008", msg);
                log.info(getCmdContent());

                UniqueIDGenerator.getInstance().decrementID();
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}

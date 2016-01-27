package netcracker.edu.ishop.api.commands.foldercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Map;

public class ShowFolderRecordsCommand extends AbstractCommand {

        public static final Logger log = Logger.getLogger(ShowFolderRecordsCommand.class);

        public ShowFolderRecordsCommand(DAO daoInstance) {
            super(daoInstance);
            this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
        }

        @Override
        public String getName() {
            return "show_folders";
        }

        @Override
        public String getDescription() {
            return "This command shows saved folders in current implementation of data structure";
        }


        @Override
        public void execute(String[] cmdArgs) {

            //daoInstance.findUserByName("u1");

            Map folderMap = daoInstance.getDataMapByABOName(Folder.class);

            if (folderMap != null && folderMap.size() > 0) {
//            for (Object obj : userMap.values()) {
//                log.info(obj);
//            }
                //log.info(userMap.keySet());
                //setStatusMessage(folderMap.values().toString());
                //log.info(getStatusMessage());

                String msg = folderMap.values().toString();
                setAllCmdData("OK", "F011", msg);
                log.info(getCmdContent());

            }
            else {
                //setStatusMessage("No folders were found for showing. Try to add at least one folder by 'create_folder' command");
                //log.info(getStatusMessage());

                String msg = "No folders were found for showing. \n Try to add at least one folder by 'create_folder' command";
                setAllCmdData("ERROR", "F012", msg);
                log.info(getCmdContent());

            }

        }

        @Override
        public String toString() {
            return null;
        }
    }

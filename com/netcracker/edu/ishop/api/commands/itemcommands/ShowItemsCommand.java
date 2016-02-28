package netcracker.edu.ishop.api.commands.itemcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Map;

public class ShowItemsCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowItemsCommand.class);

    public ShowItemsCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();

    }

    @Override
    public String getName() {
        return "show_items";
    }

    @Override
    public String getDescription() {
        return "Shows all items in data-structure";
    }

    @Override
    public String execute(String[] cmdArgs) {

        //daoInstance.findUserByName("u1");

        Map itemMap = daoInstance.getDataMapByABOName(Item.class);

        if (itemMap != null && itemMap.size() > 0) {
//            for (Object obj : userMap.values()) {
//                log.info(obj);
//            }
            //log.info(userMap.keySet());
            //setStatusMessage(folderMap.values().toString());
            //log.info(getStatusMessage());

            String msg = itemMap.values().toString();
            return CommandFormat.build("OK", "----", msg);


        }
        else {
            //setStatusMessage("No folders were found for showing. Try to add at least one folder by 'create_folder' command");
            //log.info(getStatusMessage());

            String msg = "No items were found for showing. \n Try to add at least one folder by 'add_item' command";
            return CommandFormat.build("ERROR", "----", msg);
        }

    }

    @Override
    public String toString() {
        return null;
    }
}

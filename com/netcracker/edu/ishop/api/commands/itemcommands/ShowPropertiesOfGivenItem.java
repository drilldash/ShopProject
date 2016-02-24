package netcracker.edu.ishop.api.commands.itemcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.ItemPropertyValue;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ShowPropertiesOfGivenItem extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowPropertiesOfGivenItem.class);

    public ShowPropertiesOfGivenItem(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "show_item_properties";
    }

    @Override
    public String getDescription() {
        return "Shows all properties of selected item by name in current directory. Usage: 'show_item_properties [name]'";
    }

    @Override
    public void execute(String[] cmdArgs) {


        Folder currFolder = CurrentSessionState.getCurrentFolder();
        List<Item> itemList = daoInstance.findItemsWithGivenFolderId(currFolder.getId());

        if (itemList.size() > 0 && cmdArgs.length == 1) {

            String givenItemName = cmdArgs[0];

            //Item selectedItem = daoInstance.findAbstractBusinessObjByName(Item.class, givenItemName);

            Item selectedItem = searchForItemObjectInGivenList(itemList, givenItemName);


            List<ItemPropertyValue> propertiesList = selectedItem.getCharsVals();

            if (propertiesList != null) {
                String msg = "Properties of " + givenItemName + " are " + propertiesList.toString();
                setAllCmdData("OK", "IS06", msg);
                log.info(getCmdContent());
            } else {
                String msg = "There are no properties for " + givenItemName;
                setAllCmdData("ERROR", "IF07", msg);
                log.info(getCmdContent());

            }


        } else if (cmdArgs.length == 0) {
            String msg = "No item name has been entered to show properties in current directory \"" + CurrentSessionState.getCurrentFolder().getName() + "\"";
            setAllCmdData("ERROR", "IF09", msg);
            log.info(getCmdContent());
        } else if (cmdArgs.length >= 2) {
            String msg = "Too many arguments. " + getDescription();
            setAllCmdData("ERROR", "I10", msg);
            log.info(getCmdContent());
        } else if (itemList.size() == 0) {
            String msg = "No items are available for adding properties in current directory \"" + CurrentSessionState.getCurrentFolder().getName() + "\"";
            setAllCmdData("ERROR", "IF08", msg);
            log.info(getCmdContent());
        }

    }

        @Override
        public String toString () {
            return null;
        }


    private Item searchForItemObjectInGivenList(List<Item> itemList, String givenItemName) {
        for (Item item : itemList) {
            if (item.getName() != null && item.getName().contains(givenItemName)) {
                return item;
            }
            //something here
        }
        return null;
    }

}

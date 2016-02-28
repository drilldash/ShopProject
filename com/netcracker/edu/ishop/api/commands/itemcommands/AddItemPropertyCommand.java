package netcracker.edu.ishop.api.commands.itemcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.ItemProperty;
import netcracker.edu.ishop.api.objects.ItemPropertyValue;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class AddItemPropertyCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(AddItemPropertyCommand.class);

    public AddItemPropertyCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "add_item_property";
    }

    @Override
    public String getDescription() {
        return "Adds custom property to selected item in current folder. Usage: add_item_property [item_name] [key1=val1] [key2=val2] [...]";
    }

    @Override
    public String execute(String[] cmdArgs) {

        Folder currFolder = CurrentSessionState.getCurrentSession().getCurrentFolder();
        List<Item> itemList = daoInstance.findItemsWithGivenFolderId(currFolder.getId());


        if (itemList.size() > 0 && cmdArgs.length >= 1) {

            //Item selectedItem = daoInstance.findAbstractBusinessObjByName(Item.class, givenItemName);

            String givenItemName = cmdArgs[0];
            Item selectedItem = daoInstance.searchForItemObjectInGivenList(itemList, givenItemName);

            String[] cmdItemArgData = null;
            if (cmdArgs.length >= 2) {
                cmdItemArgData = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
                //log.info(Arrays.toString(cmdItemArgData));

                if (selectedItem != null) {

                    for (String itemArg : cmdItemArgData) {
                        String[] itemParams = itemArg.split("=");
                        //log.info(Arrays.toString(itemParams));
                        //log.info(itemParams.length);
                        if (itemParams.length == 2) {
                            String itemKeyChar = itemParams[0];
                            String itemValChar = itemParams[1];

                            //log.info(itemKeyChar);
                            //add_item item1 10 k1=v1 k2=v2 k3=v3
                            // checking existence of a given item's property
                            ItemProperty itemProp = daoInstance.findAbstractBusinessObjByName(ItemProperty.class, itemKeyChar);
                            //log.info(itemProp);
                            if (itemProp == null) {
                                ItemProperty newItemProp = daoInstance.create(ItemProperty.class);
                                newItemProp.setName(itemKeyChar);
                                daoInstance.save(newItemProp);
                                //log.info(daoInstance.getDataMapByABOName(ItemProperty.class));

                                selectedItem.addPropertyWithVal(new ItemPropertyValue(newItemProp, itemValChar));
                            } else {
                                selectedItem.addPropertyWithVal(new ItemPropertyValue(itemProp, itemValChar));

                            }


                        }

                    }
                }

                String msg = "Following properties were added to " + givenItemName + ": " + Arrays.toString(cmdItemArgData);
                daoInstance.save(selectedItem);
                return CommandFormat.build("OK", "----", msg);


            }
        }
        if (itemList.size() == 0 && cmdArgs.length >= 1) {
            String msg = "No items are available for showing their properties in current directory \"" + CurrentSessionState.getCurrentSession().getCurrentFolder().getName() + "\"";
            return CommandFormat.build("ERROR", "----", msg);


        } else if (cmdArgs.length == 0) {
            String msg = "No item name has been entered to add properties to. Current directory:\"" + CurrentSessionState.getCurrentSession().getCurrentFolder().getName() + "\"";
            return CommandFormat.build("ERROR", "----", msg);


        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");
    }

    @Override
    public String toString() {
        return null;
    }


}

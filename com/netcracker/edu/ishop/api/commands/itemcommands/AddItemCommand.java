package netcracker.edu.ishop.api.commands.itemcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.ItemProperty;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.ItemPropertyValue;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class AddItemCommand extends AbstractCommand {

    // correct logger
    private static final Logger log = Logger.getLogger(AddItemCommand.class);

    public AddItemCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "add_item";
    }

    @Override
    public String getDescription() {
        return "Adds item to current folder. Usage:add_item [item_name] [key1=val1] [key2=val2] [...]";
    }

    @Override
    public String execute(String[] cmdArgs) {

        String itemName = cmdArgs[0];
        //BigInteger itemNum = new BigInteger(cmdArgs[1]);

        String[] cmdItemArgData = null;

        Item searchedItem = daoInstance.findItemByName(itemName);


        if (cmdArgs.length >= 2 && searchedItem == null) {
            cmdItemArgData = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
            //log.info(Arrays.toString(cmdItemArgData));

            Item item = daoInstance.create(Item.class);
            item.setFolderId(CurrentSessionState.getCurrentSession().getCurrentFolder().getId());
            item.setName(itemName);
            //item.setItemNum(itemNum);

            for (String itemArg : cmdItemArgData) {
                String[] itemParams = itemArg.split("=");
                //log.info(Arrays.toString(itemParams));
                //log.info(itemParams.length);
                if (itemParams.length == 2) {
                    String itemKeyChar = itemParams[0];
                    String itemValChar = itemParams[1];

                    //og.info(itemKeyChar);
                    //add_item item1 10 k1=v1 k2=v2 k3=v3
                    // checking existence of a given item's property
                    ItemProperty itemProp = daoInstance.findItemPropertyInstanceByName(itemKeyChar);
                    //log.info(itemProp);
                    if (itemProp == null) {
                        ItemProperty newItemProp = daoInstance.create(ItemProperty.class);
                        newItemProp.setName(itemKeyChar);
                        daoInstance.save(newItemProp);
                        //log.info(daoInstance.getDataMapByABOName(ItemProperty.class));

                        item.addPropertyWithVal(new ItemPropertyValue(newItemProp, itemValChar));
                    } else {
                        item.addPropertyWithVal(new ItemPropertyValue(itemProp, itemValChar));

                    }


                }

            }
            String msg = "Item " + itemName + "has been added to folder " + CurrentSessionState.getCurrentSession().getCurrentFolder();

            daoInstance.save(item);
            return CommandFormat.build("OK", "----", msg);

        }

        if (cmdArgs.length == 1 && searchedItem == null) {


            Item item = daoInstance.create(Item.class);
            item.setFolderId(CurrentSessionState.getCurrentSession().getCurrentFolder().getId());
            item.setName(itemName);
            //item.setItemNum(itemNum);
            String msg = "Item " + itemName + "has been added to folder " + CurrentSessionState.getCurrentSession().getCurrentFolder();
            //log.info(getCmdContent());
            daoInstance.save(item);
            return CommandFormat.build("OK", "IS01", msg);

        }
        if (cmdArgs.length == 0) {
            String msg = "Wrong number of arguments in " + "\"" + getName() + "\"";
            return CommandFormat.build("ERROR", "----", msg);
        }
        if (searchedItem != null) {
            String msg = "Item" + itemName + "already exists! ";
            return CommandFormat.build("ERROR", "----", msg);

        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");
    }

    @Override
    public String toString() {
        return null;
    }
}

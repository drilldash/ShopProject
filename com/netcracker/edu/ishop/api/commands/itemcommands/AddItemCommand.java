package netcracker.edu.ishop.api.commands.itemcommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.ItemProperty;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.ItemPropertyValue;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.Arrays;

public class AddItemCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(AddItemCommand.class);

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
        return "Adds item to current folder. Usage:add_item [item_name] [number] [key1=val1] [key2=val2] [...]";
    }

    @Override
    public void execute(String[] cmdArgs) {

        String itemName = cmdArgs[0];
        BigInteger itemNum = new BigInteger(cmdArgs[1]);

        String[] cmdItemArgData = null;
        if (cmdArgs.length >= 3) {
            cmdItemArgData = Arrays.copyOfRange(cmdArgs, 2, cmdArgs.length);
            log.info(Arrays.toString(cmdItemArgData));

            Item item = daoInstance.create(Item.class);
            item.setFolderId(CurrentSessionState.getCurrentFolder().getId());
            item.setName(itemName);
            item.setItemNum(itemNum);

            for (String itemArg : cmdItemArgData) {
                String[] itemParams = itemArg.split("=");
                log.info(Arrays.toString(itemParams));
                log.info(itemParams.length);
                if (itemParams.length == 2) {
                    String itemKeyChar = itemParams[0];
                    String itemValChar = itemParams[1];

                    log.info(itemKeyChar);
                    //add_item item1 10 k1=v1 k2=v2 k3=v3
                    // checking existence of a given item's property
                    ItemProperty itemProp = daoInstance.findAbstractBusinessObjByName(ItemProperty.class, itemKeyChar);
                    log.info(itemProp);
                    if ( itemProp == null) {
                            ItemProperty newItemProp = daoInstance.create(ItemProperty.class);
                            newItemProp.setName(itemKeyChar);
                            daoInstance.save(newItemProp);
                            log.info(daoInstance.getDataMapByABOName(ItemProperty.class));

                        item.addPropertyWithVal(new ItemPropertyValue(newItemProp, itemValChar));
                    }
                    else {
                        item.addPropertyWithVal(new ItemPropertyValue(itemProp, itemValChar));

                    }



                }

            }

            daoInstance.save(item);
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
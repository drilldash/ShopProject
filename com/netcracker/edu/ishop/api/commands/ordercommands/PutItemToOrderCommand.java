package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class PutItemToOrderCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(PutItemToOrderCommand.class);

    public PutItemToOrderCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "put";
    }

    @Override
    public String getDescription() {
        return "Puts item by its name to user's order. Usage:'put [itemName]' ";
    }
    //@SuppressWarnings("unchecked")
    @Override
    public void execute(String[] cmdArgs) {
        Folder currFolder = CurrentSessionState.getCurrentFolder();
        List<Item> itemList = daoInstance.findItemsWithGivenFolderId(currFolder.getId());

        if (itemList.size() > 0 && cmdArgs.length == 1) {
            String givenItemName = cmdArgs[0];
            Item selectedItem = daoInstance.searchForItemObjectInGivenList(itemList, givenItemName);

            if (selectedItem!=null) {
                User currUser = CurrentSessionState.getSignedInUser();
                if (currUser.getOrderId() == null) {
                    Order currOrder = daoInstance.create(Order.class);
                    currOrder.setText("RESERVED");
                    currOrder.setUserId(currUser.getId());
                    currOrder.addItemToOrder(selectedItem);
                    currUser.setOrderId(currOrder.getId());
                    daoInstance.save(currOrder);
                    daoInstance.save(currUser);
                    String msg = "'Order:id'" + currOrder.getId() + " has been created for user " + currUser.getName();
                    msg += "\n Item '" + selectedItem.getName() + "' has been added to Order:id" + currOrder.getId();

                    setAllCmdData("OK", "OS01", msg);
                    log.info(getCmdContent());
                }

                else if (currUser.getOrderId() != null) {
                    Order currOrder = daoInstance.findABOInstanceById(Order.class, currUser.getOrderId());
                    currOrder.addItemToOrder(selectedItem);
                    daoInstance.save(currOrder);

                    String msg = "\n Item '" + selectedItem.getName() + "' has been added to Order:id" + currOrder.getId();

                    setAllCmdData("OK", "OS01", msg);
                    log.info(getCmdContent());

                }
            }
            else if (selectedItem == null) {
                String msg = "No such item has been found in current directory. Name:" + givenItemName;
                setAllCmdData("ERROR", "OF02", msg);
                log.info(getCmdContent());
            }
        }


    }

    @Override
    public String toString() {
        return null;
    }



}

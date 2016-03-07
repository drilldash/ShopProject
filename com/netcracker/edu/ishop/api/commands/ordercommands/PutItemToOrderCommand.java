package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class PutItemToOrderCommand extends AbstractCommand {

    private static final Logger log = Logger.getLogger(PutItemToOrderCommand.class);

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
    public String execute(String[] cmdArgs) {
        Folder currFolder = CurrentSessionState.getCurrentSession().getCurrentFolder();
        List<Item> itemList = daoInstance.findItemsWithGivenFolderId(currFolder.getId());

        if (itemList.size() > 0 && cmdArgs.length == 1) {
            String givenItemName = cmdArgs[0];
            Item selectedItem = daoInstance.searchForItemObjectInGivenList(itemList, givenItemName);

            if (selectedItem != null) {
                User currUser = CurrentSessionState.getCurrentSession().getSignedInUser();

                //log.info(currUser.getOrderId());
                if (currUser.getOrderId() == null) {
                    Order currOrder = daoInstance.create(Order.class);
                    currOrder.setOrderStatus("RESERVED");
                    currOrder.setUserId(currUser.getId());
                    currOrder.addItemToOrder(selectedItem);
                    currUser.setOrderId(currOrder.getId());
                    daoInstance.save(currOrder);
                    daoInstance.save(currUser);
                    String msg = "'Order:id'" + currOrder.getId() + " has been created for user " + currUser.getName();
                    msg += "\n Item '" + selectedItem.getName() + "' has been added to Order:id" + currOrder.getId();

                    return CommandFormat.build("OK", "OS01", msg);

                } else if (currUser.getOrderId() != null) {


                    Order currOrder = daoInstance.findABOInstanceById(Order.class, currUser.getOrderId());

                    if (currOrder == null) {
                        currUser.setOrderId(null);
                        daoInstance.save(currUser);

                        String msg = "\n It seems that user with ID:" + currUser.getId() + "has ORDER_ID:" + currUser.getOrderId() +
                                " but no such Order with ID:" + currUser.getOrderId() + "has been found. " +
                                 "Trying to reset ORDERID to NULL" +
                                "Try to reenter command 'put'";
                        return CommandFormat.build("FATAL ERROR", "----", msg);

                    } else {

                        currOrder.addItemToOrder(selectedItem);
                        daoInstance.save(currOrder);

                        String msg = "\n Item '" + selectedItem.getName() + "' has been added to Order:id" + currOrder.getId();
                        return CommandFormat.build("OK", "OS01", msg);
                    }


                }
            } else if (selectedItem == null) {
                String msg = "No such item has been found in current directory. Name:" + givenItemName;
                return CommandFormat.build("ERROR", "OF02", msg);

            }
        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");

    }

    @Override
    public String toString() {
        return null;
    }


}

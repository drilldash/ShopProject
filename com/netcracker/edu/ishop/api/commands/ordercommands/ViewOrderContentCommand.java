package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

public class ViewOrderContentCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ViewOrderContentCommand.class);

    public ViewOrderContentCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "view_order";
    }

    @Override
    public String getDescription() {
        return "Shows order's content for current user. Simply type 'view_order'";
    }

    @Override
    public String execute(String[] cmdArgs) {
        if (cmdArgs.length == 0) {

            User currUser = CurrentSessionState.getCurrentSession().getSignedInUser();

            if (currUser.getOrderId() != null) {
                Order currOrder = daoInstance.findABOInstanceById(Order.class, currUser.getOrderId());
                List<BigInteger> itemListIds = currOrder.getItemListIds();

                String msg = "Following items are in order:\n";

                for (BigInteger id : itemListIds) {
                    Item nextItem = daoInstance.findABOInstanceById(Item.class, id);
                    msg += "I:" + nextItem.getName();

                }

                return CommandFormat.build("OK", "OS05", msg);


            } else if (currUser.getOrderId() == null) {
                String msg = "No items are in order. Tru to use command 'put' to add items for ordering.";
                return CommandFormat.build("OK", "OS03", msg);

            }


        } else {
            String msg = "Too many arguments. " + getDescription();
            return CommandFormat.build("ERROR", "OS04", msg);

        }
        return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");
    }

    @Override
    public String toString() {
        return null;
    }
}

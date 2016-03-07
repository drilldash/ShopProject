package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyItemsInOrderCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(BuyItemsInOrderCommand.class);

    public BuyItemsInOrderCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "buy";
    }

    @Override
    public String getDescription() {
        return "This command for buying items which are already in Order. Usage 'buy'";
    }

    @Override
    public String execute(String[] cmdArgs) {

        if (cmdArgs.length == 0) {

            User currUser = CurrentSessionState.getCurrentSession().getSignedInUser();

            if (currUser.getOrderId() != null) {
                Order currOrder = daoInstance.findABOInstanceById(Order.class, currUser.getOrderId());

                //currOrder.setUserId(null);
                currOrder.setOrderDate( new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                currOrder.setOrderStatus("SOLD OUT!");
                daoInstance.save(currOrder);


                //log.info(currUser);
                currUser.setOrderId(null);
                daoInstance.save(currUser);
                //log.info(currUser);

                String msg = "Congratulations! You've done purchase! Order id:" + currOrder.getId();
                return CommandFormat.build("Ok", "OS06", msg);
               

            }
            else if (currUser.getOrderId() == null) {
                String msg = "Can't sold out anything for user '" + currUser.getName() + "'. Try to add some items by command 'put'";
                return CommandFormat.build("ERROR", "OS06", msg);
                
            }
        }
        else {
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

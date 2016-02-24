package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
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
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length == 0) {

            User currUser = CurrentSessionState.getSignedInUser();

            if (currUser.getOrderId() != null) {
                Order currOrder = daoInstance.findABOInstanceById(Order.class, currUser.getOrderId());

                currOrder.setUserId(null);
                currOrder.setOrderDate( new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                currOrder.setText("SOLD OUT!");
                daoInstance.save(currOrder);

                currUser.setOrderId(null);
                daoInstance.save(currUser);

                String msg = "Congratulations! You've done purchase! Order id:" + currOrder.getId();
                setAllCmdData("Ok", "OS06", msg);
                log.info(getCmdContent());

            }
            else if (currUser.getOrderId() == null) {
                String msg = "Can't sold out anything for user '" + currUser.getName() + "'. Try to add some items by command 'put'";
                setAllCmdData("ERROR", "OS06", msg);
                log.info(getCmdContent());
            }
        }
        else {
            String msg = "Too many arguments. " + getDescription();
            setAllCmdData("ERROR", "OS04", msg);
            log.info(getCmdContent());
        }


    }

    @Override
    public String toString() {
        return null;
    }
}

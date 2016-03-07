package netcracker.edu.ishop.api.commands.ordercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.Order;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowHistoryOfOrdersCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(ShowHistoryOfOrdersCommand.class);

    public ShowHistoryOfOrdersCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "Shows all executed orders for now. Usage:'history'";
    }

    @Override
    public String execute(String[] cmdArgs) {

        if (cmdArgs.length == 0) {

            Map<BigInteger, Order> mapOfOrders = daoInstance.getDataMapByABOName(Order.class);

            List<Order> listSoldOutOrders = new ArrayList<>();

            if (mapOfOrders.size() > 0 ) {
                for (Order d : mapOfOrders.values()) {
                    //todo: hardcoded sh~
                    if (d.getOrderStatus().equals("SOLD OUT!")) {
                        listSoldOutOrders.add(d);
                    }//something here
                }
            }

            if (listSoldOutOrders.size() > 0) {
                String msg = "";
                for (Order order : listSoldOutOrders) {
                    msg += order.toString();
                }
                return CommandFormat.build("OK", "OS16", msg);
                
            } else {
                String msg = "There are no orders which were made by customers.";
                return CommandFormat.build("ERROR", "OS12", msg);
               
            }


        } else {
            String msg = "Too many arguments. " + getDescription();
            return CommandFormat.build("ERROR", "OS04", msg);
            
        }


    }



    @Override
    public String toString() {
        return null;
    }
}

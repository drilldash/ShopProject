package netcracker.edu.ishop.api.objects;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOFactory;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Order extends AbstractBusinessObject {

    private static final Logger log = Logger.getLogger(Order.class);

    private String orderStatus;
    private String orderDate;
    private BigInteger userId;
    private List<BigInteger> itemList = new LinkedList<BigInteger>();

    public Order(BigInteger id) {
        super(id);
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;

    }

    public BigInteger getUserId() {
        return userId;
    }


    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void addItemToOrder(Item item) {
        this.itemList.add(item.getId());
    }

    public List<BigInteger> getItemListIds() {
        return itemList;
    }

    public void setItemList(List<BigInteger> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        String msg = "+------ ORDER ID: "+ getId() + "----------------------------------------------+\n";

        msg += "| " + "ID:" + getId() + "\n";

        if (itemList != null) {

            for (BigInteger itemId : getItemListIds()) {
                DAO daoInstance = DAOFactory.getDAO();

                log.info(itemId);
                log.info(daoInstance.findABOInstanceById(Item.class, itemId));

                msg += "| " + daoInstance.findABOInstanceById(Item.class, itemId).printItemWithProperties() + "\n";
            }
            msg += "+--------------------------------------------------------------------+\n";
            return msg;
        }
        else {
            return "";
        }

    }

}


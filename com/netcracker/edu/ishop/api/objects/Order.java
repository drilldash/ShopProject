package netcracker.edu.ishop.api.objects;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOFactory;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order extends AbstractBusinessObject {
    private String text;
    private String orderDate;
    private BigInteger userId;
    private List<BigInteger> itemList = new LinkedList<BigInteger>();

    public Order(BigInteger id) {
        super(id);
    }


    public String getText() {
        return text;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public String toString() {
        String msg = "+--------------------------------------------------------------------+\n";

        msg += "| " + "ID:" + getId() + "\n";

        if (itemList != null) {

            for (BigInteger itemId : getItemListIds()) {
                DAO daoInstance = DAOFactory.getDAO();
                msg += "| " + daoInstance.findABOInstanceById(Item.class, itemId).printItemWithProperties() + "\n";
            }

            return msg;
        }
        else {
            return "";
        }

    }

}


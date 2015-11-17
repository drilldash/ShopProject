package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;
import java.util.Date;

public class Order extends AbstractBusinessObject {
    private String text;
    private Date orderDate;
    private BigInteger userId;

    public BigInteger getUserId() {
        return userId;
    }

    public Order(BigInteger id) {
        super(id);
    }

    public String getText() {
        return text;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}



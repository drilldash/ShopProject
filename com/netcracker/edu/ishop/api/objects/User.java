package netcracker.edu.ishop.api.objects;

import netcracker.edu.ishop.utils.UserGroupTypes;

import java.math.BigInteger;

public class User extends AbstractBusinessObject {
    private char[] password;
    private boolean isAdmin;
    private UserGroupTypes groupType;
    private BigInteger orderId = null;

    @Override
    public String toString() {

        try {
            String orderId = "";
            if (getOrderId() == null) {
                orderId+="null";
            }
            else{
                orderId+=String.valueOf(getOrderId().intValue());
            }

            return "" + getClass().getSimpleName() + " id:" + getId() + " name:" + getName() + " group:"
                    + getGroup().toString().toCharArray()[0] + "  " + orderId ;
        }
        catch (NullPointerException NPE) {
            return "ID:" + getId() + " " + ". Rest of User fields are null. Check data for this ID.";
        }
    }

    public User(BigInteger id) {
        super(id);
    }

    public char[] getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public UserGroupTypes getGroup() {
        return groupType;
    }

    public void setGroupType(UserGroupTypes groupType) {
        this.groupType = groupType;
    }

    public boolean hasGroup(String role) {
        return (this.groupType.toString()).equals(role);
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }
}

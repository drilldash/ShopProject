package netcracker.edu.ishop.api.objects;

import netcracker.edu.ishop.utils.UserGroupTypes;

import java.math.BigInteger;

public class User extends AbstractBusinessObject {
    private String name;
    private char[] password;
    private boolean isAdmin;
    private UserGroupTypes groupType;

    @Override
    public String toString() {
        return ""+getClass().getSimpleName() + " id:" + getId() + " name:" + getName() + " group:" + getGroup().toString().toCharArray()[0];
    }

    public User(BigInteger id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public char[] getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
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

}

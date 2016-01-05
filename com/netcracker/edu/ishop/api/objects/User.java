package netcracker.edu.ishop.api.objects;

import java.math.BigInteger;

public class User extends AbstractBusinessObject {
    private String name;
    private String password;
    private boolean isAdmin;


    @Override
    public String toString() {
        return ""+getClass().getSimpleName() + " id: " + getId() + " name:" + getName();
    }

    public User(BigInteger id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}

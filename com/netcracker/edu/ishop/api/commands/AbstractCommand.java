package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;

import java.util.EnumSet;

public abstract class AbstractCommand{
    protected DAO daoInstance;
    protected EnumSet<UserGroupTypes> defaultLevelAccess;
    protected String statusMessage;

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
         return statusMessage;
    }

    public AbstractCommand(DAO daoInstance) {
        this.daoInstance = daoInstance;
    }

    public void setDefaultLevelAccess(EnumSet<UserGroupTypes> defaultLevelAccess) {
        this.defaultLevelAccess = defaultLevelAccess;
    }

    public abstract String getName();
    public abstract String getDescription();


    public abstract void execute(String[] cmdArgs);

    public abstract String toString();

    public boolean checkAccess(UserGroupTypes groupType) throws ClassCastException, NullPointerException {
        return defaultLevelAccess.contains(groupType);
    }

    public String getRequiredLevelAccess() {
        return defaultLevelAccess.toString();
    }
}

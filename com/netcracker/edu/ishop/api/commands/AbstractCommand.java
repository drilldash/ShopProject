package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOInMemory;
import netcracker.edu.ishop.utils.UserGroupTypes;

import java.util.EnumSet;

public abstract class AbstractCommand{
    protected DAO daoInstance;
    protected EnumSet<UserGroupTypes> defaultLevelAccess;

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

}

package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOInMemory;

public abstract class AbstractCommand {
    protected DAO daoInstance;

    public AbstractCommand(DAO daoInstance) {
        this.daoInstance = daoInstance;
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute(String[] cmdArgs);
    public abstract String toString();
}

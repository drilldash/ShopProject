package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.persistence.DAO;
import org.apache.log4j.Logger;

import java.util.List;

public class ExitCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(ExitCommand.class);

    public ExitCommand(DAO daoInstance) {
        super(daoInstance);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Use this command to exit from the application";
    }

    @Override
    public void execute(String[] cmdArgs) {
        log.info("Exiting application...");
        daoInstance.DAOExit();
        System.exit(0);
    }



    @Override

    public String toString() {
        return null;
    }
}


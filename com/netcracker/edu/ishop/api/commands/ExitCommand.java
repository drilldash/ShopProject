package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;

public class ExitCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(ExitCommand.class);

    public ExitCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
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

        int numberSignedInUsers = CurrentSessionState.getAllSignedInUsers().size();

        if (numberSignedInUsers > 0) {
            //setStatusMessage(numberSignedInUsers + " users will be signed out automatically." + "Exiting application...");
            //log.info(getStatusMessage());

            String msg = numberSignedInUsers + "\" users will be signed out automatically.\" + \"Exiting application...\"";
            //C stands for CommonStatuses
            setAllCmdData("OK", "C002", msg);
            log.info(getCmdContent());

        }

        daoInstance.DAOExit();
        System.exit(0);
    }



    @Override

    public String toString() {
        return null;
    }
}


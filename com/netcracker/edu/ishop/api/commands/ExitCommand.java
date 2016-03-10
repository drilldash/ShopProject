package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class ExitCommand extends AbstractCommand{

    private static final Logger log = Logger.getLogger(ExitCommand.class);

    public ExitCommand(DAO daoInstance) {
        super(daoInstance);
        //this.defaultLevelAccess = UserGroupTypes.setAdminAccessGroup();
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
    public String execute(String[] cmdArgs) {

        int numberSignedInUsers = CurrentSessionState.getCurrentSession().getAllSignedInUsers().size();

        if (numberSignedInUsers > 0) {

            String msg = numberSignedInUsers + " users will be signed out automatically." + " Exiting application...";

            daoInstance.DAOExit();

            return CommandFormat.build("OK", "----", msg);
           

        }

        daoInstance.DAOExit();
        return CommandFormat.build("OK", "----", "Exit has been done!");
    }



    @Override

    public String toString() {
        return null;
    }
}


package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.EnumSet;
import java.util.List;

public class HelpCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(HelpCommand.class);

    private List<AbstractCommand> cmdList;

    public HelpCommand(DAO daoInstance, List<AbstractCommand> cmdList) {
        super(daoInstance);
        this.cmdList = cmdList;
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

//    public HelpCommand(DAO daoInstance, String defaultLevelAccess, List<AbstractCommand> cmdList) {
//        super(daoInstance, defaultLevelAccess);
//        this.defaultLevelAccess = UserGroupTypes.;
//        this.cmdList = cmdList;
//    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Use this command to list available commands";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] cmdArgs) {
        String helpText = "Available commands:";

        for (AbstractCommand cmd : cmdList ) {
            helpText += "\n" + (cmd.getName() + " ---> " + cmd.getDescription());
        }
        setStatusMessage(helpText);
        log.info(getStatusMessage());
    }
}

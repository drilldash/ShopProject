package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.List;

public class HelpCommand extends AbstractCommand {

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
        return "Use this command to list available commands. Usage: \"help\" or \"help [given command name]\" (e.g. \'help ls\')";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String execute(String[] cmdArgs) {


        if (cmdArgs.length == 0) {
            String helpText = "Available commands:";

            for (AbstractCommand cmd : cmdList) {
                helpText += "\n" + (cmd.getName() + " ---> " + cmd.getDescription());
            }
            //setStatusMessage(helpText);
            //log.info(getStatusMessage());

            String msg = helpText;
            //C stands for CommonErrors
            return CommandFormat.build("OK", "C002", msg);
            
        } else if (cmdArgs.length == 1) {
            String helpText = "";
            String givenCmdName = cmdArgs[0];

            for (AbstractCommand cmd : cmdList) {
                if (cmd.getName().equals(givenCmdName)) {
                    helpText += "" + (cmd.getName() + " ---> " + cmd.getDescription());
                }
            }
            String msg;
            if (helpText.equals("")) {
                msg = "There is no help documentation for \"" + givenCmdName + "\"";
            } else {
                msg = helpText;
            }

            return CommandFormat.build("OK", "C002", msg);
            

        } else if (cmdArgs.length > 1) {
            String msg = "Too many arguments. " + getDescription();
            return CommandFormat.build("ERROR", "I10", msg);
            
        }
    return CommandFormat.build("FATAL ERROR", "----", "Work of command:" + getName() + " is incorrect");
    }
}

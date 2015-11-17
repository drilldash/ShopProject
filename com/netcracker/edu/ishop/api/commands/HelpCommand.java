package netcracker.edu.ishop.api.commands;

import org.apache.log4j.Logger;

import java.util.List;

public class HelpCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(HelpCommand.class);

    private List<AbstractCommand> cmdList;

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Use this command to list available commands";
    }

    public HelpCommand(List<AbstractCommand> cmdList) {
        this.cmdList = cmdList;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] cmdArgs) {
        String helpText = "\n Available commands:";

        for (AbstractCommand cmd : cmdList ) {
            helpText += "\n" + (cmd.getName() + " ---> " + cmd.getDescription());
        }
        log.info(helpText);
    }
}

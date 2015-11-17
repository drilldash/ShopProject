package netcracker.edu.ishop.api.commands;


import netcracker.edu.ishop.api.persistence.DAOInMemory;

import java.util.*;

public class CommandEngine {
    private Map<String, AbstractCommand> commandsMap;
    private static List<AbstractCommand> commandsList;


    private DAOInMemory daoInMemory = new DAOInMemory();

    public CommandEngine() {
        // we're registering commands here
        commandsList = new ArrayList<AbstractCommand>();
        commandsList.add(new ExitCommand());
        commandsList.add(new RegisterUserCommand(daoInMemory));
        commandsList.add(new HelpCommand(commandsList));

        commandsMap = new HashMap<String, AbstractCommand>();
        for (AbstractCommand cmd: commandsList) {
            commandsMap.put(cmd.getName().toLowerCase(), cmd);
        }

    }

    public static List<AbstractCommand> getListOfCommands() {
        return commandsList;
    }


    public void executeCommand(String cmdRawData) {
        String[] cmdParams;
        if (cmdRawData == null) {
            System.exit(0);
        }
        String[] cmdArgs = cmdRawData.split(" ");

        String cmdName = cmdArgs[0].toLowerCase();
        if (cmdArgs.length >= 1) {
            cmdParams = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
        } else {
            cmdParams = new String[1];
            Arrays.fill(cmdParams, "");
        }

        if (cmdName == null || commandsMap.get(cmdName) == null) {
            System.out.println("Unknown command! Type \"help\"!");
        }
        else {
            commandsMap.get(cmdName).execute(cmdParams);
        }

    }



}

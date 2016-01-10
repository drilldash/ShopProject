package netcracker.edu.ishop.api.commands.engine;


import netcracker.edu.ishop.api.commands.*;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOInMemory;
import org.apache.log4j.Logger;

import java.nio.file.AccessDeniedException;
import java.util.*;

public class CommandEngine {
    public static final Logger log = Logger.getLogger(CommandEngine.class);

    private Map<String, AbstractCommand> commandsMap;
    private static List<AbstractCommand> commandsList;

    private DAO daoInstance = new DAOInMemory();

    public CommandEngine() {

        // we're registering commands here
        commandsList = new ArrayList<AbstractCommand>();
        commandsList.add(new ExitCommand(daoInstance));
        commandsList.add(new RegisterUserCommand(daoInstance));
        commandsList.add(new RegisterAdminCommand(daoInstance));
        commandsList.add(new ShowUsersCommand(daoInstance));
        commandsList.add(new SignInCommand(daoInstance));
        commandsList.add(new SignOutCommand(daoInstance));
        commandsList.add(new WhoAmICommand(daoInstance));
        commandsList.add(new DeleteUserCommand(daoInstance));
        commandsList.add(new RenameUserCommand(daoInstance));
        commandsList.add(new WhichGroupCommand(daoInstance));

        commandsList.add(new HelpCommand(daoInstance, commandsList));



        commandsMap = new HashMap<String, AbstractCommand>();
        for (AbstractCommand cmd: commandsList) {
            commandsMap.put(cmd.getName().toLowerCase(), cmd);
        }

    }

    public static List<AbstractCommand> getListOfCommands() {
        return commandsList;
    }


    public void executeCommand(String cmdRawData) throws AccessDeniedException{
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
            AbstractCommand command = commandsMap.get(cmdName);
            //log.info(CurrentSessionState.getUserGroupTypeLocal());
            if (command.checkAccess(CurrentSessionState.getUserGroupTypeLocal())) {
                try {
                    command.execute(cmdParams);
                } catch (IllegalArgumentException IAE) {
                    log.info(IAE.toString());
                }
            }
            else {
                //log.info(cmdRawData + "\nAccess denied!" + " Use command \"which_group\" to see yout current level of access.");
                throw new AccessDeniedException(cmdRawData + "\nAccess denied!" + " Use command \"which_group\" to see yout current level of access.");
            }
        }

    }



}

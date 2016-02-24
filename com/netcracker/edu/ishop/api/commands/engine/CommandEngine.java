package netcracker.edu.ishop.api.commands.engine;


import netcracker.edu.ishop.api.commands.*;
import netcracker.edu.ishop.api.commands.foldercommands.*;
import netcracker.edu.ishop.api.commands.itemcommands.AddItemCommand;
import netcracker.edu.ishop.api.commands.itemcommands.AddItemPropertyCommand;
import netcracker.edu.ishop.api.commands.itemcommands.ShowItemsCommand;
import netcracker.edu.ishop.api.commands.itemcommands.ShowPropertiesOfGivenItem;
import netcracker.edu.ishop.api.commands.mixedcommands.ListSegmentsCommand;
import netcracker.edu.ishop.api.commands.ordercommands.BuyItemsInOrderCommand;
import netcracker.edu.ishop.api.commands.ordercommands.PutItemToOrderCommand;
import netcracker.edu.ishop.api.commands.ordercommands.ShowHistoryOfOrdersCommand;
import netcracker.edu.ishop.api.commands.ordercommands.ViewOrderContentCommand;
import netcracker.edu.ishop.api.commands.usercommands.*;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.api.persistence.DAOFactory;

import org.apache.log4j.Logger;


import java.nio.file.AccessDeniedException;
import java.util.*;

public class CommandEngine {
    public static final Logger log = Logger.getLogger(CommandEngine.class);

    private static CommandEngine INSTANCE = new CommandEngine();
    private Map<String, AbstractCommand> commandsMap;
    private static List<AbstractCommand> commandsList;

    //private DAO daoInstance = new JsonDaoNew<>();


    private String commandStatus;

    private CommandEngine() {

        DAO daoInstance = DAOFactory.getDAO();

        // we're registering commands here

        //user commands
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

        // folder commands
        commandsList.add(new CreateFolderCommand(daoInstance));
        commandsList.add(new ShowFolderPathCommand(daoInstance));
        commandsList.add(new ShowFolderRecordsCommand(daoInstance));
        commandsList.add(new ShowCurrentFolderCommand(daoInstance));
        commandsList.add(new ShowFoldersInCurrentCommand(daoInstance));
        commandsList.add(new ChangeFolderCommand(daoInstance));

        // item commands
        commandsList.add(new AddItemCommand(daoInstance));
        commandsList.add(new ShowItemsCommand(daoInstance));
        commandsList.add(new AddItemPropertyCommand(daoInstance));
        commandsList.add(new ShowPropertiesOfGivenItem(daoInstance));

        // mixed commands
        commandsList.add(new ListSegmentsCommand(daoInstance));

        //order commands
        commandsList.add(new BuyItemsInOrderCommand(daoInstance));
        commandsList.add(new PutItemToOrderCommand(daoInstance));
        commandsList.add(new ShowHistoryOfOrdersCommand(daoInstance));
        commandsList.add(new ViewOrderContentCommand(daoInstance));

        //

        commandsMap = new HashMap<String, AbstractCommand>();


        for (AbstractCommand cmd : commandsList) {
            commandsMap.put(cmd.getName().toLowerCase(), cmd);
        }

    }

    public static CommandEngine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandEngine();
        }
        return INSTANCE;
    }


    public static List<AbstractCommand> getListOfCommands() {
        return commandsList;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void executeCommand(String cmdRawData) throws AccessDeniedException {
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
            commandStatus = "Unknown command! Type \"help\"!";
        } else {
            AbstractCommand command = commandsMap.get(cmdName);
            //log.info(CurrentSessionState.getUserGroupTypeLocal());
            if (command.checkAccess(CurrentSessionState.getUserGroupTypeLocal())) {
                try {
                    command.execute(cmdParams);
                    //commandStatus = command.getStatusMessage();
                    commandStatus = command.getCmdJsonCommandData().toString();
                    //log.info(commandStatus);
                } catch (IllegalArgumentException IAE) {
                    log.info(IAE.toString());
                    commandStatus = IAE.toString();
                }

            } else {
                //log.info(cmdRawData + "\nAccess denied!" + " Use command \"which_group\" to see yout current level of access.");
                throw new AccessDeniedException(cmdRawData + "\nAccess denied! Using this command requires GROUP:" + command.getRequiredLevelAccess() + " \nUse command " +
                        "\"which_group\" to see your current level of access.");
            }
        }

    }


}

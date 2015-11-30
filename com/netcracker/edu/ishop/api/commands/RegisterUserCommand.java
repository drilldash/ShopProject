package netcracker.edu.ishop.api.commands;

import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import org.apache.log4j.Logger;

public class RegisterUserCommand extends AbstractCommand{

    public static final Logger log = Logger.getLogger(RegisterUserCommand.class);

    public RegisterUserCommand(DAO daoInstance) {
        super(daoInstance);
    }

    @Override
    public String toString() {
        return ""+getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        return "Usage of the command: register_user [username] [password]";
    }

    @Override
    public String getName() {
        return "register_user";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
            throw new IllegalArgumentException("Wrong number of arguments in " + "\"" +  getName() + "\"");
        }

        String username = cmdArgs[0];
        String password = cmdArgs[1];


        log.info(username + password);
        daoInstance.create(User.class);

    }
}

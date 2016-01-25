package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class RegisterUserCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(RegisterUserCommand.class);

    public RegisterUserCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAccessForSignInGroups();
    }

    @Override
    public String toString() {
        return "" + getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        return "Usage: register_user [username] [password]";
    }

    @Override
    public String getName() {
        return "register_user";
    }

    @Override
    public void execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
            setStatusMessage("Wrong number of arguments in " + "\"" + getName() + "\"");
            log.info(getStatusMessage());
        } else {

            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();


            //class cast!!!
            //User user = (User) daoInstance.create(User.class);

            //DAO<User> daoInstance = new DAOInMemoryJSON<User>();
            User user = daoInstance.create(User.class);

            user.setName(username);
            user.setPassword(password);
            user.setGroupType(UserGroupTypes.USER);

            if (daoInstance.findUserByName(username) == null) {
                daoInstance.save(user);
                setStatusMessage(username + " has been saved in data-structure!");
                log.info(getStatusMessage());
            } else {
                setStatusMessage("Username " + username + " is taken, can't save it to data-structure");
                log.info(getStatusMessage());
                UniqueIDGenerator.getInstance().decrementID();
            }
        }
    }
}

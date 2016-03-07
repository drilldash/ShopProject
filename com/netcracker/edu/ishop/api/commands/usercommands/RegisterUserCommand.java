package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
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
    public String execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
            return CommandFormat.build("ERROR", "----", "Wrong number of arguments in " + "\"" + getName() + "\"");
            
        } else {

            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();


            //class cast!!!
            //User user = (User) daoInstance.create(User.class);

            //DAO<User> daoInstance = new DAOInMemoryJSONConcurrent<User>();



            //daoInstance.save(user);


            //log.info(daoInstance.findUserByName(username).toString());

            if (daoInstance.findUserByName(username) == null) {
                User user = daoInstance.create(User.class);
                user.setName(username);
                user.setPassword(password);
                user.setGroupType(UserGroupTypes.USER);
                daoInstance.save(user);

                //setStatusMessage(username + " has been saved in data-structure!");
                //log.info(getStatusMessage());

                String msg = username + " has been saved in data-structure!";
                return CommandFormat.build("OK", "U005", msg);


            } else {
                //setStatusMessage("Username " + username + " is taken, can't save it to data-structure");
                //log.info(getStatusMessage());


                String msg = "Username " + username + " is taken, can't save it to data-structure";
                //UniqueIDGenerator.getInstance().decrementID();
                return CommandFormat.build("ERROR", "U006", msg);


            }
        }

    }
}

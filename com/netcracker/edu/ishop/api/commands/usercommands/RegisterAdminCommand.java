package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

public class RegisterAdminCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(RegisterAdminCommand.class);

    public RegisterAdminCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAdminAccessGroup();
    }

    @Override
    public String toString() {
        return "" + getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        return "Usage: register_admin [username] [password]";
    }

    @Override
    public String getName() {
        return "register_admin";
    }

    @Override
    public String execute(String[] cmdArgs) {

        if (cmdArgs.length > 2 || cmdArgs.length < 2) {
                return CommandFormat.build("ERROR", "----", "Wrong number of arguments in " + "\"" + getName() + "\"");

        } else {

            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();


            // class cast!!!
            //User user = (User) daoInstance.create(User.class);

            //DAO<User> daoInstance = new DAOInMemoryJSONConcurrent<User>();
            User user = daoInstance.create(User.class);

            user.setName(username);
            user.setPassword(password);
            user.setGroupType(UserGroupTypes.ADMIN);
            user.setIsAdmin(true);

            if (daoInstance.findUserByName(username) == null) {
                daoInstance.save(user);

                //setStatusMessage("Admin " + username + " has been saved in data-structure!");
                //log.info(getStatusMessage());

                String msg = "Admin " + username + " has been saved in data-structure!";
                return CommandFormat.build("OK", "U003", msg);
                

            } else {

                //setStatusMessage("Username " + username + " is taken, can't save it to data-structure");
                //log.info(getStatusMessage());

                String msg = "Username " + username + " is taken, can't save it to data-structure";
                UniqueIDGenerator.getInstance().decrementID();
                return CommandFormat.build("ERROR", "U004", msg);
                
                
            }
        }
    }
}

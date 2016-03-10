package netcracker.edu.ishop.api.commands.usercommands;

import netcracker.edu.ishop.api.commands.AbstractCommand;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.commands.CommandFormat;
import netcracker.edu.ishop.utils.UserGroupTypes;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class SignInCommand extends AbstractCommand {

    public static final Logger log = Logger.getLogger(SignInCommand.class);

    public SignInCommand(DAO daoInstance) {
        super(daoInstance);
        this.defaultLevelAccess = UserGroupTypes.setAllAccessGroups();
    }

    @Override
    public String getName() {
        return "sign_in";
    }

    @Override
    public String getDescription() {
        return "Usage: sign_in [username] [password]";
    }

    @Override
    public String execute(String[] cmdArgs) {

        if ((cmdArgs.length > 2 || cmdArgs.length < 2) && (CurrentSessionState.getCurrentSession().getSignedInUser() == null)) {
            //log.info("Wrong number of arguments in " + "\"" + getName() + "\"");
            throw new IllegalArgumentException("Wrong number of arguments in " + "\"" + getName() + "\"");
        } else if (CurrentSessionState.getCurrentSession().getSignedInUser() != null) {
            //setStatusMessage("User " + CurrentSessionState.getSignedInUser().getName() + " should sign out first");
            //log.info(getStatusMessage());

            String msg = "User " + CurrentSessionState.getCurrentSession().getSignedInUser().getName() + " should sign out first";
            return CommandFormat.build("ERROR", "U012", msg);
            


        } else {
            String username = cmdArgs[0];
            char[] password = cmdArgs[1].toCharArray();

            //System.out.println(username + " " + Arrays.toString(password) );

            User user = daoInstance.findUserByName(username);


            //System.out.println(user.getName() + " " + Arrays.toString(user.getPassword()) );

            if (user == null) {
                //setStatusMessage("No such username found in data-structure, you should register first");
                //log.info(getStatusMessage());

                String msg = "No such username found in data-structure, you should register first";
                return CommandFormat.build("ERROR", "U013", msg);
                


            } else if (user != null && !Arrays.equals(user.getPassword(), password)) {
                //setStatusMessage("Passwords are not matching each other");
                //log.info(getStatusMessage());

                String msg = "Passwords are not matching each other";
                return CommandFormat.build("ERROR", "U014", msg);
                


            } else if (!CurrentSessionState.getCurrentSession().getAllSignedInUsers().contains(user.getId())) {
                CurrentSessionState.getCurrentSession().setSignedInUser(user);

                //setStatusMessage("User \"" + username + "\" has been successfully signed in!");
                //log.info(getStatusMessage());

                String msg = "User \"" + username + "\" has been successfully signed in!";
                return CommandFormat.build("OK", "U015", msg);
                
            } else {
                String msg = "User \"" + username + "\" has already been taken to sign in!";
                return CommandFormat.build("OK", "U015", msg);
                
            }

        }

    }


    @Override
    public String toString() {
        return null;
    }
}

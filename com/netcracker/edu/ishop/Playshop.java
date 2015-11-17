package netcracker.edu.ishop;
import netcracker.edu.ishop.api.commands.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Playshop {

    public static final Logger log = Logger.getLogger(Playshop.class);

    public static void main(String[] args) {

        CommandEngine commandEngine = new CommandEngine();

        while (true) {
            System.out.println(">>>");
            String cmdData = getInputStringCommand();
            commandEngine.executeCommand(cmdData);
        }

    }

    private static String getInputStringCommand() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException ioe) {
            log.info("Exception during user input");
            ioe.printStackTrace();
            return null;
        }
    }


}

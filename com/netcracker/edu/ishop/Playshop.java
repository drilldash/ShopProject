package netcracker.edu.ishop;
import netcracker.edu.ishop.api.commands.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Playshop {


    public static void main(String[] args) {

        CommandEngine commandEngine = new CommandEngine();

        while (true) {
            System.out.print(">>>\n");
            String cmdData = getInputStringCommand();
            commandEngine.executeCommand(cmdData);
        }

    }

    private static String getInputStringCommand() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException ioe) {
            System.out.println("Exception during user input");
            ioe.printStackTrace();
            return null;
        }
    }


}

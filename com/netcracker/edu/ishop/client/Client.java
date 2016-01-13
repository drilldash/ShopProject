package netcracker.edu.ishop.client;

/* This class is for reading from console.*/

import netcracker.edu.ishop.utils.PortSettings;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final Logger log = Logger.getLogger(Client.class);
    public static void execute() throws IOException,ClassNotFoundException {
        System.out.println("Connecting to server via socket");


        Socket serverSocket = new Socket("localhost", PortSettings.PORT_NUMBER);
        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);


        while (true) {
            String userInput = getInputStringCommand();
            out.println(userInput);
            String serverInput = in.readLine();

            log.info(serverInput+"\n");

            if (userInput != null && userInput.equalsIgnoreCase("close")) break;
        }

        out.close();
        in.close();
        serverSocket.close();
    }

    private static String getInputStringCommand() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException ioe) {
            log.info("Exception during user input");
            ioe.printStackTrace();
            return null;
        } catch (NullPointerException NPE) {
            log.info("Exception during user input " + NPE);
        }
        return null;
    }
}

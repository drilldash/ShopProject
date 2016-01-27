package netcracker.edu.ishop.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import netcracker.edu.ishop.utils.PortSettingsNew;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final Logger log = Logger.getLogger("com.netcracker.edu.ishop.client");
    public static void execute() throws IOException,ClassNotFoundException {
        System.out.println("Connecting to server via socket");

        Socket serverSocket = new Socket("localhost", PortSettingsNew.PORT_NUMBER);
        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);

        while (true) {
            System.out.println(">>>");
            String userInput = getInputStringCommand();
            out.println(userInput);
            String serverInput = in.readLine();

            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(serverInput).getAsJsonObject();

            //log.info(serverInput);
            //hardcoded-style, pal

            if (json.has("STATUS") && json.has("STATUS_CODE") && json.has("Content")) {
                String status = "Status: " + json.get("STATUS").getAsString();
                String statusCode = "Code: " + json.get("STATUS_CODE").getAsString() ;
                String content = "Content: " + json.get("Content").getAsString();
                log.info( status + "\n" + statusCode + "\n" + content );
            }
            else {
                log.info(serverInput);
            }

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
package netcracker.edu.ishop.server;

import netcracker.edu.ishop.utils.PortSettingsNew;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayServer {
    public static final Logger log = Logger.getLogger("com.netcracker.edu.ishop.server");
    public static void execute() throws IOException {

        ServerSocket ssock = new ServerSocket(PortSettingsNew.PORT_NUMBER);
        System.out.println("Listening");
        while (true) {
            log.info("Waiting for client");
            Socket sock = ssock.accept();
            log.info("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }

    public static void main(String[] args) {
        try {
            PlayServer.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package netcracker.edu.ishop.server;

import netcracker.edu.ishop.api.commands.engine.CommandEngine;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.utils.PortSettings;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.AccessDeniedException;
import java.security.AccessControlException;

public class MultiThreadServer implements Runnable {
    public static final Logger log = Logger.getLogger(MultiThreadServer.class);
    Socket csocket;

    MultiThreadServer(Socket csocket) {
        this.csocket = csocket;
    }

    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(PortSettings.PORT_NUMBER);
        System.out.println("Listening");
        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(csocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()))) {

            String input;
            while ((input = in.readLine()) != null) {
                try {
                    CommandEngine cmdEngine = CommandEngine.getInstance();
                    String cmdData = input.toLowerCase();
                    if (cmdData == "close") {
                        cmdEngine.executeCommand("exit");
                        out.println("");
                    } else {
                        cmdEngine.executeCommand(cmdData);
                        out.println("Smth to send");
                    }

                } catch (IllegalArgumentException e) {
                    log.info(e.toString());
                    out.println(1);
                } catch (AccessControlException ACE) {
                    log.info(ACE.toString());
                    out.println(1);
                } catch (AccessDeniedException ACE) {
                    log.info(ACE.toString());
                    out.println(1);
                }

            }
            csocket.close();
        } catch (SocketException e) {
            log.info(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CurrentSessionState.removeUserFromSignedInUsers();
        }
    }
}

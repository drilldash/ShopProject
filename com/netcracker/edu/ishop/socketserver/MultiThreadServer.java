package netcracker.edu.ishop.socketserver;

import netcracker.edu.ishop.api.commands.engine.CommandEngine;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class MultiThreadServer implements Runnable {
    public static final Logger log = Logger.getLogger("com.netcracker.edu.ishop.socketserver");
    Socket csocket;

    MultiThreadServer(Socket csocket) {
        this.csocket = csocket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(csocket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()))) {

            String input;
            while ((input = in.readLine()) != null) {

                    CommandEngine cmdEngine = CommandEngine.getInstance();
                    String cmdData = input;
                    log.info(cmdData);
                    if (cmdData.equals("close")) {

                        out.println(cmdEngine.executeCommand("exit"));
                        //out.println();
                    } else {

                        out.println(cmdEngine.executeCommand(cmdData));
                    }


            }
            csocket.close();
        } catch (SocketException e) {
            log.info(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CurrentSessionState.getCurrentSession().removeUserFromSignedInUsers();
        }
    }
}


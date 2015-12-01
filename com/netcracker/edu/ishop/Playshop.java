package netcracker.edu.ishop;

import netcracker.edu.ishop.api.commands.*;
import netcracker.edu.ishop.utils.ProjectConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Playshop {

    public static final Logger log = Logger.getLogger(Playshop.class);

    public static void main(String[] args) {

        //inspired by these: (ways of concatenating file paths and reading files)
        //http://stackoverflow.com/a/4177678/2938167
        //http://stackoverflow.com/a/3433102/2938167

        CommandEngine commandEngine = new CommandEngine();

        if (ProjectConstants.PERMIT_SCENARIO_EXECUTION) {

            File scenarioFile = new File(ProjectConstants.SCENARIO_FILE_PATH);
            log.info("Path of the scenario file is: " + ProjectConstants.SCENARIO_FILE_PATH);

            LineIterator lineIterator = null;

            try {
                lineIterator = FileUtils.lineIterator(scenarioFile, "UTF-8");
                while (lineIterator.hasNext()) {
                    String cmdData = lineIterator.next();
                    if (!cmdData.startsWith("#")) {
                        commandEngine.executeCommand(cmdData);
                    }

                }

            } catch (IOException e) {
                log.info("No scenario file was found! Is scenario\'s filepath " + scenarioFile + "  correct?\n" + e);
            } finally {
                LineIterator.closeQuietly(lineIterator);
                log.info("Scenario file was executed");
            }


            while (true) {
                System.out.println(">>>");
                String cmdData = getInputStringCommand();
                commandEngine.executeCommand(cmdData);
            }

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

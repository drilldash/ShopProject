package netcracker.edu.ishop;

import netcracker.edu.ishop.api.commands.*;
import netcracker.edu.ishop.utils.ScenarioConstants;
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

        CommandEngine commandEngine = new CommandEngine();

        executeScenarioScript(commandEngine);

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

    private static void executeScenarioScript(CommandEngine commandEngine) {

        //inspired by these: (ways of concatenating file paths and reading files)
        //http://stackoverflow.com/a/4177678/2938167
        //http://stackoverflow.com/a/3433102/2938167

        if (ScenarioConstants.PERMIT_SCENARIO_EXECUTION) {

            File scenarioFile = new File(ScenarioConstants.SCENARIO_FILE_PATH);
            log.info("Path of the scenario file is: " + ScenarioConstants.SCENARIO_FILE_PATH);

            LineIterator lineIterator = null;

            try {
                lineIterator = FileUtils.lineIterator(scenarioFile, "UTF-8");
                long lineNumber = 0;
                while (lineIterator.hasNext()) {
                    lineNumber++;
                    String cmdData = lineIterator.next();
                    if (!cmdData.startsWith("#")) {
                        commandEngine.executeCommand(cmdData);
                    } else {
                        log.info("Line " + lineNumber + ":\"" + cmdData + "\" was omitted for execution, because it\'s a comment");
                    }

                }
                log.info("Executing of scenario\'s file was finished");

            } catch (IOException e) {
                log.info("No scenario file was found! Is scenario\'s filepath\n" + scenarioFile + " correct?\n" + e);
            } finally {
                LineIterator.closeQuietly(lineIterator);
            }

        }
        else {
            log.info("Execution of scenarios is not allowed, because in 'ScenarioConstants.java' constant 'PERMIT_SCENARIO_EXECUTION'= false");
        }
    }
}



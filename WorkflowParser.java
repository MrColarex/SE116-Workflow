import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class WorkflowParser {

    public static void parseWorkflow(File workflowFile, File jobFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(jobFile))) {

            String line;
            int indexOfType = 0;  //indexOfType tells which type are we working on (jobtypes stations or tasktypes.)
            List<String> taskTypeElements = new ArrayList<>(); // List to store elements of TASKTYPES
            List<String> jobTypeElements = new ArrayList<>(); // List to store elements of JOBTYPES
            List<String> stationElements = new ArrayList<>(); // List to store elements of STATIONS
            while ((line = br.readLine()) != null) {
                line = line.trim(); //Removing spaces at the beginning and at the end
                if (line.isEmpty()) {
                    continue; // Skip to the next iteration if line is empty
                } 
                // Check if the line represents task types
                if (line.startsWith("(JOBTYPES")) 
                    indexOfType = 1;
                if (line.startsWith("(STATIONS")) 
                     indexOfType = 2;

                if (indexOfType == 0) {
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        taskTypeElements.add(element); // Add elements to the TASKTYPES list
                    }
                }
                if (indexOfType == 1) {   
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        jobTypeElements.add(element); // Add elements to the JOBTYPES list
                    }
                }
                if (indexOfType == 2) {   
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        stationElements.add(element); // Add elements to the STATIONS list
                    }
                }
            }

            // Remove the first element "TASKTYPES"
            taskTypeElements.remove(0);

            // Remove parentheses from the first and last strings
            if (!taskTypeElements.isEmpty()) {
                String firstElement = taskTypeElements.get(0);
                if (firstElement.startsWith("(")) {
                    taskTypeElements.set(0, firstElement.substring(1)); // Remove the opening parenthesis
                }
                String lastElement = taskTypeElements.get(taskTypeElements.size() - 1);
                if (lastElement.endsWith(")")) {
                    taskTypeElements.set(taskTypeElements.size() - 1, lastElement.substring(0, lastElement.length() - 1)); // Remove the closing parenthesis
                }
            }
            // Print accumulated elements
            System.out.println("TASKTYPES Elements:");
            for (String element : taskTypeElements) {
                System.out.println(element);
            }
            System.out.println("Now we will create and print TaskType objects.");

            //Code to create TaskType objects while checking for errors.
            for (String element : taskTypeElements) {

            }

        } 
        catch (Exception e) {
            System.out.println("create message here");
        }
    }
}

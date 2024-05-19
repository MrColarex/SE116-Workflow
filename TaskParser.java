import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.util.Set;

public class TaskParser {

    public static void parseAndAssignTasks(List<Station> stations, File workflowFile) {
        List<Task> tasks = parseTasks(workflowFile);

        // Iterate over tasks and assign them to stations
        for (Task task : tasks) {
            boolean taskAssigned = false;
            for (Station station : stations) {
                if (station.canProcessTask(task)) {
                    if (station.getexecutingJobs().size() < station.getMaxCapacity()) {
                        station.addJob(new Job(task.getTaskID(), null, 0, 0)); // Create a temporary job
                        taskAssigned = true;
                        break;
                    }
                }
            }
            if (!taskAssigned) {
                System.out.println("No station available to process task: " + task.getTaskID());
            }
        }
    }

    public static List<Task> parseTasks(File workflowFile) {
        String line;
        String currentTaskID = null;
        int indexOfType = 0; //indexOfType tells which type are we working on (jobtypes stations or tasktypes.)
        List<String> taskTypeElements = new ArrayList<>(); // List to store elements of TASKTYPES
        List<Task> tasks = new ArrayList<>();
        Set<String> taskIDs = new HashSet<>();


        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile))) {
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Removing spaces at the beginning and at the end
                if (line.isEmpty()) {
                    continue; // Skip to the next iteration if line is empty
                }
                // Check if the line represents task types
                if (line.startsWith("(JOBTYPES"))
                    indexOfType = 1; // This stops the parsing of tasks once jobtypes starts


                if (indexOfType == 0) {
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        taskTypeElements.add(element); // Add elements to the TASKTYPES list
                    }
                }

            }
            // Remove the first element "TASKTYPES"
            taskTypeElements.remove(0);

            // Remove parentheses from the first and last strings for taskTypeElements String array.
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

            // Code to create Task objects while checking for errors
            for (String element : taskTypeElements) {
                try {
                    // Try to parse the element as a double
                    double size = Double.parseDouble(element);

                    // Check for negative TaskSize
                    if (size < 0) {
                        System.out.println("Semantic error: Negative TaskSize for taskID " + currentTaskID);
                        continue; // Skip to the next iteration
                    }

                    // Check for duplicate task IDs
                    if (currentTaskID != null && taskIDs.contains(currentTaskID)) {
                        System.out.println("Semantic error: Duplicate taskID " + currentTaskID);
                        currentTaskID = null; // Reset current task ID to skip this entry
                        continue;
                    }

                    // Add the task with the current ID and size
                    if (currentTaskID != null) {
                        tasks.add(new Task(currentTaskID, size));
                        taskIDs.add(currentTaskID); // Add to set of task IDs
                    }

                    // Reset current task ID
                    currentTaskID = null;
                } catch (NumberFormatException e) {
                    // If parsing as a double fails, assume it's a task ID
                    if (currentTaskID != null && !taskIDs.contains(currentTaskID)) {
                        // Add the task with size 0 if the current ID is not a duplicate
                        tasks.add(new Task(currentTaskID, 0));
                        taskIDs.add(currentTaskID); // Add to set of task IDs
                    }
                    currentTaskID = element;

                    // Validate the tasktypeID manually
                    if (!isValidTaskID(currentTaskID)) {
                        System.out.println(currentTaskID + " is an invalid tasktypeID");
                        return tasks; // Return early since we want to stop processing
                    }
                }
            }
            // Add the last task if it exists and is not a duplicate
            if (currentTaskID != null && !taskIDs.contains(currentTaskID)) {
                // Assuming size 0 for the last task if size is not explicitly provided
                tasks.add(new Task(currentTaskID, 0));
                taskIDs.add(currentTaskID); // Add to set of task IDs
            }

        } catch (IOException e) {
            System.err.println("Error reading the workflow file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Incorrect input. Please provide valid input.");
            tasks.clear(); // Clear the tasks list
        }

        return tasks;
    }
    private static boolean isValidTaskID(String taskID) {
        if (!Character.isLetter(taskID.charAt(0))) {
            return false; // First character must be a letter
        }
        for (char c : taskID.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
                return false; // Return false if any character is not alphanumeric
            }
        }
        return true; // Return true if all characters are alphanumeric
    }
}

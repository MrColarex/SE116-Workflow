import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class StationParser {
    public static List<Station> parseStations(File workflowFile, List<Task> tasks) {
        List<Station> stations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile))) {
            String line;
            boolean isStationSection = false;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Removing spaces at the beginning and at the end
                if (line.isEmpty()) {
                    continue; // Skip to the next iteration if line is empty
                }
                if (line.startsWith("(STATIONS")) {
                    isStationSection = true;
                    continue; // Skip the current line as it just marks the start of STATIONS
                }
                if (isStationSection && line.startsWith(")")) {
                    isStationSection = false;
                    continue; // Skip the current line as it just marks the end of STATIONS
                }
                if (isStationSection) {
                    stations.add(parseStation(line, tasks)); // Pass the 'tasks' list to the 'parseStation' method
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    private static Station parseStation(String line, List<Task> allTasks) {
        line = removeTrailingSpacesAndClosingParentheses(line);
        String[] elements = line.split(" ");
        String stationID = elements[0].substring(1);
        int maxCapacity = Integer.parseInt(elements[1]);
        boolean multiflag = elements[2].equals("Y");
        boolean fifoflag = elements[3].equals("Y");
    
        List<Task> tasksCanBeDone = new ArrayList<>();
        List<Double> speedForTask = new ArrayList<>();
        List<Double> speedVariabilityMultiplier = new ArrayList<>();
    
        for (int i = 4; i < elements.length; i++) {
            // Check if the element is a task ID by trying to find it in the allTasks list
            Task task = findTaskByID(elements[i], allTasks);
            if (task != null) {
                // If it's a task ID, add the task to the tasksCanBeDone list
                tasksCanBeDone.add(task);
                // The next element should be the speed for this task
                i++;
                speedForTask.add(Double.parseDouble(elements[i]));
                // Check if there's a speed multiplier following the speed
                if (i + 1 < elements.length && isDouble(elements[i + 1])) {
                    i++;
                    speedVariabilityMultiplier.add(Double.parseDouble(elements[i]));
                } else {
                    // If there's no speed multiplier, use a default value (e.g., 1.0)
                    speedVariabilityMultiplier.add(0.0);
                }
            }
        }
    
        return new Station(stationID, maxCapacity, multiflag, fifoflag, tasksCanBeDone, speedForTask, speedVariabilityMultiplier);
    }
    
    private static Task findTaskByID(String taskID, List<Task> allTasks) {
        for (Task task : allTasks) {
            if (task.getTaskID().equals(taskID)) {
                return task;
            }
        }
        return null; // Return null if no task with the given ID is found
    }
    
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String removeTrailingSpacesAndClosingParentheses(String input) {
        // Loop through the string until the last character is not a space or closing parenthesis
        while (!input.isEmpty() && (input.charAt(input.length() - 1) == ' ' || input.charAt(input.length() - 1) == ')')) {
            // Remove the last character
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }
  
}
    

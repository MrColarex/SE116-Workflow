import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class StationParser {
    public static List<Station> parseStations(File workflowFile) {
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
                    stations.add(parseStation(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    private static Station parseStation(String line) {
        String[] elements = line.split(" ");
        String stationID = elements[0].substring(1);
        int maxCapacity = Integer.parseInt(elements[1]);
        boolean multiflag = elements[2].equals("Y");
        boolean fifoflag = elements[3].equals("Y");
    
        List<Task> tasksCanBeDone = new ArrayList<>();
        List<Double> speedForTask = new ArrayList<>();
        List<Double> speedVariabilityMultiplier = new ArrayList<>();
    
        String currentTaskID = null;
        double currentTaskSize = 0.0;
    
        for (int i = 4; i < elements.length; i++) {
            String element = elements[i];
            try {
                // Try to parse the element as a double (task size)
                double size = Double.parseDouble(element);
    
                if (currentTaskID != null) {
                    // Add the previously parsed task with its size and speed multiplier
                    tasksCanBeDone.add(new Task(currentTaskID, size));
                    speedForTask.add(size);
                    speedVariabilityMultiplier.add(0.0); // Assuming default multiplier as 0
                }
    
                // Update current task ID and size
                currentTaskID = null;
                currentTaskSize = size;
            } catch (NumberFormatException e) {
                // If parsing as a double fails, assume it's a task ID
                if (currentTaskID != null) {
                    // Add the previously parsed task with default size and speed multiplier
                    tasksCanBeDone.add(new Task(currentTaskID, currentTaskSize));
                    speedForTask.add(currentTaskSize);
                    speedVariabilityMultiplier.add(0.0); // Assuming default multiplier as 0
                }
                currentTaskID = element;
            }
        }
    
        // Add the last task if it exists
        if (currentTaskID != null) {
            tasksCanBeDone.add(new Task(currentTaskID, currentTaskSize));
            speedForTask.add(currentTaskSize);
            speedVariabilityMultiplier.add(0.0); // Assuming default multiplier as 0
        }
    
        return new Station(stationID, maxCapacity, multiflag, fifoflag, tasksCanBeDone, speedForTask, speedVariabilityMultiplier, "active");
    }
    
    
}
    

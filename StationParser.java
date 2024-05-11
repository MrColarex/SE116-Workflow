import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class StationParser {
    public static List<Station> parseStations(File workflowFile) {
        String line;
        String currentTaskID = null;
        int indexOfType = 0;
        List<String> stationElements = new ArrayList<>(); // List to store elements of STATIONS
        List<Station> stations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile))) {
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Removing spaces at the beginning and at the end
                if (line.isEmpty()) {
                    continue; // Skip to the next iteration if line is empty
                }
                // Check if the line represents task types
                if (line.startsWith("(STATIONS"))
                    indexOfType = 2; // Set indexOfType to 0 for STATIONS
    

                if (indexOfType == 2) {
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        stationElements.add(element); // Add elements to the STATIONS list
                    }
                }
                
            }

            // Remove the first element "STATIONS"
            stationElements.remove(0);
            stationElements.remove(stationElements.size() - 1);

            // Print the station elements list after the loop to check
            System.out.println("Station Elements:");
            for (String element : stationElements) {
                System.out.println(element);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }
}

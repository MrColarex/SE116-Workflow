import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JobParser {
    public static List<Job> parseJobFile(String filename) {
        List<Job> jobs = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;

                // Split the line by whitespace characters
                String[] parts = line.split("\\s+");

                // Check if the correct number of fields is present
                if (parts.length != 4) {
                    System.out.println("Syntax error at line " + lineNumber + ": Invalid number of fields");
                    continue;
                }

                // Extract jobID, jobTypeID, startTime, and duration
                String jobID = parts[0];
                String jobTypeID = parts[1];
                int startTime;
                int duration;
                try {
                    startTime = Integer.parseInt(parts[2]);
                    duration = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Semantic error at line " + lineNumber + ": Non-numeric value for startTime or duration");
                    continue;
                }

                // Create the Job object
                Job job = new Job(jobID, jobTypeID, startTime, duration);
                jobs.add(job);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Job file not found");
        }

        return jobs;
    }
}

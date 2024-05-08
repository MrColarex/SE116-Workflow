import java.io.*;
import java.util.*;


public class JobParser {
    public static List<Job> parseJobFile(String filename) {
        List<Job> jobs = new ArrayList<>();
        Set<String> jobIDs = new HashSet<>();

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
                int duration;
                int startTime;
                try {
                    duration = Integer.parseInt(parts[2]);
                    startTime = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Semantic error at line " + lineNumber + ": Non-numeric value for startTime or duration");
                    continue;
                }

                // Check if jobID is unique
                if (jobIDs.contains(jobID)) {
                    System.out.println("Semantic error at line " + lineNumber + ": Duplicate jobID");
                    continue;
                }
                jobIDs.add(jobID);

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

import java.io.*;
import java.util.*;

public class JobParser {
    public static List<Job> parseJobFile(String filename, List<JobType> jobTypes) {
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
                String jobTypeIDString = parts[1]; // Assuming jobTypeID is a String
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

                // Find the matching JobType object based on jobTypeID
                JobType matchedJobType = null;
                for (JobType jobType : jobTypes) {
                    if (jobType.getJobTypeID().equals(jobTypeIDString)) {
                        matchedJobType = jobType;
                        break;
                    }
                }

                if (matchedJobType == null) {
                    System.out.println("Semantic error at line " + lineNumber + ": JobType with ID " + jobTypeIDString + " not found");
                    continue;
                }

                // Create the Job object
                Job job = new Job(jobID, matchedJobType, duration, startTime);
                jobs.add(job);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Job file not found");
        }

        return jobs;
    }

    public static void updateJobStatus(List<Job> jobs, List<Station> stations, int currentTime) {
        for (Station station : stations) {
            for (Job job : station.getexecutingJobs()) {
                if (job.getDeadline() <= currentTime) {
                    job.setCompleted(true); // İşin son teslim tarihini geçmişse işi tamamlanmış olarak işaretle
                }
            }
        }
    }
}

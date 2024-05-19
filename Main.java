import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Main <workflow_file_path> <output_file_path>");
            return;
        }

        String workflowFilePath = args[0];
        String jobFilePath = args[1];

        // Check if workflow file exists and is readable
        File workflowFile = new File(workflowFilePath);
        if (!workflowFile.exists() || !workflowFile.canRead()) {
            System.out.println("Error: Workflow file does not exist or is not accessible.");
            return;
        }

        // Check if output file exists and is writable
        File outputFile = new File(jobFilePath);
        if (!outputFile.exists()) {
            System.out.println("Error: Jobs file does not exist.");
            return;
        } else if (!outputFile.canWrite()) {
            System.out.println("Error: Jobs file exists but is not writable.");
            return;
        }

        // At this point, both the workflow file and output file are accessible and valid.
        System.out.println("Both files are ready for processing.");

        // Parse tasks jobTypes, stations and jobs.
        List<Task> tasks = TaskParser.parseTasks(workflowFile);
        List<JobType> jobTypes = JobTypeParser.parseJobTypes(workflowFilePath, tasks);
        List<Station> stations = StationParser.parseStations(workflowFile, tasks);
        List<Job> jobs = JobParser.parseJobFile(jobFilePath, jobTypes);


        Task.printParsedTasks(tasks); // in task class

        System.out.println("----------");

        Job.printParsedJobTypes(jobTypes); //in job class

        System.out.println("----------");

        for (Station station : stations) {   //in station class
            System.out.println(station.getView());
        }

        // Process and print job details
        Job.printJobDetails(jobs, stations); //in job class

        System.out.println("----------");

        Station.printStationStatus(stations); //in station class

        System.out.println("----------");

        TaskAssignment.printJobStatus(jobs); // in taskAssignment class

        double mainCurrentTime = 0.0;

        while (true) {
            boolean allJobsCompleted = true; // Assume all jobs are completed initially
            for (Job job : jobs) {
                if (!job.isCompleted()) {
                    allJobsCompleted = false; // At least one job is not completed
                    break; // Exit the loop since not all jobs are completed
                }
            }

            if (allJobsCompleted) {
                // All jobs are completed, exit the while loop
                break;
            }

            // If we are still continuing, this means there are still jobs to be done. 
            // Now if we are at the beginning of the while loop, instantly go to starting time of earliest job.
            if (mainCurrentTime == 0) {
                // Find the earliest time to start
                int earliestStartTime = Integer.MAX_VALUE;
                for (Job job : jobs) {
                    if (job.getStartTime() < earliestStartTime) {
                        earliestStartTime = job.getStartTime();
                    }
                }
                System.out.println("Job assignation:");
                mainCurrentTime = earliestStartTime;
                // Assign jobs to stations if their startTime matches the current time
                for (Job job : jobs) {
                    if (!job.isCompleted()) {
                        TaskAssignment.assignStationToJob(job, stations, 10);
                    }
                }
            }
            System.out.println();
            System.out.println();

            
            break; // TEMPORARY BREAK TO TEST
        }
        System.out.println("Job adding to execution list of station:");
        for (Station station : stations) {
            station.addToExecute();
        }
        System.out.println();
        System.out.println();
        Station.printStationStatus(stations);
    }
}
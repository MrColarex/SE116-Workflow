import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Main <workflow_file_path> <output_file_path>");
            return;
        }

        String workflowFilePath = args[0];
        String outputFilePath = args[1];

        // Check if workflow file exists and is readable
        File workflowFile = new File(workflowFilePath);
        if (!workflowFile.exists() || !workflowFile.canRead()) {
            System.out.println("Error: Workflow file does not exist or is not accessible.");
            return;
        }

        // Check if job file exists and is readable
        File jobFile = new File(outputFilePath);
        if (!jobFile.exists() || !jobFile.canRead()) {
            System.out.println("Error: Job file does not exist or is not accessible.");
            return;
        }

        // Parse tasks
        List<Task> tasks = TaskParser.parseTasks(workflowFile);
        // Parse job file
        List<Job> jobs = JobParser.parseJobFile(outputFilePath);
        System.out.println("----------");
        System.out.println();

        // Print job file contents
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----------");

        // Print parsed jobs
        for (Job job : jobs) {
            System.out.println();
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Type ID: " + job.getJobType());
            System.out.println("Start Time: " + job.getStartTime());
            System.out.println("Duration: " + job.getDuration());
            System.out.println();
        }

        // Print parsed tasks
        System.out.println("----------");
        System.out.println("Task Objects:");
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskID());
            System.out.println("Task Size: " + task.getTaskSize());
            System.out.println();
        }
        System.out.println("----------");

        // Parse stations
        List<Station> stations = StationParser.parseStations(workflowFile, tasks);

        // Print parsed job types
        System.out.println("----------");
        System.out.println("Job Type Objects:");
        List<JobType> jobTypes = JobTypeParser.parseJobTypes(outputFilePath, tasks);

        for (JobType jobType : jobTypes) {
            System.out.println("JobType ID: " + jobType.getJobTypeID());
            System.out.println("Tasks Sequence:");
            for (Task task : jobType.getTasksSequence()) {
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println();
        }
        System.out.println("----------");

        // Print station views
        for (Station station : stations) {
            System.out.println(station.getView());
        }

        // Process and print job details
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Start Time: " + job.getStartTime());
            System.out.println("Job Duration: " + job.getDuration());
            System.out.println("Job Deadline: " + job.getEndTime());

            // Simulate current time (e.g., job start time)
            int currentTime = job.getStartTime();

            // Update job status
            job.updateStatus(currentTime);

            // Check if job is completed
            if (job.isCompleted()) {
                System.out.println("Job is completed.");
            } else {
                System.out.println("Job is still in progress.");
            }

            // Show remaining time for job completion
            if (!job.isCompleted()) {
                int timeRemaining = job.getEndTime() - currentTime;
                System.out.println("Time remaining for job completion: " + timeRemaining);
            }

            // Show how late the job is if past deadline
            if (currentTime > job.getEndTime()) {
                int timeAfterDeadline = job.getTimeAfterDeadline(currentTime);
                System.out.println("Job completed " + timeAfterDeadline + " units after deadline.");
            }

            System.out.println();
        }
    }
}

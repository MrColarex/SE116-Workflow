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

        // Parse tasks jobTypes and stations.
        List<Task> tasks = TaskParser.parseTasks(workflowFile);
        List<JobType> jobTypes = JobTypeParser.parseJobTypes(workflowFilePath, tasks);
        List<Station> stations = StationParser.parseStations(workflowFile, tasks);

        // Parse job file
        List<Job> jobs = JobParser.parseJobFile(jobFilePath, jobTypes);

        System.out.println("----------");
        System.out.println();


        // Print job file contents
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jobFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----------");

        // Print parsed tasks
        System.out.println("----------");
        System.out.println("Task Objects:");
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskID());
            System.out.println("Task Size: " + task.getTaskSize());
            System.out.println();
        }
        System.out.println("----------");

        // Print parsed job types
        System.out.println("----------");
        System.out.println("Job Type Objects:");
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
            System.out.println("Job Type ID: " + job.getJobType().getJobTypeID());
            System.out.println("Job Start Time: " + job.getStartTime());
            System.out.println("Job Duration: " + job.getDuration());
            for (Task task : job.getJobType().getTasksSequence()) {
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println("Job Deadline: " + job.getDeadline());

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
                int timeRemaining = job.getDeadline() - currentTime;
                System.out.println("Time remaining for job completion: " + timeRemaining);
            }

            // Show how late the job is if past deadline
            if (currentTime > job.getDeadline()) {
                int timeAfterDeadline = job.getTimeAfterDeadline(currentTime);
                System.out.println("Job completed " + timeAfterDeadline + " units after deadline.");
            }

            System.out.println();
        }

        System.out.println("Station Status:");
        for (Station station : stations) {
            System.out.println("Station ID: " + station.getStationID());
            System.out.println("Status: " + station.getStatus());
            System.out.println("Executing Tasks: " + station.getExecutingTasks());
            System.out.println("Waiting Tasks: " + station.getWaitingTasks());
            System.out.println();
        }

// Görevlerin durumunu kontrol et
        System.out.println("Job Status:");
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Status: " + (job.isCompleted() ? "Completed" : "In Progress"));
            System.out.println();
        }



        // 2. Bir Job Atama İşlemi Yapın
        for (Job job : jobs) {
            TaskAssignment.assignStationToJob(job, stations);
        }

    }
}

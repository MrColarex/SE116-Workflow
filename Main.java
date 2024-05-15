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

        File workflowFile = new File(workflowFilePath);
        if (!workflowFile.exists() || !workflowFile.canRead()) {
            System.out.println("Error: Workflow file does not exist or is not accessible.");
            return;
        }

        File jobFile = new File(outputFilePath);
        if (!jobFile.exists() || !jobFile.canRead()) {
            System.out.println("Error: Job file does not exist or is not accessible.");
            return;
        }
		
        TaskParser.parseTasks(workflowFile);


        System.out.println("----------");

        // İş dosyasını ayrıştır
        String jobFilename = "jobs.txt";
        List<Job> jobs = JobParser.parseJobFile(jobFilename);

        System.out.println("----------");
        System.out.println();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jobFilename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("----------");

        // Ayrıştırılan işleri yazdır
        for (Job job : jobs) {
            System.out.println();
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Type ID: " + job.getJobType());
            System.out.println("Start Time: " + job.getStartTime());
            System.out.println("Duration: " + job.getDuration());
            System.out.println();
        }


        //This is for printing Task Objects to make sure it works.
        System.out.println("----------");
        System.out.println("Task Objects:");
        List<Task> tasks = TaskParser.parseTasks(workflowFile);
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskID());
            System.out.println("Task Size: " + task.getTaskSize());
            System.out.println();
        }
        System.out.println("----------");
        
        List<Station> stations = StationParser.parseStations(workflowFile);

        for (Station station : stations) {
            System.out.println(station);
        }
        System.out.println("----------");
        System.out.println("Job Type Objects:");

        String jobWorkflowFile = "workflow.txt";
        List<Task> tasksFromWorkflow = WorkflowParser.parseWorkflow(new File(jobWorkflowFile));

        // Parse job types using the obtained tasks from the workflow file
        List<JobType> jobTypes = JobTypeParser.parseJobTypes(jobWorkflowFile, tasksFromWorkflow);

        // Display job types and their tasks
        for (JobType jobType : jobTypes) {
            System.out.println("JobType ID: " + jobType.getJobTypeID());
            System.out.println("Tasks Sequence:");
            for (Task task : jobType.getTasksSequence()) {
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println();
        }
        System.out.println("----------");
    }
}

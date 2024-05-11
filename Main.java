import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
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
        System.out.println("----------");
        System.out.println("----------");

        // İş dosyasını ayrıştır
        String jobFilename = "jobs.txt";
        List<Job> jobs = JobParser.parseJobFile(jobFilename);
        System.out.println("----------");
        System.out.println("----------");
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
        System.out.println("----------");
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

    }
}
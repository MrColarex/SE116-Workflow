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
		
        List<Task> tasks = TaskParser.parseTasks(workflowFile);


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
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskID());
            System.out.println("Task Size: " + task.getTaskSize());
            System.out.println();
        }
        System.out.println("----------");
        
        List<Station> stations = StationParser.parseStations(workflowFile, tasks);

        System.out.println("----------");
        System.out.println("Job Type Objects:");

        String jobWorkflowFile = "workflow.txt";
        List<JobType> jobTypes = JobTypeParser.parseJobTypes(jobWorkflowFile, tasks);

        for (JobType jobType : jobTypes) {
            System.out.println("JobType ID: " + jobType.getJobTypeID());
            System.out.println("Tasks Sequence:");
            for (Task task : jobType.getTasksSequence()) {
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println();
        }
        System.out.println("----------");
        
        for (Station station : stations) {
            System.out.println(station.getView());
        }
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Start Time: " + job.getStartTime());
            System.out.println("Job Duration: " + job.getDuration());

            // Şu anki zamanı simüle edelim (örneğin, iş başlama zamanı)
            int currentTime = job.getStartTime();

            // İşin durumunu güncelleyelim (örneğin, iş başlama zamanı)
            job.updateStatus(currentTime);

            // İşin tamamlanma durumunu kontrol edelim
            if (job.isCompleted()) {
                System.out.println("Job is completed.");
            } else {
                System.out.println("Job is still in progress.");
            }

            // Eğer iş tamamlanmadıysa, işin bitiş zamanına ne kadar kaldığını gösterelim
            if (!job.isCompleted()) {
                int timeRemaining = job.getEndTime() - currentTime;
                System.out.println("Time remaining for job completion: " + timeRemaining);
            }
            
            // İşin bitiş zamanı geçmişse, işin ne kadar geç tamamlandığını gösterelim
            if (currentTime > job.getEndTime()) {
                int timeAfterDeadline = job.getTimeAfterDeadline(currentTime);
                System.out.println("Job completed " + timeAfterDeadline + " units after deadline.");
            }
            
            System.out.println();
        }
    }
}
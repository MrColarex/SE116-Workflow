import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JobTypeParser {
    public static List<JobType> parseWorkflow(File workflowFile) {
        String line;
        String currentTaskID = null;

        List<String> jobTypeElements = new ArrayList<>(); // List to store elements of JOBTYPES
        List<JobType> jobs = new ArrayList<>();

        int indexOfType = 0; //indexOfType tells which type are we working on (jobtypes stations or tasktypes.)

        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile))) {
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Removing spaces at the beginning and at the end
                if (line.isEmpty()) {
                    continue; // Skip to the next iteration if line is empty
                }
                if (line.startsWith("(JOBTYPES"))
                    indexOfType = 1;

                if (indexOfType == 1) {
                    String[] elements = line.split(" ");
                    for (String element : elements) {
                        jobTypeElements.add(element); // Add elements to the JOBTYPES list
                    }
                }

                String input = "(JOBTYPES\n" +
                        "(J1 T1 1 T2 T3 3)\n" +
                        "(J2 T2 T3 T4 1 )\n" +
                        "(J3 T2)\n" +
                        "(J2 T21 5 T1 2)\n" +
                        ")";

                // Remove parentheses and split into individual job type strings
                String[] jobTypeStrings = input.replace("(", "").split("\\)\\s*\\(");

                List<JobType> jobTypes = new ArrayList<>();

                // Iterate over job type strings to create JobType objects
                for (String jobTypeString : jobTypeStrings) {
                    // Split the job type string into job type ID and task sequences
                    String[] parts = jobTypeString.split("\\s+");
                    String jobTypeID = parts[0];
                    List<Task> tasksSequence = new ArrayList<>();

                    // Iterate over the remaining parts to create Task objects for the task sequence
                    for (int i = 1; i < parts.length; i += 2) {
                        String taskID = parts[i];
                        double taskSize = 0.0; // Default task size
                        if (i + 1 < parts.length) {
                            try {
                                taskSize = Double.parseDouble(parts[i + 1]);
                            } catch (NumberFormatException e) {
                                System.err.println("Error parsing task size: " + parts[i + 1]);
                            }
                        }
                        Task task = new Task(taskID, taskSize);
                        tasksSequence.add(task);
                    }

                    // Create and add the JobType object to the list
                    JobType jobType = new JobType(jobTypeID, tasksSequence);
                    jobTypes.add(jobType);
                }

                // Print the created JobType objects
                for (JobType jobType : jobTypes) {
                    System.out.println("Job Type ID: " + jobType.getJobTypeID());
                    System.out.println("Tasks Sequence:");
                    for (Task task : jobType.getTasksSequence()) {
                        System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
                    }
                    System.out.println();
                }



            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
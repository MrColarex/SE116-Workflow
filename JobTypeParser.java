import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobTypeParser {

    public static List<JobType> parseJobTypes(String filePath, List<Task> tasksFromWorkflow) {
        List<JobType> jobTypes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean insideJobTypes = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("(JOBTYPES")) {
                    insideJobTypes = true;
                } else if (line.startsWith("(STATIONS")) {
                    insideJobTypes = false; // Stop parsing job types if stations part is encountered
                } else if (insideJobTypes) {
                    JobType jobType = parseJobType(line, tasksFromWorkflow);
                    if (jobType != null) {
                        jobTypes.add(jobType);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return jobTypes;
    }

    private static JobType parseJobType(String line, List<Task> tasksFromWorkflow) {
        List<Task> tasks = new ArrayList<>();
        line = line.substring(1, line.length() - 1).trim(); // Remove parenthesis from the line and trim any extra spaces

        String[] parts = line.split(" ");
        String jobTypeID = parts[0];
        for (int i = 1; i < parts.length; i++) {
            String taskID = parts[i].replaceAll("[^a-zA-Z0-9_]", ""); // Remove any non-alphanumeric characters from task ID
            double taskSize = findTaskSize(taskID, tasksFromWorkflow);

            // Check if the next part is a number to assign as the task size
            if (i + 1 < parts.length && parts[i + 1].matches("\\d+(\\.\\d+)?")) {
                taskSize = Double.parseDouble(parts[i + 1]);
                i++; // Increment i to skip the task size in the next iteration
            }
            tasks.add(new Task(taskID, taskSize));
        }

        return new JobType(jobTypeID, tasks);
    }

    private static double findTaskSize(String taskID, List<Task> tasksFromWorkflow) {
        for (Task task : tasksFromWorkflow) {
            if (task.getTaskID().equals(taskID)) {
                return task.getTaskSize();
            }
        }
        // If the task size is not found, return a default size or throw an exception, depending on your requirements
        return 2.0; // Default size
    }
}
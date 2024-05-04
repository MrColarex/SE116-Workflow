import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class WorkflowParser {

    public static void parseWorkflow(File workflowFile, File jobFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(workflowFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(jobFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Check if the line represents task types
                if (line.startsWith("(TASKTYPES")) {
                    List<TaskType> taskTypes = parseTaskTypes(line);
                    List<TaskType> validTaskTypes = new ArrayList<>();

                    for (TaskType taskType : taskTypes) {
                        if (taskType.getDefaultTaskSize() >= 0) {
                            validTaskTypes.add(taskType);
                        } else {
                            System.out.println(taskType.getTaskTypeID() + " default task size was negative. Converted to positive.");
                            // Convert negative default task sizes to positive
                            taskType.setDefaultTaskSize(Math.abs(taskType.getDefaultTaskSize()));
                            validTaskTypes.add(taskType);
                        }
                    }

                    // Update task types list
                    taskTypes = validTaskTypes;

                    // Write updated task types to jobs.txt
                    bw.write("(TASKTYPES ");
                    for (TaskType taskType : taskTypes) {
                        bw.write(taskType.getTaskTypeID() + " " + taskType.getDefaultTaskSize() + " ");
                    }
                    bw.write(")");
                    bw.newLine();
                } else {
                    // If not task types, simply copy the line to jobs.txt
                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Workflow copied to jobs.txt successfully.");
        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // Helper method to parse task types from a line
    private static List<TaskType> parseTaskTypes(String line) {
        List<TaskType> taskTypes = new ArrayList<>();
        // Assuming task types are represented as "(TASKTYPES T1 1 T2 2 ...)"
        String[] parts = line.split("\\s+");
        for (int i = 1; i < parts.length; i += 2) {
            String taskTypeID = parts[i];
            String sizeString = parts[i + 1];
            double defaultTaskSize;
            try {
                defaultTaskSize = Double.parseDouble(sizeString);
                taskTypes.add(new TaskType(taskTypeID, defaultTaskSize));
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid default task size for TaskType " + taskTypeID);
                // You can handle this error as needed, e.g., skip this TaskType or mark it as invalid
            }
        }
        return taskTypes;
    }
}

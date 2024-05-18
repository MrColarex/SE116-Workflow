import java.util.List;

public class Task {
    private String taskID;
    private double taskSize; 

    // Constructor
    public Task(String taskID, double taskSize) { // Modified parameter to double
        this.taskID = taskID;
        this.taskSize = taskSize;
    }

    // Getters and setters
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public double getTaskSize() { // Modified return type to double
        return taskSize;
    }

    public void setTaskSize(double taskSize) { // Modified parameter type to double
        this.taskSize = taskSize;
    }
    // Override toString method
    @Override
    public String toString() {
        return taskID; // Just return the task ID when converting Task object to String
    }
    public static void printParsedTasks(List<Task> tasks) {
        System.out.println("----------");
        System.out.println("Task Objects:");
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskID());
            System.out.println("Task Size: " + task.getTaskSize());
            System.out.println();
        }
    }
}

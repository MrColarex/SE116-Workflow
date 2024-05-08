public class Task {
    private String taskID;
    private double taskSize; // Modified to double

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
}

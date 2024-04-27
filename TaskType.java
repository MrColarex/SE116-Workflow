public class TaskType {
    private String taskTypeID;
    private double defaultTaskSize;

    // Constructor
    public TaskType(String taskTypeID, double defaultTaskSize) {
        this.taskTypeID = taskTypeID;
        this.defaultTaskSize = defaultTaskSize;
    }

    // Getters and setters
    public String getTaskTypeID() {
        return taskTypeID;
    }

    public void setTaskTypeID(String taskTypeID) {
        this.taskTypeID = taskTypeID;
    }

    public double getDefaultTaskSize() {
        return defaultTaskSize;
    }

    public void setDefaultTaskSize(double defaultTaskSize) {
        this.defaultTaskSize = defaultTaskSize;
    }
}

public class Task {
    private String taskID;
    private TaskType taskType;
    private int taskSize;

    // Constructor
    public Task(String taskID, TaskType taskType, int taskSize) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.taskSize = taskSize;
    }

    // Getters and setters
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public int getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }
}

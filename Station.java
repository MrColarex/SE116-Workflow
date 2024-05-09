import java.util.List;

public class Station {
    private String stationID;
    private int capacity;
    private double speed;
    private List<Task> taskTypesHandled;
    boolean canHandleMultipleTasks;

    // Constructor
    public Station(String stationID, int capacity, boolean canHandleMultipleTasks, double speed, List<Task> taskTypesHandled) {
        this.stationID = stationID;
        this.capacity = capacity;
        this.canHandleMultipleTasks = canHandleMultipleTasks;
        this.speed = speed;
        this.taskTypesHandled = taskTypesHandled;
    }
    

    // Getters and setters
    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public List<Task> getTaskTypesHandled() {
        return taskTypesHandled;
    }

    public void setTaskTypesHandled(List<Task> taskTypesHandled) {
        this.taskTypesHandled = taskTypesHandled;
    }
    public boolean canHandleMultipleTasks() {
        return this.canHandleMultipleTasks;
    }
    

    // Method to check if a station can handle a specific task type
    public boolean canHandleTaskType(Task taskType) {
        return taskTypesHandled.contains(taskType);
    }

    // Method to execute tasks at the station, considering speed and capacity
    public void executeTask(Task task) {
        // Implementation of task execution
    }
}

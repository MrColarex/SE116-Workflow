import java.util.List;

public class Station {
    private String stationID;
    private int maxCapacity;
    private boolean multiflag;
    private boolean fifoflag;
    private List<Task> tasksCanBeDone;
    private List<Double> speedForTask;
    private List<Double> speedVariabilityMultiplier;
    private String status;

    // Constructor
    public Station(String stationID, int maxCapacity, boolean multiflag, boolean fifoflag,
                   List<Task> tasksCanBeDone, List<Double> speedForTask, List<Double> speedVariabilityMultiplier,
                   String status) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
        this.tasksCanBeDone = tasksCanBeDone;
        this.speedForTask = speedForTask;
        this.speedVariabilityMultiplier = speedVariabilityMultiplier;
        this.status = status;
    }

    // Getter methods
    public String getStationID() {
        return stationID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isMultiflag() {
        return multiflag;
    }

    public boolean isFifoflag() {
        return fifoflag;
    }

    public List<Task> getTasksCanBeDone() {
        return tasksCanBeDone;
    }

    public List<Double> getSpeedForTask() {
        return speedForTask;
    }

    public List<Double> getSpeedVariabilityMultiplier() {
        return speedVariabilityMultiplier;
    }

    public String getStatus() {
        return status;
    }

    // Method to get a view of the station
    public String getView() {
        String view = "Station ID: " + stationID + "\n" +
                      "Max Capacity: " + maxCapacity + "\n" +
                      "Multiflag: " + multiflag + "\n" +
                      "FIFOflag: " + fifoflag + "\n" +
                      "Tasks that can be done: ";
    
        // Append task names individually
        for (int i = 0; i < tasksCanBeDone.size(); i++) {
            if (i > 0) {
                view += ", ";
            }
            view += tasksCanBeDone.get(i).getTaskID();
        }
    
        view += "\nSpeed for Task: " + speedForTask + "\n" +
                "Speed Variability Multiplier: " + speedVariabilityMultiplier + "\n" +
                "Status: " + status + "\n";
    
        return view;
    }
    
}

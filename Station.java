import java.util.*;

public class Station {
    private String stationID;
    private int maxCapacity;
    private boolean multiflag;
    private boolean fifoflag;
    private List<Task> tasksCanBeDone;
    private List<Double> speedForTask;
    private List<Double> speedVariabilityMultiplier;
    private String status;
    private List<Job> waitingTasks; // List for waiting tasks (using ArrayList)
    private List<Job> executingTasks; // List for currently executing tasks

    private static int currentTime; // Static field for global current time

    public Station(String stationID, int maxCapacity, boolean multiflag, boolean fifoflag, List<Task> tasksCanBeDone,
                   List<Double> speedForTask, List<Double> speedVariabilityMultiplier) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
        this.tasksCanBeDone = tasksCanBeDone;
        this.speedForTask = speedForTask;
        this.speedVariabilityMultiplier = speedVariabilityMultiplier;
        this.status = "Idle";
        this.waitingTasks = new ArrayList<>(); // Using ArrayList for waitingTasks
        this.executingTasks = new ArrayList<>();
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
    // Method to change status to "Busy" if there are executing tasks
    public void updateStatus() {
        if (executingTasks.isEmpty()) {
            status = "Idle";
            System.out.println("Station is currently idle.");
        } else {
            status = "Busy";
            System.out.println("Station is currently busy.");
        }
    }
    
    public void selectTasksFCFS() {
        if (fifoflag) { // Check if FCFS strategy is specified for the station
            while (!waitingTasks.isEmpty() && executingTasks.size() < maxCapacity) {
                Job job = waitingTasks.remove(0); // Remove the first task in the waiting list
                executingTasks.add(job);
            }
        }
    }
   public void selectTasksEDF() {
    if (!fifoflag) { // Check if EDF strategy is specified for the station
        // Sort waiting tasks by deadline
        waitingTasks.sort(Comparator.comparingInt(Job::getdeadline));
        
        // Move tasks to executing list until max capacity is reached or no more tasks available
        while (!waitingTasks.isEmpty() && executingTasks.size() < maxCapacity) {
            Job job = waitingTasks.remove(0); // Remove the task with earliest deadline
            executingTasks.add(job);
        }
    }
}

    public void processTaskSelection() {
        if (fifoflag) {
            selectTasksFCFS(); // Use FCFS strategy
        } else {
            selectTasksEDF(); // Use EDF strategy
        }
        updateStatus(); // Update station status after task selection
    }

    
}

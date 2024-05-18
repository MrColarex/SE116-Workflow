import java.util.ArrayList;
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
    private List<Job> waitingTasks;
    private List<Job> executingTasks;
    private List<JobType> processableJobTypes;


    // Constructor
    public Station(String stationID, int maxCapacity, boolean multiflag, boolean fifoflag,
                   List<Task> tasksCanBeDone, List<Double> speedForTask, List<Double> speedVariabilityMultiplier) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
        this.tasksCanBeDone = tasksCanBeDone;
        this.speedForTask = speedForTask;
        this.speedVariabilityMultiplier = speedVariabilityMultiplier;
        this.status = "Idle";
        this.waitingTasks = new ArrayList<>();
        this.executingTasks = new ArrayList<>();
        this.processableJobTypes = new ArrayList<>();
    }

    // Getters and Setters
    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isMultiflag() {
        return multiflag;
    }

    public void setMultiflag(boolean multiflag) {
        this.multiflag = multiflag;
    }

    public boolean isFifoflag() {
        return fifoflag;
    }

    public void setFifoflag(boolean fifoflag) {
        this.fifoflag = fifoflag;
    }

    public List<Task> getTasksCanBeDone() {
        return tasksCanBeDone;
    }

    public void setTasksCanBeDone(List<Task> tasksCanBeDone) {
        this.tasksCanBeDone = tasksCanBeDone;
    }

    public List<Double> getSpeedForTask() {
        return speedForTask;
    }

    public void setSpeedForTask(List<Double> speedForTask) {
        this.speedForTask = speedForTask;
    }

    public List<Double> getSpeedVariabilityMultiplier() {
        return speedVariabilityMultiplier;
    }

    public void setSpeedVariabilityMultiplier(List<Double> speedVariabilityMultiplier) {
        this.speedVariabilityMultiplier = speedVariabilityMultiplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Job> getWaitingTasks() {
        return waitingTasks;
    }

    public void setWaitingTasks(List<Job> waitingTasks) {
        this.waitingTasks = waitingTasks;
    }

    public List<Job> getExecutingTasks() {
        return executingTasks;
    }

    public void setExecutingTasks(List<Job> executingTasks) {
        this.executingTasks = executingTasks;
    }

    // Method to get a view of the station
    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append("Station ID: ").append(stationID).append("\n");
        view.append("Max Capacity: ").append(maxCapacity).append("\n");
        view.append("Multiflag: ").append(multiflag).append("\n");
        view.append("FIFOflag: ").append(fifoflag).append("\n");
        view.append("Tasks that can be done: ");
        for (Task task : tasksCanBeDone) {
            view.append(task.getTaskID()).append(", ");
        }
        view.append("\nSpeed for Task: ").append(speedForTask).append("\n");
        view.append("Speed Variability Multiplier: ").append(speedVariabilityMultiplier).append("\n");
        view.append("Status: ").append(status).append("\n");
        return view.toString();
    }

    // Method to update station status
    public void updateStationStatus() {
        if (executingTasks.isEmpty() && waitingTasks.isEmpty()) {
            status = "Idle";
        } else {
            status = "Busy";
        }
    }





    // Method to add a job to the station
    public void addJob(Job job) {
        waitingTasks.add(job);
        updateStationStatus();
    }

    // Method to mark a job as completed at the station
    public void completeJob(Job job) {
        executingTasks.remove(job);
        updateStationStatus();




    }

    // İşleyebileceği iş türlerini eklemek için yöntem
    public void addProcessableJobType(JobType jobType) {
        processableJobTypes.add(jobType);
    }

    // İşleyebileceği iş türlerini kontrol etmek için yöntem
    public boolean canProcessJobType(JobType jobType) {
        return processableJobTypes.contains(jobType);
    }

    // Diğer getter ve setter metotları burada olacak
}




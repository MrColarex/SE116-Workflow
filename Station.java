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
    private List<Job> waitingJobs;
    private List<Job> executingJobs;

    //Current time as static number beacause time is same for all stations. 
    public static double currentTime = 0;

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
        this.waitingJobs = new ArrayList<>();
        this.executingJobs = new ArrayList<>();
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

    public List<Job> getwaitingJobs() {
        return waitingJobs;
    }

    public void setwaitingJobs(List<Job> waitingJobs) {
        this.waitingJobs = waitingJobs;
    }

    public List<Job> getexecutingJobs() {
        return executingJobs;
    }

    public void setexecutingJobs(List<Job> executingTasks) {
        this.executingJobs = executingTasks;
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
        if (executingJobs.isEmpty() && waitingJobs.isEmpty()) {
            status = "Idle";
        } else if (waitingJobs.size() + executingJobs.size() >= maxCapacity) {
            status = "Full"; // Update status to "Full" when at max capacity
        } else {
            status = "Busy";
        }
    }

    public static void printStationStatus(List<Station> stations) {
        System.out.println("Station Status:");
        for (Station station : stations) {
            System.out.println("Station ID: " + station.getStationID());
            System.out.println("Status: " + station.getStatus());
            System.out.print("Executing Tasks: ");
            for (Job executingJob : station.getexecutingJobs()) {
                System.out.print(executingJob.getJobID() + " "); // Print ID of each executing job
            }
            System.out.println(); // Add a newline after printing executing tasks
    
            System.out.print("Waiting Tasks: ");
            for (Job waitingJob : station.getwaitingJobs()) {
                System.out.print(waitingJob.getJobID() + " "); // Print ID of each waiting job
            }
            System.out.println(); // Add a newline after printing waiting tasks
            System.out.println();
        }
    }
    
    public void executeTasksForAllJobs() {
        for (Job job : executingJobs) {
            Task task = job.getNextTask();
            if (task != null) {
                double speed = getSpeedForTask(task);
                double size = task.getTaskSize();
                double time = size / speed;

                int taskIndex = -1;
                for (int i = 0; i < tasksCanBeDone.size(); i++) {
                    if (tasksCanBeDone.get(i).getTaskID().equals(task.getTaskID())) {
                        taskIndex = i;
                        break;
                    }
                }
                if (taskIndex != -1) { // Ensure the task is in the list
                    double variability = speedVariabilityMultiplier.get(taskIndex);
                    double minMultiplier = 1.0 - variability;
                    double maxMultiplier = 1.0 + variability;
                    double randomizedMultiplier = minMultiplier + Math.random() * (maxMultiplier - minMultiplier);
                    time *= randomizedMultiplier;
                } else {
                    System.out.println("Task " + task.getTaskID() + " is not in the list of tasks that can be done.");
                    continue; // Skip to the next iteration
                }

                currentTime += time;
                job.completeTask(task); // Mark the task as completed
                System.out.println("Task " + task.getTaskID() + " executed at Station " + stationID + " in " + time + " units of time.");

                if (job.isCompleted()) {
                    System.out.println("Job " + job.getJobID() + " is completed.");
                    completeJob(job); // Mark the job as completed at the station
                } else {
                    job.updateStatus((int) currentTime); // Update job status
                }
            } else {
                System.out.println("No tasks remaining for job " + job.getJobID());
            }
        }
    }
    
    
    
    
    // Method to get speed for a task
    private double getSpeedForTask(Task task) {
        int index = tasksCanBeDone.indexOf(task);
        if (index != -1) {
            return speedForTask.get(index);
        }
        return 0; // Return 0 if speed is not found for the task
    }

    // Updated method to add a job to the station
    public void addJob(Job job) {
        if (waitingJobs.size() + executingJobs.size() < maxCapacity) {
            waitingJobs.add(job);
            updateStationStatus();
        } else {
            System.out.println("Station " + stationID + " is at full capacity.");
        }
    }

    // Method to start a job at the station
    public void startJob(Job job) {
        if (executingJobs.size() < maxCapacity) {
            executingJobs.add(job); // Add the job to the list of executing tasks
            waitingJobs.remove(job); // Remove the job from the waiting list
            updateStationStatus();
        } else {
            System.out.println("Station " + stationID + " cannot start a new job as it is at full capacity.");
        }
    }
    public void addToExecute() {
        if (!waitingJobs.isEmpty()) {
            if (fifoflag) {
                // FIFO flag is true, add jobs from the beginning of the waiting list
                while (!waitingJobs.isEmpty() && executingJobs.size() < maxCapacity) {
                    Job jobToExecute = waitingJobs.remove(0); // Remove the first job from waiting list
                    executingJobs.add(jobToExecute); // Add the job to executing list
                    System.out.println(jobToExecute.getJobID() + " added to execution list of station " + stationID);
                }
            } else {
                // FIFO flag is false, add jobs with the earliest deadlines
                while (!waitingJobs.isEmpty() && executingJobs.size() < maxCapacity) {
                    Job jobToExecute = findJobWithEarliestDeadline();
                    if (jobToExecute != null) {
                        waitingJobs.remove(jobToExecute); // Remove the job from waiting list
                        executingJobs.add(jobToExecute); // Add the job to executing list
                        System.out.println(jobToExecute.getJobID() + " added to execution list of station " + stationID);
                    } else {
                        System.out.println("No jobs in the waiting list of station " + stationID);
                        break; // Exit the loop if no jobs are found
                    }
                }
            }
            updateStationStatus(); // Update station status after adding jobs
        } else {
            System.out.println("No jobs in the waiting list of station " + stationID);
        }
    }
    
    // Method to find job with earliest deadline in waiting tasks
    private Job findJobWithEarliestDeadline() {
        if (!waitingJobs.isEmpty()) {
            Job earliestJob = waitingJobs.get(0);
            for (Job job : waitingJobs) {
                if (job.getDeadline() < earliestJob.getDeadline()) {
                    earliestJob = job;
                }
            }
            return earliestJob;
        }
        return null;
    }
    
    // Method to mark a job as completed at the station

    public void completeJob(Job job) {
        executingJobs.remove(job);
        updateStationStatus();
    }

    // Check if the station can process a given task
    public boolean canProcessTask(Task task) {
        return tasksCanBeDone.contains(task);
    }
}

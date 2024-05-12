import java.util.List;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private JobType jobType;
    private List<Task> tasks;

    // Constructor
    public Job(String jobID, JobType jobType, int duration, int startTime) {
        this.jobID = jobID;
        this.duration = duration;
        this.jobType = jobType;
        this.startTime = startTime;
    }

    // Getters and setters
    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }



    // Method to add tasks to the job
    public void addTask(Task task) {
        tasks.add(task);
    }
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}

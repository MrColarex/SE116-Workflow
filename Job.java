import java.util.List;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private JobType jobType;
    private List<Task> tasks;
    private int endTime;
    private boolean completed; 

    // Constructor
    public Job(String jobID, JobType jobType, int duration, int startTime) {
        this.jobID = jobID;
        this.duration = duration;
        this.jobType = jobType;
        this.startTime = startTime;
        this.endTime = startTime + duration; // Bitiş zamanını hesapla
        this.completed = false; // İş henüz tamamlanmadı
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Method to add tasks to the job
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to update job status based on current time
    public void updateStatus(int currentTime) {
        if (currentTime >= endTime) {
            completed = true; // İş tamamlandı
        }
    }

    // Method to get the time remaining or exceeded after the deadline
    public int getTimeAfterDeadline(int currentTime) {
        if (completed) {
            return Math.max(0, currentTime - endTime); // Geçen süre, eğer iş tamamlandıysa
        } else {
            return Math.max(0, endTime - currentTime); // Zamanı geçen süre, eğer iş tamamlanmadıysa
        }

    }
}
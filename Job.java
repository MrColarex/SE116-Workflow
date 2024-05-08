public class Job {
    private String jobID;
    private int duration;
    private JobType jobType;
    private int startTime; // Changed from List<Task> tasks to int startTime

    // Constructor
    public Job(String jobID, int duration, JobType jobType, int startTime) {
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}

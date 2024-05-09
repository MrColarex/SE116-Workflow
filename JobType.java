import java.util.List;

public class JobType {
    private String jobTypeID;
    private List<Task> tasksSequence;

    // Constructor
    public JobType(String jobTypeID, List<Task> tasksSequence) {
        this.jobTypeID = jobTypeID;
        this.tasksSequence = tasksSequence;
    }

    // Getters and setters
    public String getJobTypeID() {
        return jobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
    }

    public List<Task> getTasksSequence() {
        return tasksSequence;
    }

    public void setTasksSequence(List<Task> tasksSequence) {
        this.tasksSequence = tasksSequence;
    }
}

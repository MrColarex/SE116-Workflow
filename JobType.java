import java.util.List;

public class JobType {
    private String jobTypeID;
    private List<TaskType> tasksSequence;

    // Constructor
    public JobType(String jobTypeID, List<TaskType> tasksSequence) {
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

    public List<TaskType> getTasksSequence() {
        return tasksSequence;
    }

    public void setTasksSequence(List<TaskType> tasksSequence) {
        this.tasksSequence = tasksSequence;
    }
}

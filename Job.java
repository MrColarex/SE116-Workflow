import java.util.ArrayList;
import java.util.List;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private JobType jobType;
    private List<Task> tasks;
    private int deadline;
    private boolean completed;

    // Constructor
    public Job(String jobID, JobType jobType, int startTime, int duration) {
        this.jobID = jobID;
        this.duration = duration;
        this.jobType = jobType;
        this.startTime = startTime;
        this.deadline = startTime + duration;
        this.completed = false;
        this.tasks = new ArrayList<>(jobType.getTasksSequence()); // Initialize the tasks list
    }

    public String toString() {
        return "Job ID: " + jobID + ", StartTime: " + startTime + ", Duration: " + duration;
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

    public int getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {}

    // Method to add tasks to the job
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to update job status based on current time
    public void updateStatus(int currentTime) {
        if (currentTime >= deadline) {
            completed = true;
        }
    }

    public static void printParsedJobTypes(List<JobType> jobTypes) {
        System.out.println("----------");
        System.out.println("Job Type Objects:");
        for (JobType jobType : jobTypes) {
            System.out.println("JobType ID: " + jobType.getJobTypeID());
            System.out.println("Tasks Sequence:");
            for (Task task : jobType.getTasksSequence()) {
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println();
        }
    }

    public static void printJobDetails(List<Job> jobs, List<Station> stations) {
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Start Time: " + job.getStartTime());
            System.out.println("Job Duration: " + job.getDuration());
            System.out.println("Tasks for this job:");
            for (Task task : job.getTasks()) { // Iterate over tasks in the Job object
                System.out.println("Task ID: " + task.getTaskID() + ", Task Size: " + task.getTaskSize());
            }
            System.out.println("Job Deadline: " + job.getDeadline());
    
            // Simulate current time (e.g., job start time)
            int currentTime = job.getStartTime();
    
            // Update job status
            job.updateStatus(currentTime);
    
            // Check if job is completed
            if (job.isCompleted()) {
                System.out.println("Job is completed.");
            } else {
                System.out.println("Job is still in progress or haven't started yet.");
            }
    
            // Show remaining time for job completion
            if (!job.isCompleted()) {
                int timeRemaining = job.getDeadline() - currentTime;
                System.out.println("Time remaining for job completion: " + timeRemaining);
            }
    
            // Show how late the job is if past deadline
            if (currentTime > job.getDeadline()) {
                int timeAfterDeadline = job.getTimeAfterDeadline(currentTime);
                System.out.println("Job completed " + timeAfterDeadline + " units after deadline.");
            }
    
            System.out.println();
        }
    }
    

    // Method to get the time remaining or exceeded after the deadline
    public int getTimeAfterDeadline(int currentTime) {
        if (completed) {
            return Math.max(0, currentTime - deadline); // Geçen süre, eğer iş tamamlandıysa
        } else {
            return Math.max(0, deadline - currentTime); // Zamanı geçen süre, eğer iş tamamlanmadıysa
        }
    }
    public Task getNextTask() {
        if (!tasks.isEmpty()) {
            return tasks.get(0); // Return the first task from the list
        } else {
            return null; // Return null if there are no tasks remaining
        }
    }

    // Method to mark the current task as completed
    public void completeTask(Task task) {
        tasks.remove(task);
    }


    // Method to get the number of remaining tasks in the job
    public int getRemainingTasks() {
        return tasks.size();
    }
    

}
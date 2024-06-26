import java.util.List;

public class TaskAssignment {

    public static void printJobStatus(List<Job> jobs) {
        System.out.println("Job Status:");
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Job Status: " + (job.isCompleted() ? "Completed" : "In Progress or did not start yet"));
            System.out.println();
        }
    }
    // Method to find a suitable station for processing a job
    public static Station findSuitableStation(Job job, List<Station> stations) {
        // Iterate over each task in the job's job type's task sequence
        for (Task jobTask : job.getJobType().getTasksSequence()) {
            // Iterate over each station
            for (Station station : stations) {
                // Check if the station has capacity
                if (station.getwaitingJobs().size() < station.getMaxCapacity()) {
                    // Iterate over each task that the station can perform
                    for (Task stationTask : station.getTasksCanBeDone()) {
                        // Compare the task IDs
                        if (stationTask.getTaskID().equals(jobTask.getTaskID())) {
                            return station; // Return the first suitable station found
                        }
                    }
                }
            }
        }
        return null; // Return null if no suitable station is found
    }

    // Method to assign a station to a job
    public static void assignStationToJob(Job job, List<Station> stations, double currentTime) {
        // Check if the current time is equal to or greater than the job's start time
        if (currentTime < job.getStartTime()) {
            System.out.println(job.getJobID() + " cannot be assigned yet. Current time: " + currentTime + ", Start time: " + job.getStartTime());
            return; // Exit the method without assigning the job to any station
        }
    
        Station station = findSuitableStation(job, stations);
        if (station != null) {
            station.addJob(job); // Assign the job to the suitable station
            // Check if the station is at full capacity after adding the job
            if (station.getwaitingJobs().size() + station.getexecutingJobs().size() >= station.getMaxCapacity()) {
                station.setStatus("Full"); // Set the station's status to "Full"
            }
            System.out.println(job.getJobID() + " assigned to station " + station.getStationID());

        } else {
            System.out.println("No suitable station found for " + job.getJobID());

        }
    }
}

import java.util.List;

public class TaskAssignment {

    // Method to find a suitable station for processing a job
    public static Station findSuitableStation(Job job, List<Station> stations) {
        // Iterate over each task in the job's job type's task sequence
        for (Task jobTask : job.getJobType().getTasksSequence()) {
            // Iterate over each station
            for (Station station : stations) {
                // Check if the station has capacity
                if (station.getWaitingTasks().size() < station.getMaxCapacity()) {
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
    public static void assignStationToJob(Job job, List<Station> stations) {
        Station station = findSuitableStation(job, stations);
        if (station != null) {
            station.addJob(job); // Assign the job to the suitable station
            System.out.println("Job " + job.getJobID() + " assigned to station " + station.getStationID());
        } else {
            System.out.println("No suitable station found for job " + job.getJobID());
        }
    }
}

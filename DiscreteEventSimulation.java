import java.util.PriorityQueue;
import java.util.List;

public class DiscreteEventSimulation {
    private PriorityQueue<Event> eventQueue;
    private List<Station> stations;
    private int currentTime;

    public DiscreteEventSimulation(List<Station> stations) {
        this.stations = stations;
        this.eventQueue = new PriorityQueue<>();
        this.currentTime = 0;
    }

    public void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    public void start(List<Job> jobs) {
        // Initialize the event queue with job start events
        for (Job job : jobs) {
            eventQueue.add(new Event(job.getStartTime(), Event.JOB_START, job));
        }

        // Process events
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            currentTime = event.getTime();
            processEvent(event);
        }
    }

    // Method to process an event
    private void processEvent(Event event) {
        switch (event.getType()) {
            case Event.JOB_START:
                handleJobStart(event.getJob());
                break;
            case Event.TASK_COMPLETE:
                handleTaskComplete(event.getJob());
                break;
        }
        updateStationsStatus(); // Update the status of stations after processing each event
        printSystemState();
    }

    // Handle job start event
    private void handleJobStart(Job job) {
        System.out.println("Time " + currentTime + ": Job " + job.getJobID() + " starts.");
        Station station = TaskAssignment.findSuitableStation(job, stations);
        if (station != null) {
            station.addJob(job);
            int finishTime = currentTime + job.getDuration();
            eventQueue.add(new Event(finishTime, Event.TASK_COMPLETE, job));
        } else {
            System.out.println("No suitable station found for Job " + job.getJobID());
        }
    }

    // Handle task complete event
    private void handleTaskComplete(Job job) {
        System.out.println("Time " + currentTime + ": Job " + job.getJobID() + "'s task completed.");
        // Additional logic to handle the completion of tasks and moving to the next task if applicable
        // Example: job.advanceToNextTask();
        // Example: reschedule the next task if available or mark the job as complete
    }

    // Print the current system state
    private void printSystemState() {
        System.out.println("Current Time: " + currentTime);
        for (Station station : stations) {
            System.out.println("Station " + station.getStationID() + " status: " + station.getStatus());
        }
        System.out.println();
    }

    // Update the status of all stations
    private void updateStationsStatus() {
        for (Station station : stations) {
            station.updateStationStatus();
        }
    }
}

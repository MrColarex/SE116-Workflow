import java.util.PriorityQueue;
import java.util.List;

// Event class to represent events in the system
class Event implements Comparable<Event> {
    public static final int JOB_START = 0;
    public static final int TASK_COMPLETE = 1;

    private int time;
    private int type;
    private Job job;

    public Event(int time, int type, Job job) {
        this.time = time;
        this.type = type;
        this.job = job;
    }

    public int getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public Job getJob() {
        return job;
    }

    // Compare events based on their time
    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.time, other.time);
    }
}
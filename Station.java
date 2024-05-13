public class Station {
    private String stationID;
    private int maxCapacity;
    private boolean multiflag;
    private boolean fifoflag;
    private double stationSpeed;
    private double speedVariabilityPercentage;
    private String status;

    // Constructor
    public Station(String stationID, int maxCapacity, boolean multiflag, boolean fifoflag, double stationSpeed, double speedVariabilityPercentage, String status) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.multiflag = multiflag;
        this.fifoflag = fifoflag;
        this.stationSpeed = stationSpeed;
        this.speedVariabilityPercentage = speedVariabilityPercentage;
        this.status = status;
    }
    
    // Getters and Setters
    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isMultiflag() {
        return multiflag;
    }

    public void setMultiflag(boolean multiflag) {
        this.multiflag = multiflag;
    }

    public boolean isFifoflag() {
        return fifoflag;
    }

    public void setFifoflag(boolean fifoflag) {
        this.fifoflag = fifoflag;
    }

    public double getStationSpeed() {
        return stationSpeed;
    }

    public void setStationSpeed(double stationSpeed) {
        this.stationSpeed = stationSpeed;
    }

    public double getSpeedVariabilityPercentage() {
        return speedVariabilityPercentage;
    }

    public void setSpeedVariabilityPercentage(double speedVariabilityPercentage) {
        this.speedVariabilityPercentage = speedVariabilityPercentage;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

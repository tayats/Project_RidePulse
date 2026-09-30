public class Ride {

    private String rideId;
    private String passengerName;
    private String pickupLocation;
    private String dropoffLocation;
    private double distanceKm;
    private FareStrategy fareStrategy;
    private double fare;
    private RideStatus status;
    private String driverName;

    public Ride (String rideId, String passengerName, String pickupLocation, String dropoffLocation,
                 double distanceKm, FareStrategy fareStrategy) {
        this.rideId = rideId;
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distanceKm = distanceKm;
        this.fareStrategy = fareStrategy;
        this.fare = fareStrategy.calculateFare(distanceKm);
        this.status = RideStatus.REQUESTED;
        this.driverName = "Unassigned";

    }
    public boolean assignDriver(String driverName) {
        if (this.status == RideStatus.REQUESTED) {
            this.driverName = driverName;
            this.status = RideStatus.DRIVER_ASSIGNED;
            return true;
        }
        return false;
    }

    public boolean startRide() {
        if (this.status == RideStatus.DRIVER_ASSIGNED) {
            this.status = RideStatus.IN_PROGRESS;
            return true;
        }
        return false;
    }

    public boolean completeRide() {
        if (this.status == RideStatus.IN_PROGRESS) {
            this.status = RideStatus.COMPLETED;
            return true;
        }
        return false;
    }

    public boolean cancelRide() {
        if (this.status == RideStatus.REQUESTED || this.status == RideStatus.DRIVER_ASSIGNED) {
            this.status = RideStatus.CANCELLED;
            return true;
        }
        return false;
    }

    public String getRideId() {
        return rideId;
    }

    public String getDriverName() {
        return driverName;
    }

    public RideStatus getStatus() {
        return status;
    }

    public double getFare() {
        return fare;
    }

    public FareStrategy getFareStrategy() {
        return fareStrategy;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getPassengerName() {
        return passengerName;
    }
    @Override
    public String toString() {
        return String.format("[%s] %s | %s -> %s (%.1f km) | $%.2f (%s) | Driver: %s | Status: %s",
                rideId, passengerName, pickupLocation, dropoffLocation, distanceKm, fare,
                fareStrategy.getStrategyName(), driverName, status);
    }
}

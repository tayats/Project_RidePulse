import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RideService {

    private ArrayList<Ride> rides;
    private int rideCounter;
    private final String FILE_NAME = "rides.txt";

    public RideService() {
        this.rides = new ArrayList<>();
        this.rideCounter = 101;

        FareStrategy standard = new StandardFare();
        rides.add(new Ride("RIDE-101", "Alice Smith", "Central Station", "Airport", 15.0, standard));

        FareStrategy surge = new SurgeFare();
        rides.add(new Ride("RIDE-102", "Bob Jones", "Downtown Mall", "Sports Stadium", 8.0, surge));

        this.rideCounter = 103;
        saveAllRiders();
    }

    public Optional<Ride> findRide(String rideId) {
        return rides.stream().filter(r -> r.getRideId().equalsIgnoreCase(rideId)).findFirst();
    }

    public Ride requestRide(String passenger, String pickup, String dropoff,
                            double distance, FareStrategy strategy) {
        String id = "RIDE-" + rideCounter++;
        Ride ride = new Ride(id, passenger, pickup, dropoff, distance, strategy);
        rides.add(ride);
        saveAllRiders();
        return ride;
    }

    public boolean assignDriver(String rideId, String driverName) {
        Optional<Ride> opt = findRide(rideId);
        if (opt.isPresent() && opt.get().assignDriver(driverName)) {
            saveAllRiders();
            return true;
        }
        return false;
    }

    public boolean startTrip(String rideId) {
        Optional<Ride> opt = findRide(rideId);
        if (opt.isPresent() && opt.get().startRide()) {
            saveAllRiders();
            return true;
        }
        return false;
    }

    public boolean completeTrip(String rideId) {
        Optional<Ride> opt = findRide(rideId);
        if (opt.isPresent() && opt.get().completeRide()) {
            saveAllRiders();
            return true;
        }
        return false;
    }

    public boolean cancelTrip(String rideId) {
        Optional<Ride> opt = findRide(rideId);
        if (opt.isPresent() && opt.get().cancelRide()) {
            saveAllRiders();
            return true;
        }
        return false;
    }

    public ArrayList<Ride> getAllRides() {
        return rides;
    }

    public List<Ride> getActiveRides() {
        return rides.stream().filter(r -> r.getStatus() != RideStatus.COMPLETED &&
                r.getStatus() != RideStatus.CANCELLED).collect(Collectors.toList());
    }

    public double calculateCompletedRevenue() {
        return rides.stream().filter(r -> r.getStatus() == RideStatus.COMPLETED)
                .mapToDouble(Ride::getFare).sum();
    }

    public void saveAllRiders() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Ride r : rides) {
                writer.write(r.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving rides to disk: " + e.getMessage());
        }
    }
}

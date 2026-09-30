import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RideService service = new RideService();

        while (true) {

            System.out.println("\n==================================================");
            System.out.println("       RIDEPULSE URBAN MOBILITY DISPATCH HUB      ");
            System.out.println("==================================================");
            System.out.println("--- PASSENGER ACTIONS ---");
            System.out.println("1. Request New Ride (Select Strategy: Standard / Surge / Carpool)");
            System.out.println("2. Track My Ride (Safe lookup via Optional)");
            System.out.println("3. Cancel Ride (Only allowed before pickup!)");
            System.out.println("\n--- DRIVER & DISPATCHER ACTIONS ---");
            System.out.println("4. View Active Rides On Road (Filtered via Streams)");
            System.out.println("5. Assign Driver to Ride");
            System.out.println("6. Start Trip (Pick up passenger)");
            System.out.println("7. Complete Trip (Drop off passenger)");
            System.out.println("\n--- MANAGEMENT & REVENUE ---");
            System.out.println("8. View Total Completed Revenue (Stream Sum)");
            System.out.println("9. View All Historic Rides");
            System.out.println("\n0. Exit Dispatch Hub");
            System.out.print("Please enter your choice (0-9): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Enter passenger name: ");
                    String passenger = scanner.nextLine().trim();

                    System.out.print("Enter pickup location: ");
                    String pickup = scanner.nextLine().trim();

                    System.out.print("Enter dropoff destination: ");
                    String dropoff = scanner.nextLine().trim();

                    System.out.println("Enter estimated distance in km (e.g. 12.5): ");
                    double distance;
                    try {
                        distance = Double.parseDouble(scanner.nextLine().trim());
                        if (distance <= 0) {
                            System.out.println("Distance must be greater than 0!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid distance number!");
                        break;
                    }

                    System.out.println("\nChoose Pricing Strategy:");
                    System.out.println("1. Standard Fare ($3.50 base + $1.75/km)");
                    System.out.println("2. Surge Pricing ($5.00 base + $2.75/km - High Demand)");
                    System.out.println("3. Carpool Discount ($2.00 base + $1.10/km - Shared)");
                    System.out.print("Select strategy (1-3): ");
                    String stratChoice = scanner.nextLine().trim();

                    FareStrategy strategy;
                    if (stratChoice.equals("2")) {
                        strategy = new SurgeFare();
                    } else if (stratChoice.equals("3")) {
                        strategy = new CarpoolFare();
                    } else {
                        strategy = new StandardFare();
                    }

                    Ride newRide = service.requestRide(passenger, pickup, dropoff, distance, strategy);
                    System.out.println("\n✅ Ride requested successfully!");
                    System.out.println("Your Ride ID: " + newRide.getRideId());
                    System.out.printf("Calculated Fare: $%.2f (%s)\n", newRide.getFare(), strategy.getStrategyName());
                    break;

                case "2":
                    System.out.println("Enter Ride ID to track (e.g., RIDE-101): ");
                    String trackId = scanner.nextLine().trim().toUpperCase();

                    Optional<Ride> optRide = service.findRide(trackId);
                    if (optRide.isPresent()) {
                        System.out.println("\n🔎 Ride Details:");
                        System.out.println(optRide.get());
                    } else {
                        System.out.println("❌ No ride found with ID: " + trackId);
                    }
                    break;

                case "3":
                    System.out.println("Enter Ride ID to cancel: ");
                    String cancelId = scanner.nextLine().trim().toUpperCase();

                    boolean cancelled = service.cancelTrip(cancelId);
                    if (cancelled) {
                        System.out.println("✅ Ride " + cancelId + " was successfully cancelled.");
                    } else {
                        System.out.println("❌ Cancellation rejected: Ride not found, or trip is already IN_PROGRESS / COMPLETED!");
                    }
                    break;

                case "4":
                    System.out.println("\n🚗 --- ACTIVE RIDES ON THE ROAD ---");
                    List<Ride> activeRides = service.getActiveRides();
                    if (activeRides.isEmpty()) {
                        System.out.println("No active rides at this moment.");
                    } else {
                        activeRides.forEach(System.out::println);
                    }
                    break;

                case "5":
                    System.out.print("Enter Ride ID to assign driver: ");
                    String assignId = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Enter Driver Name: ");
                    String driver = scanner.nextLine().trim();

                    if (service.assignDriver(assignId, driver)) {
                        System.out.println("✅ Driver " + driver + " assigned to " + assignId + "!");
                    } else {
                        System.out.println("❌ Assignment failed: Ride not found or status is not REQUESTED.");
                    }
                    break;

                case "6":
                    System.out.print("Enter Ride ID to start: ");
                    String startId = scanner.nextLine().trim().toUpperCase();

                    if (service.startTrip(startId)) {
                        System.out.println("✅ Trip started! Ride " + startId + " is now IN_PROGRESS.");
                    } else {
                        System.out.println("❌ Cannot start trip: Ride must have a driver assigned first!");
                    }
                    break;

                case "7":
                    System.out.print("Enter Ride ID to complete: ");
                    String compId = scanner.nextLine().trim().toUpperCase();

                    if (service.completeTrip(compId)) {
                        System.out.println("✅ Trip completed! Passenger dropped off. Payment processed.");
                    } else {
                        System.out.println("❌ Cannot complete trip: Ride must be IN_PROGRESS first!");
                    }
                    break;

                case "8":
                    double revenue = service.calculateCompletedRevenue();
                    System.out.println("\n📊 --- FINANCIAL REVENUE DASHBOARD ---");
                    System.out.printf("Total Completed Rides Revenue: $%.2f\n", revenue);
                    break;

                case "9":
                    System.out.println("\n📋 --- ALL HISTORIC RIDES ---");
                    service.getAllRides().forEach(System.out::println);
                    break;

                case "0":
                    System.out.println("Exiting RidePulse Dispatch Hub. All records safely saved to rides.txt.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid selection. Please choose 0 to 9.");

            }
        }
    }
}

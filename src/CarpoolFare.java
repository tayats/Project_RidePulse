public class CarpoolFare implements FareStrategy {

    @Override
    public double calculateFare(double distanceKm) {
        return 2.00 + (distanceKm * 1.1);
    }
    @Override
    public String getStrategyName() {
        return "Carpool Fare";
    }
}

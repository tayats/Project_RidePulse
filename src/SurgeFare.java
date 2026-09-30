public class SurgeFare implements FareStrategy {

    @Override
    public double calculateFare(double distanceKm) {
        return 5.00 + (distanceKm * 2.75);
    }
    @Override
    public String getStrategyName() {
        return "Surge Fare";
    }
}

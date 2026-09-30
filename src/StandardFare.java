public class StandardFare implements FareStrategy{

    @Override
    public double calculateFare(double distanceKm) {
        return 3.5 + (distanceKm * 1.75);
    }

    @Override
    public String getStrategyName() {
        return "Standard Fare";
    }
}

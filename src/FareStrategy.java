public interface FareStrategy {

    double calculateFare(double distanceKm);
    String getStrategyName();
}

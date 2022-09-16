public class RecommendedFilm {
    private Film film;
    private double predictedRate;

    public RecommendedFilm(Film film, double predictedRate) {
        this.film = film;
        this.predictedRate = predictedRate;
    }

    @Override
    public String toString() {
        if (this.film == null) {
            return "Hadn't found recommended film";

        }
        return String.format("Recommended film:\n%sPredicted Rate: %f\n", this.film, this.predictedRate);
    }
}

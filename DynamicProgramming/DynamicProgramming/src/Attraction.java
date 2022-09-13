public class Attraction {
    private String name;
    private int score;
    private double day;

    public Attraction(String name, int score, double day) {
        this.name = name;
        this.score = score;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }
}

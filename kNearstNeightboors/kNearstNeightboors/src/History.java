import java.util.HashMap;
import java.util.Map;

public class History {
    private Map<User, Map<Film, Double>> history = new HashMap<>();

    public void add(User user, Film film, double rate) {
        if (!history.containsKey(user)) {
            history.put(user, new HashMap<Film, Double>());
        }

        history.get(user).put(film, rate);
        recalculateFilmRate(film);
    }

    public void add(User user, Film film) {
        if (!history.containsKey(user)) {
            history.put(user, new HashMap<Film, Double>());
        }

        history.get(user).put(film, 3.0);
        recalculateFilmRate(film);
    }

    public Map<Film, Double> get(User user) {
        if (history.containsKey(user)) {
            return history.get(user);
        }

        return null;
    }

    private void recalculateFilmRate(Film film) {
        double newFilmRate = 0;
        int countUserWatchFilm = 0;
        for (User user : history.keySet()) {
            Map<Film, Double> userHistory = history.get(user);
            if (userHistory.containsKey(film)) {
                newFilmRate += userHistory.get(film);
                countUserWatchFilm++;
            }
        }
        newFilmRate /= countUserWatchFilm;

        film.setRate(newFilmRate);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("History!\n");
        for (User user : this.history.keySet()) {
            stringBuilder.append(String.format("User %s watch and rate:\n", user.getName()));
            for (Film film : this.history.get(user).keySet()) {
                stringBuilder.append(String.format("%s; Rate user: %f\n", film.getName(),
                        this.history.get(user).get(film)));
            }
            stringBuilder.append("----------------------------------------\n");
        }

        return stringBuilder.toString();
    }

}

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private Map<Category, Integer> evaluateCategory;

    public User(String name, Map<Category, Integer> evaluateCategory) {
        this.name = name;
        this.evaluateCategory = evaluateCategory;
    }

    public void watchFilm(Film film, double rate, History history) {
        history.add(this, film, rate);
    }

    public void watchFilm(Film film, History history) {
        history.add(this, film);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s\n", this.name));
        for (Category category : evaluateCategory.keySet()) {
            stringBuilder.append(String.format("%s: %d\n", category, evaluateCategory.get(category)));
        }
        stringBuilder.append("----------------------------------------\n");
        return stringBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Category, Integer> getEvaluateCategory() {
        return evaluateCategory;
    }

    public void setEvaluateCategory(Map<Category, Integer> evaluateCategory) {
        this.evaluateCategory = evaluateCategory;
    }
}

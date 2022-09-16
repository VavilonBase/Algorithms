public class Film {
    private String name;
    private Category category;
    private double rate;

    public Film(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Name: %s; Category: %s; Rate: %s;\n", this.name, this.category.getName(), this.rate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

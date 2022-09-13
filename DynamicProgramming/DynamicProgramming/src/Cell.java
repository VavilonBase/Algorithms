import java.util.ArrayList;
import java.util.List;

public class Cell<E> {
    private List<E> items = new ArrayList<>();

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }
}

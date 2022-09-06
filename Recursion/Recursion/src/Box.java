public class Box {
    private String content;
    private Box[] innerBoxes;

    public Box(String content) {
        this.content = content;
        this.innerBoxes = null;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Box[] getInnerBoxes() {
        return innerBoxes;
    }

    public void setInnerBoxes(Box[] innerBoxes) {
        this.innerBoxes = innerBoxes;
    }
}

public class DequePerson {
    private DequePersonItem startPerson;
    private DequePersonItem endPerson;
    private int length = 0;

    public void put(Person person) {
        DequePersonItem newDequePersonItem = new DequePersonItem(person);
        if (this.startPerson == null) {
            this.startPerson = newDequePersonItem;
            this.endPerson = newDequePersonItem;
        } else {
            this.endPerson.setNextPerson(newDequePersonItem);
            this.endPerson = newDequePersonItem;
        }

        length++;
    }

    public Person pop() {
        DequePersonItem popDequePersonItem = this.startPerson;
        this.startPerson = this.startPerson.getNextPerson();
        length--;
        if (length == 0) this.endPerson = null;
        return popDequePersonItem.getPerson();
    }

    public DequePersonItem getStartPerson() {
        return startPerson;
    }

    public void setStartPerson(DequePersonItem startPerson) {
        this.startPerson = startPerson;
    }

    public DequePersonItem getEndPerson() {
        return endPerson;
    }

    public void setEndPerson(DequePersonItem endPerson) {
        this.endPerson = endPerson;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

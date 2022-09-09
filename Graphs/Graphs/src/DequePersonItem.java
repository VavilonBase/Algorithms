public class DequePersonItem {
    private Person person;
    private DequePersonItem nextPerson;

    public DequePersonItem(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public DequePersonItem getNextPerson() {
        return nextPerson;
    }

    public void setNextPerson(DequePersonItem nextPerson) {
        this.nextPerson = nextPerson;
    }
}

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Person artem = new Person("Artem");
        Person ivan = new Person("Ivan");
        Person danil = new Person("Danil");
        Person katya = new Person("Katya");

        SellerMango nikolay = new SellerMango("Nikolay");
        SellerMango nikita = new SellerMango("Nikita");
        SellerMango peter = new SellerMango("Peter");

        Map<String, Person> graph = new HashMap<>();
        /*       artem
        *      /   |    \
        *  ivan - nikolay danil
        *    /            / /    \
        *   /    /-------/ /
        * nikita       peter  katya
        *
        *
        *
        * */
        artem.setFriends(new ArrayList<>(List.of(ivan, nikolay, danil)));
        ivan.setFriends(new ArrayList<>(List.of(artem, nikita, nikolay)));
        danil.setFriends(new ArrayList<>(List.of(artem, nikita, peter, katya)));
        peter.setFriends(new ArrayList<>(List.of(danil)));
        katya.setFriends(new ArrayList<>(List.of(danil)));
        nikita.setFriends(new ArrayList<>(List.of(ivan, danil)));
        nikolay.setFriends(new ArrayList<>(List.of(artem, ivan)));

        graph.put("Artem", artem);
        graph.put("Ivan", ivan);
        graph.put("Danil", danil);
        graph.put("Nikolay", nikolay);
        graph.put("Peter", peter);
        graph.put("Katya", katya);

        SellerMango findSellerMango = Main.searchInWidth(graph, "Artem");

        if (findSellerMango == null) {
            System.out.println("Not found seller mango");
        } else {
            System.out.printf("First seller mango friend: %s", findSellerMango.getName());
        }
    }

    public static SellerMango searchInWidth(Map<String, Person> graph, String name) {
//        Map<String, Person> checkedPerson = new HashMap<>();
        Set<Person> checkedPerson = new HashSet<>();
        DequePerson deque = new DequePerson();
        Person firstPerson = graph.get(name);
        if (firstPerson == null) return null;

        deque.put(firstPerson);

        while (deque.getLength() != 0) {
            Person currentPerson = deque.pop();
//            if (checkedPerson.contains(currentPerson.getName())) continue;
            if (checkedPerson.contains(currentPerson)) continue;

            if (currentPerson instanceof SellerMango) {
                return (SellerMango) currentPerson;
            }
            List<Person> currentPersonFriends = currentPerson.getFriends();
            for (int i = 0; i < currentPersonFriends.size(); i++) {
                deque.put(currentPersonFriends.get(i));
            }
//            checkedPerson.put(currentPerson.getName(), currentPerson);
            checkedPerson.add(currentPerson);
        }

        return null;
    }
}
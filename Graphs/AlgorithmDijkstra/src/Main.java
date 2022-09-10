import java.util.*;

public class Main {
    private static String START = "start";
    private static String A = "a";
    private static String B = "b";

    private static String END = "end";
    /*private static Map<String, Map<String, Integer>> graph = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, Map.ofEntries(
                    Map.entry(Main.A, 6),
                    Map.entry(Main.B, 2)
            )),
            Map.entry(Main.A, Map.ofEntries(
                    Map.entry(Main.END, 1)
            )),
            Map.entry(Main.B, Map.ofEntries(
                    Map.entry(Main.A, 3),
                    Map.entry(Main.END, 5)
            )),
            Map.entry(Main.END, new HashMap<>())
    ));

    private static Map<String, Integer> costs = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, 0),
            Map.entry(Main.A, 6),
            Map.entry(Main.B, 2),
            Map.entry(Main.END, Integer.MAX_VALUE)
    ));

    private static Map<String,String> parents = new HashMap<>(Map.ofEntries(
            Map.entry(Main.A, Main.START),
            Map.entry(Main.B, Main.START),
            Map.entry(Main.END, "")
    ));*/

    private static String C = "c";

    private static String D = "d";

    private static Map<String, Map<String, Integer>> graph = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, Map.ofEntries(
                    Map.entry(Main.A, 5),
                    Map.entry(Main.B, 2)
            )),
            Map.entry(Main.A, Map.ofEntries(
                    Map.entry(Main.C, 4),
                    Map.entry(Main.D, 2)
            )),
            Map.entry(Main.B, Map.ofEntries(
                    Map.entry(Main.A, 8),
                    Map.entry(Main.D, 7)
            )),
            Map.entry(Main.C, Map.ofEntries(
                    Map.entry(Main.END, 2),
                    Map.entry(Main.D, 6)
            )),
            Map.entry(Main.D, Map.ofEntries(
                    Map.entry(Main.END, 1)
            )),
            Map.entry(Main.END, new HashMap<>())
    ));

    private static Map<String, Integer> costs = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, 0),
            Map.entry(Main.A, 5),
            Map.entry(Main.B, 2),
            Map.entry(Main.C, Integer.MAX_VALUE),
            Map.entry(Main.D, Integer.MAX_VALUE),
            Map.entry(Main.END, Integer.MAX_VALUE)
    ));

    private static Map<String,String> parents = new HashMap<>(Map.ofEntries(
            Map.entry(Main.A, Main.START),
            Map.entry(Main.B, Main.START),
            Map.entry(Main.C, ""),
            Map.entry(Main.D, ""),
            Map.entry(Main.END, "")
    ));

    /*private static String C = "c";

    private static Map<String, Map<String, Integer>> graph = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, Map.ofEntries(
                    Map.entry(Main.A, 10)
            )),
            Map.entry(Main.A, Map.ofEntries(
                    Map.entry(Main.B, 20)
            )),
            Map.entry(Main.B, Map.ofEntries(
                    Map.entry(Main.END, 30),
                    Map.entry(Main.C, 1)
            )),
            Map.entry(Main.C, Map.ofEntries(
                    Map.entry(Main.A, 1)
            )),
            Map.entry(Main.END, new HashMap<>())
    ));

    private static Map<String, Integer> costs = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, 0),
            Map.entry(Main.A, Integer.MAX_VALUE),
            Map.entry(Main.B, Integer.MAX_VALUE),
            Map.entry(Main.C, Integer.MAX_VALUE),
            Map.entry(Main.END, Integer.MAX_VALUE)
    ));
*/

    /*private static String C = "c";

    private static Map<String, Map<String, Integer>> graph = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, Map.ofEntries(
                    Map.entry(Main.A, 2),
                    Map.entry(Main.B, 2)
            )),
            Map.entry(Main.A, Map.ofEntries(
                    Map.entry(Main.B, 2)
            )),
            Map.entry(Main.B, Map.ofEntries(
                    Map.entry(Main.END, 2),
                    Map.entry(Main.C, 2)
            )),
            Map.entry(Main.C, Map.ofEntries(
                    Map.entry(Main.A, -1),
                    Map.entry(Main.END, 2)
            )),
            Map.entry(Main.END, new HashMap<>())
    ));

    private static Map<String, Integer> costs = new HashMap<>(Map.ofEntries(
            Map.entry(Main.START, 0),
            Map.entry(Main.A, Integer.MAX_VALUE),
            Map.entry(Main.B, Integer.MAX_VALUE),
            Map.entry(Main.C, Integer.MAX_VALUE),
            Map.entry(Main.END, Integer.MAX_VALUE)
    ));

    private static Map<String,String> parents = new HashMap<>(Map.ofEntries(
            Map.entry(Main.A, ""),
            Map.entry(Main.B, ""),
            Map.entry(Main.C, ""),
            Map.entry(Main.END, "")
    ));*/

    private static Set<String> processed = new HashSet<>(8);

    public static void main(String[] args) {
        String lowestCostNode = findLowestCost();
        while (lowestCostNode != null) {
            Map<String, Integer> childesNode = Main.graph.get(lowestCostNode);
            Integer nodeCost = Main.costs.get(lowestCostNode);
            for (String child : childesNode.keySet()) {
                Integer toChildDirection = Main.graph.get(lowestCostNode).get(child) + nodeCost;
                if (toChildDirection < Main.costs.get(child)) {
                    Main.costs.put(child, toChildDirection);
                    Main.parents.put(child, lowestCostNode);
                }
            }

            processed.add(lowestCostNode);

            lowestCostNode = findLowestCost();
        }

        System.out.printf("Lowest find cost: %d\n", Main.costs.get(Main.END));
        System.out.println("Lowest cost way");

        List<String> way = new ArrayList<>(List.of(Main.END));

        String node = Main.parents.get(Main.END);
        while (!node.equals(Main.START)) {
            way.add(node);
            node = Main.parents.get(node);
        }

        way.add(Main.START);
        for (int i = way.size() - 1; i > 0; i--) {
            System.out.printf("%s->", way.get(i));
        }

        System.out.print(way.get(0));
    }

    private static String findLowestCost() {
        Integer lowestCost = Integer.MAX_VALUE;
        String lowestCostNode = null;
        for (String node : Main.graph.keySet()) {
            Integer cost = Main.costs.get(node);
            if (cost < lowestCost && !Main.processed.contains(node)) {
                lowestCost = cost;
                lowestCostNode = node;
            }
        }

        return lowestCostNode;
    }
}
import java.util.*;

public class Main {

    private static Map<String, Set<String>> stations = new HashMap<>(Map.ofEntries(
            Map.entry("kone", new HashSet<>(List.of(
                    "id", "nv", "ut"
            ))),
            Map.entry("ktwo", new HashSet<>(List.of(
                    "wa", "id", "mt"
            ))),
            Map.entry("kthree", new HashSet<>(List.of(
                    "or", "nv", "ca"
            ))),
            Map.entry("kfour", new HashSet<>(List.of(
                    "nv", "ut"
            ))),
            Map.entry("kfive", new HashSet<>(List.of(
                    "ca", "az"
            )))
    ));
    public static void main(String[] args) {
        Set<String> setNeeded = new HashSet<>(List.of("id", "nv", "ut", "ca", "az", "or", "mt"));
        List<String> bestStations = new ArrayList<>();

        while(setNeeded.size() != 0) {
            String bestStation = null;
            Set<String> covered = new HashSet<>();
            for (var station : Main.stations.entrySet()) {
                Set<String> intersectionNeededAndStationCovered = new HashSet<>(station.getValue());
                intersectionNeededAndStationCovered.retainAll(setNeeded);
                if (covered.size() < intersectionNeededAndStationCovered.size()) {
                    bestStation = station.getKey();
                    covered = intersectionNeededAndStationCovered;
                }
            }
            setNeeded.removeAll(covered);
            bestStations.add(bestStation);
        }

        System.out.print("The best stations: ");
        for (String station : bestStations) {
            System.out.printf("%s ", station);
        }
        System.out.println();
    }
}
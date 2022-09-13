import java.util.*;

public class Main {
    private final static int COUNT_ITEMS = 5;
    private final static int MAX_WEIGHT = 4;
    private final static int ATTRACTION_COUNT = 5;
    private final static int COUNT_TIMES = 4;

    private final static double TIMES_STEP = .5;

    public static void main(String[] args) {
        Item recorder = new Item("Recorder", 3000, 4);
        Item notebook = new Item("Notebook", 2000, 3);
        Item guitar = new Item("Guitar", 1500, 1);
        Item iphone = new Item("IPhone", 2000, 1);
        Item mp3Player = new Item("MP3-player", 1000, 1);
        List<Item> listItems = new ArrayList<>(List.of(recorder, notebook, guitar, iphone, mp3Player));
        CellItem[][] table = new CellItem[Main.COUNT_ITEMS][Main.MAX_WEIGHT];

        // Initialize
        for (int i = 0; i < Main.COUNT_ITEMS; i++) {
            for (int j = 0; j < Main.MAX_WEIGHT; j++) {
                table[i][j] = new CellItem();
            }
        }

        // Find
        for (int i = 0; i < Main.COUNT_ITEMS; i++) {
            Item item = listItems.get(i);
            for (int j = 0; j < Main.MAX_WEIGHT; j++) {
                int diffWeight = j + 1 - item.getWeight();
                CellItem cell = table[i][j];
                if (diffWeight >= 0) {
                    if (i > 0) {
                        CellItem prevCell = table[i-1][j];
                        int max = prevCell.getMaxPrice();
                        int currentValue = item.getPrice();
                        CellItem diffCell = new CellItem();

                        if (diffWeight > 0) {
                            diffCell = table[i-1][diffWeight - 1];
                            currentValue += diffCell.getMaxPrice();
                        }

                        if (currentValue > max) {
                            List<Item> cellItems = cell.getItems();
                            cellItems.add(item);
                            cellItems.addAll(diffCell.getItems());
                            max = currentValue;
                        } else {
                            List<Item> cellItems = cell.getItems();
                            cellItems.addAll(prevCell.getItems());
                        }
                        cell.setMaxPrice(max);
                    } else {
                        cell.setMaxPrice(item.getPrice());
                        cell.getItems().add(item);
                    }
                } else { // diffWeight < 0
                    if (i > 0) {
                        CellItem prevCell = table[i-1][j];
                        cell.setMaxPrice(prevCell.getMaxPrice());
                        cell.getItems().addAll(prevCell.getItems());
                    } else {
                        cell.setMaxPrice(0);
                    }
                }
            }
        }

        CellItem lastCell = table[Main.COUNT_ITEMS - 1][Main.MAX_WEIGHT - 1];
        List<Item> lastItems = lastCell.getItems();

        System.out.printf("Max items price: %d\n", lastCell.getMaxPrice());

        for (Item item : lastItems) {
            System.out.println(item.getName());
        }

        Main.travelToAttractions();
    }

    public static void travelToAttractions() {
        Attraction westminster = new Attraction("Westminster", 7, .5);
        Attraction theaterGlobe = new Attraction("Theater Glob", 6, .5);
        Attraction nationalGallery = new Attraction("National Gallery", 9, 1);
        Attraction britishMuseum = new Attraction("British Museum", 9, 2);
        Attraction cathedralStPaul = new Attraction("St. Paul's Cathedral", 8, .5);
        CellAttraction[][] table = new CellAttraction[Main.ATTRACTION_COUNT][Main.COUNT_TIMES];
        Attraction[] attractions =  { westminster, theaterGlobe, nationalGallery, britishMuseum, cathedralStPaul };
        double[] times = new double[Main.COUNT_TIMES];

        // Initialize table
        for (int i = 0; i < Main.ATTRACTION_COUNT; i++) {
            for (int j = 0; j < Main.COUNT_TIMES; j++) {
                table[i][j] = new CellAttraction();
            }
        }

        // Initialize times array
        for (int i = 0; i < Main.COUNT_TIMES; i++) {
            times[i] = (i+1) * Main.TIMES_STEP;
        }

        // Find effective travel way
        for (int i = 0; i < Main.ATTRACTION_COUNT; i++) {
            for (int j = 0; j < Main.COUNT_TIMES; j++) {
                CellAttraction cellAttraction = table[i][j];
                CellAttraction prevAttraction;
                Attraction currentAttraction = attractions[i];
                double currentTime = times[j];
                if (i > 0 && j > 0) {
                    prevAttraction = table[i-1][j];
                    double spaceTime = currentTime - currentAttraction.getDay();
                    if (spaceTime >= 0) {
                        int indexSpaceTime = Main.findIndexByTime(times, spaceTime);
                        int newMaxScore;
                        if (indexSpaceTime != -1) {
                            newMaxScore = currentAttraction.getScore() + table[i-1][indexSpaceTime].getMaxScore();
                            if (newMaxScore > prevAttraction.getMaxScore()) {
                                cellAttraction.setMaxScore(newMaxScore);
                                cellAttraction.getItems().add(currentAttraction);
                                cellAttraction.getItems().addAll(table[i-1][indexSpaceTime].getItems());
                                continue;
                            }
                        }
                    }
                    cellAttraction.setMaxScore(prevAttraction.getMaxScore());
                    cellAttraction.getItems().addAll(prevAttraction.getItems());
                } else if (i == 0) {
                    if (currentAttraction.getDay() <= currentTime) {
                        cellAttraction.setMaxScore(currentAttraction.getScore());
                        cellAttraction.getItems().add(currentAttraction);
                    }
                } else { // i > 0 && j == 0
                    prevAttraction = table[i-1][j];
                    if ((currentAttraction.getDay() <= currentTime) &&
                            (currentAttraction.getScore() > prevAttraction.getMaxScore())) {
                        cellAttraction.setMaxScore(currentAttraction.getScore());
                        cellAttraction.getItems().add(currentAttraction);
                    } else {
                        cellAttraction.setMaxScore(prevAttraction.getMaxScore());
                        cellAttraction.getItems().addAll(prevAttraction.getItems());
                    }
                }
            }
        }


        System.out.print(" ".repeat(20) + "\t");
        for (int i = 0; i < times.length; i++) {
            String time = String.format("%f" + " ".repeat(20), times[i]);
            System.out.printf(time.substring(0, 20), times[i]);
        }
        System.out.println();

        for (int i = 0; i < Main.ATTRACTION_COUNT; i++) {
            String name = attractions[i].getName() + " ".repeat(20);
            System.out.printf(name.substring(0, 20) + "\t", attractions[i].getName());
            for (int j = 0; j < Main.COUNT_TIMES; j++) {
                String score = String.format("%d" + " ".repeat(20), table[i][j].getMaxScore());
                System.out.printf(score.substring(0, 20), table[i][j].getMaxScore());
            }
            System.out.println();
        }

    }

    public static int findIndexByTime(double[] times, double time) {
        for (int i = 0; i < times.length; i++) {
            if (times[i] == time) return i;
        }
        return -1;
    }
}
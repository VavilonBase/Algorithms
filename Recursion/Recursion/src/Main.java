import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) {
        Box mainBox = new Box("Main");
        Box box1 = new Box("Main->box1");
        Box box2 = new Box("Main->box2");
        Box[] mainInnerBoxes = { box1, box2 };
        mainBox.setInnerBoxes(mainInnerBoxes);

        Box box3 = new Box("Main->box1->box3");
        Box box4 = new Box("Main->box1->box4");
        Box[] box1InnerBoxes = { box3, box4 };
        box1.setInnerBoxes(box1InnerBoxes);

        Box box5 = new Box("key"); // Main->box2->box5
        Box[] box2InnerBoxes = { box5 };
        box2.setInnerBoxes(box2InnerBoxes);

        if (!recursionFindKeyInBox(mainBox)) {
            System.out.println("Key not found");
        }

        int n = 10;
        System.out.printf("Factorial of %d equals %d\n", n, Main.factorial(n));

        List<Integer> numbersList = new ArrayList<Integer>(List.of(1, 5 , 3, 7, 8));
        System.out.printf("Sum numbers list equals %d\n", Main.recursionSum(numbersList));

        System.out.printf("Count of list elements equals %d\n", Main.recursionCountListElements(numbersList));

        System.out.printf("Max number of list equals %d\n", Main.recursionFindMax(numbersList));

        List<Integer> sortedList = Main.qSort(numbersList);
        System.out.print("Sorted list elements by quick sort: ");

        for (Integer number : sortedList) {
            System.out.printf("%d\t", number);
        }
    }

    public static boolean recursionFindKeyInBox(Box box) {
        System.out.println(box.getContent());
        if (box.getContent().equals("key")) {
            System.out.println("Find key");
            return true;
        } else if (box.getInnerBoxes() == null){
            return false;
        } else {
            for (int i = 0; i < box.getInnerBoxes().length; i++) {
                 if (Main.recursionFindKeyInBox(box.getInnerBoxes()[i])) {
                     return true;
                 }
            }
            return false;
        }
    }

    public static int factorial(int n) {
        if (n < 0) return -1;
        if (n == 0) return 1;
        if (n == 1) return 1;

        return n * Main.factorial(n - 1);
    }

    public static Integer recursionSum(List<Integer> arr) {
        if (arr == null || arr.size() == 0) return null;

        if (arr.size() == 1) return arr.get(0);

        Integer number = arr.get(0);
        arr.remove(0);

        Integer returnNumber = number + Main.recursionSum(arr);

        arr.add(0, number);

        return returnNumber;
    }

    public static int recursionCountListElements(List<Integer> arr) {
        if (arr == null || arr.size() == 0) return 0;

        if (arr.size() == 1) return 1;

        Integer number = arr.get(0);
        arr.remove(0);

        int count = 1 + Main.recursionCountListElements(arr);

        arr.add(0, number);

        return count;
    }

    public static Integer recursionFindMax(List<Integer> arr) {
        if (arr == null || arr.size() == 0) return null;

        if (arr.size() == 1) return arr.get(0);

        Integer maxNumber = arr.get(0);
        arr.remove(0);
        Integer nextMaxNumber = recursionFindMax(arr);

        arr.add(0, maxNumber);

        return maxNumber > nextMaxNumber ? maxNumber : nextMaxNumber;
    }

    public static List<Integer> qSort(List<Integer> arr) {

        if (arr == null) return null;

        if (arr.size() == 0 || arr.size() == 1) return arr;

        int pivotIndex = Main.random.nextInt(arr.size());

        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            if (i == pivotIndex) continue;

            if (arr.get(i) < arr.get(pivotIndex)) {
                less.add(arr.get(i));
            } else {
                greater.add(arr.get(i));
            }
        }

        List<Integer> sortedList = new ArrayList<>(Main.qSort(less));
        sortedList.add(arr.get(pivotIndex));
        sortedList.addAll(Main.qSort(greater));

        return sortedList;
    }
}
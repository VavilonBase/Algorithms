import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<Integer>(Arrays.asList(3, 1, 2, 4, 7, 5, 6, 9, 10, 8));

        int[] sortedArr = Main.selectionSort(arr);

        if (sortedArr != null) {
            for (int i = 0; i < sortedArr.length; i++) {
                System.out.print(sortedArr[i] + "\t");
            }
        } else {
            System.out.println("List is empty");
        }
    }

    public static int[] selectionSort(List<Integer> arr) {
        if (arr.size() == 0) return null;
        int[] newArr = new int[arr.size()];

        int i = 0;

        while (arr.size() != 0) {
            int maxElemIndex = Main.findMaxArrayElem(arr);
            newArr[i] = arr.get(maxElemIndex);
            arr.remove(maxElemIndex);
            i++;
        }

        return newArr;
    }

    public static int findMaxArrayElem(List<Integer> arr) {
        int maxElem = arr.get(0);
        int maxELemIndex = 0;

        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) > maxElem) {
                maxElem = arr.get(i);
                maxELemIndex = i;
            }
        }

        return maxELemIndex;
    }
}
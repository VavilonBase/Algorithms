public class Main {
    private static final int MAX_ELEMENTS = 1024;
    public static void main(String[] args) {

        int[] sortedNumbers = new int[Main.MAX_ELEMENTS];
        for (int i = 0; i < Main.MAX_ELEMENTS; i++) {
            sortedNumbers[i] = i;
        }

        int findElement = 1023;

        int findIndex = Main.binarySearch(sortedNumbers, findElement);

        if (findIndex == -1) {
            System.out.printf("Number %d is not in the list", findElement);
        } else {
            System.out.printf("Find index: %d", findIndex);
        }
    }

    public static int binarySearch(int[] sortedNumbers, int findElement) {
        if (sortedNumbers == null || sortedNumbers.length == 0) {
            return -1;
        }

        int startPtr = 0;
        int endPtr = sortedNumbers.length - 1;
        int count = 0;

        while (startPtr < endPtr) {
            count++;
            int middlePtr = (startPtr + endPtr) / 2;

            if (sortedNumbers[middlePtr] == findElement) {
                System.out.printf("Number of operations to search for an element: %d, equals: %d\n", findElement, count);
                return middlePtr;
            } else if (sortedNumbers[middlePtr] > findElement) {
                endPtr = middlePtr - 1;
            } else {
                startPtr = middlePtr + 1;
            }
        }

        // if exit from while, then startPtr == endPtr
        if (sortedNumbers[startPtr] == findElement) {
            System.out.printf("Number of operations to search for an element: %d, equals: %d\n", findElement, count);
            return startPtr;
        }
        return -1;
    }
}
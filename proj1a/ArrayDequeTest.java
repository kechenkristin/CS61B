public class ArrayDequeTest {
    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");

        ArrayDeque<String> ad1 = new ArrayDeque<>();

        boolean passed = checkEmpty(true, ad1.isEmpty());

        ad1.addFirst("front");
        ad1.addFirst("second");
        ad1.addFirst("third");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(3, ad1.size()) && passed;
        passed = checkEmpty(false, ad1.isEmpty()) && passed;

        ad1.addLast("fourth");
        ad1.addLast("fifth");
        ad1.addLast("sixth");
        passed = checkSize(6, ad1.size()) && passed;


        System.out.println("Printing out deque: ");
        ad1.printDeque();

        System.out.println();
        printTestStatus(passed);

    }

    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");


        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        lld1.addFirst(11);
        lld1.addFirst(12);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);

    }

    public static void resizeTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        lld1.addFirst("A");
        lld1.addLast("B");
        lld1.addFirst("C");
        lld1.addLast("D");
        lld1.addFirst("E");
        lld1.addFirst("F");
        lld1.addFirst("G");
        lld1.addFirst("H");
        lld1.addFirst("I");
    }

    public static void resizeRemoveTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        lld1.addFirst("A");
        lld1.addFirst("C");
        lld1.addLast("H");
        lld1.addLast("G");
        lld1.addFirst("P");
        lld1.addLast("J");
        lld1.removeFirst();
        lld1.addLast("D");
        lld1.addFirst("E");
        lld1.removeLast();
        lld1.addFirst("Q");
        lld1.addLast("F");
        lld1.addLast("K");
        lld1.removeFirst();
        lld1.addFirst("H");
        lld1.addLast("I");
        lld1.addFirst("O");
        lld1.addFirst("P");
        lld1.removeLast();
        lld1.removeFirst();
        lld1.addLast("L");
    }


    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        resizeTest();
        resizeRemoveTest();
    }
}

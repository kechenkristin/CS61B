import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {

    /**
     * @source cs61b StudentArrayDequeLauncher.java
     */
    @Test
    public void TestStudentArrayDeque() {
        StudentArrayDeque<Integer> studentArray = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arraySolution = new ArrayDequeSolution<>();
        String log = "";

        for (int i = 0; i < 1000; i += 1) {
            if (studentArray.size() == 0) {
                int addNumber = StdRandom.uniform(1000);
                double numberBetweenZeroAndOne = StdRandom.uniform();

                if (numberBetweenZeroAndOne < 0.5) {
                    log += "addFirst(" + addNumber + ")\n";
                    studentArray.addFirst(addNumber);
                    arraySolution.addFirst(addNumber);
                } else {
                    log += "addLast(" + addNumber + ")\n";
                    studentArray.addLast(addNumber);
                    arraySolution.addLast(addNumber);
                }
            } else {
                int removeStudent = 1;
                int removeSolution = 1;
                int addNum = StdRandom.uniform(1000);
                int randomNum = StdRandom.uniform(4);

                switch (randomNum) {
                    case 0:
                        log += "addFirst(" + addNum + ")\n";
                        studentArray.addFirst(addNum);
                        arraySolution.addFirst(addNum);

                    case 1:
                        log += "addLast(" + addNum + ")\n";
                        studentArray.addLast(addNum);
                        arraySolution.addLast(addNum);

                    case 2:
                        log += "removeFirst()\n";
                        removeStudent = studentArray.removeFirst();
                        removeSolution = arraySolution.removeFirst();

                    case 3:
                        log += "removeLast()\n";
                        removeStudent = studentArray.removeLast();
                        removeSolution = studentArray.removeLast();

                    default:
                }
                assertEquals(log, removeStudent, removeSolution);
            }
        }

    }
}

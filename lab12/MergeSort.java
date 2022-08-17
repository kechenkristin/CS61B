import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order,
     * with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since
            // the queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> qoq = new Queue<>();
        for (Item item : items) {
            Queue<Item> innerQueue = new Queue<>();
            innerQueue.enqueue(item);
            qoq.enqueue(innerQueue);
        }
        return qoq;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> mergedQ = new Queue<>();
        while ((!q1.isEmpty()) || (!q2.isEmpty())) {
            mergedQ.enqueue(getMin(q1, q2));
        }
        return mergedQ;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        if (items.isEmpty()) {
            return items;
        } else {
            Queue<Queue<Item>> toSort = makeSingleItemQueues(items);
            return mergeSortHelper(toSort).dequeue();
        }
    }

    private static <Item extends Comparable> Queue<Queue<Item>>
    mergeSortHelper(Queue<Queue<Item>> toSort) {
        if (toSort.size() <= 1) {
            return toSort;
        } else {
            toSort.enqueue(mergeSortedQueues(toSort.dequeue(),
                    toSort.dequeue()));
            return mergeSortHelper(toSort);
        }
    }

    public static void main(String[] args) {
        Queue<Integer> nums = new Queue<>();
        nums.enqueue(32);
        nums.enqueue(15);
        nums.enqueue(2);
        nums.enqueue(17);
        nums.enqueue(19);
        nums.enqueue(24);
        nums.enqueue(41);
        nums.enqueue(17);
        nums.enqueue(17);
        System.out.println("before mergesort");
        System.out.println(nums);
        System.out.println("after mergesort");
        Queue<Integer> sortedNum = MergeSort.mergeSort(nums);
        System.out.println(sortedNum.toString());
        System.out.println();

        Queue<Integer> num = new Queue<>();
        num.enqueue(2);
        num.enqueue(4);
        num.enqueue(1);
        num.enqueue(6);
        System.out.println("before mergesort");
        System.out.println(num);
        System.out.println("after mergesort");
        Queue<Integer> sorted = MergeSort.mergeSort(num);
        System.out.println(sorted.toString());
    }
}

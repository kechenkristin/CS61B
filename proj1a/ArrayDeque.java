import java.util.Iterator;

public class ArrayDeque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;


    /**
     * Creates an empty linked array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last,
     * separated by a space.
     */
    public void printDeque() {
        for (T i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * Get the previous index. left
     */
    private int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    /**
     * Get the next index. right
     */
    private int plusOne(int index) {
        return Math.floorMod(index + 1, items.length);
    }

    /**
     * Plusone for resize.
     */
    private int plusOne(int index, int length) {
        return Math.floorMod(index + 1, length);
    }


    /**
     * get the first item.
     */
    private T getFirst() {
        return items[plusOne(nextFirst)];
    }

    /**
     * get the last item.
     */
    private T getLast() {
        return items[minusOne(nextLast)];
    }


    /**
     * Adds item of type T to the front of the list.
     */
    public void addFirst(T item) {
        resize();
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        resize();
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }


    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        resize();
        T removedItem = getFirst();
        nextFirst = plusOne(nextFirst);
        items[nextFirst] = null;
        size -= 1;
        return removedItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        resize();
        T removedItem = getLast();
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        size -= 1;
        return removedItem;
    }


    private void resize() {
        if (size == items.length) {
            expand();
        }
        if (size < items.length * 0.25 && items.length > 8) {
            reduce();
        }
    }

    private void expand() {
        resizeHelper(items.length * 2);
    }

    private void reduce() {
        resizeHelper(items.length / 2);
    }

    private void resizeHelper(int newLength) {
        T[] temp = items;
        int start = plusOne(nextFirst);
        int end = minusOne(nextLast);

        items = (T[]) new Object[newLength];
        nextFirst = 0;
        nextLast = 1;

        for (int i = start; i != end; i = plusOne(i, temp.length)) {
            items[nextLast] = temp[i];
            nextLast = plusOne(nextLast, newLength);
        }

        items[nextLast] = temp[end];
        nextLast = plusOne(nextLast, newLength);
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        }
        index = Math.floorMod(plusOne(nextFirst) + index, items.length);
        return items[index];
    }

}

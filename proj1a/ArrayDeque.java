import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Iterable<T> {

   private  T[] items;
   private int size;

   private int length;

   private int first;

   private int last;


   public Iterator<T> iterator() {
       List<T> itemList = List.of(items);
       return itemList.iterator();

   }

    /** Creates an empty linked array deque. */
    public ArrayDeque() {
        length = 8;
        items = (T[]) new Object[length];
        size = 0;
        first = 4;
        last = 5;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
       for(T i : items) {
           System.out.print(i + " ");
       }
    }


    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;

    }

    /** Adds item of type T to the front of the list. */
    public void addFirst(T item){
        if (size == length) {
            resize(length * 2);
        }
        items[first] = item;
        first = (first - 1) % length;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == length) {
            resize(length * 2);
        }
        items[last] = item;
        last = (last + 1) % length;
        size += 1;
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
       size -= 1;
       T returnItem = items[first];
       items[first] = null;
       first = (first + 1) % length;
       return returnItem;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        size -= 1;
        T returnItem = items[last];
        items[last] = null;
        last = (last - 1) % length;
        return returnItem;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque! */
    public T get(int index) {
        return items[index];
    }



}

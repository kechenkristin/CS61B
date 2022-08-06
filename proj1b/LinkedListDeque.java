
public class LinkedListDeque<T> implements Deque<T> {

    private static class TNode<T> {
        private T item;
        private TNode prev;
        private TNode next;

        public TNode() {
        }

        public TNode(T i, TNode p, TNode n) {
            item = i;
            prev = p;
            next = n;
        }

        /**
         * constructor for sential
         */
        public TNode(TNode p, TNode n) {
            prev = p;
            next = n;
        }

    }


    private TNode sentinel;
    private int size;


    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new TNode(null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    @Override
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
    @Override
    public void printDeque() {
        TNode ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    /**
     * Adds item of type T to the front of the list.
     */
    @Override
    public void addFirst(T item) {
        TNode newNode = new TNode(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    @Override
    public void addLast(T item) {
        TNode newNode = new TNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = (T) sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = (T) sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        TNode ptr = sentinel.next;
        for (int i = 0; i != index; i++) {
            ptr = ptr.next;
        }
        return (T) ptr.item;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else {
            return (T) getHelper(index, sentinel.next).item;
        }
    }

    private TNode getHelper(int index, TNode node) {
        if (index == 0) {
            return node;
        } else {
            return getHelper(index - 1, node.next);
        }
    }
}

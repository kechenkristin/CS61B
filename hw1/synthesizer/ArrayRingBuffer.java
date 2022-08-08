package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int nextFirst;
    /* Index for the next enqueue. */
    private int nextLast;
    /* Array for storing the buffer data. */
    private T[] rb;

    private int plusOne(int index) {
        return Math.floorMod(index - 1, rb.length);
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        nextFirst = 0;
        nextLast = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[nextLast] = x;
        nextLast = plusOne(nextLast);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T returnItem = rb[nextFirst];
        nextFirst = plusOne(nextFirst);
        fillCount -= 1;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[nextFirst];
    }

    @Override
    public Iterator<T> iterator() {
        return new TIterator();
    }

    private class TIterator implements Iterator<T> {
        private int wizardPosition = 0;

        public TIterator() {
            wizardPosition = nextFirst;
        }

        @Override
        public boolean hasNext() {
            return wizardPosition != fillCount;
        }

        @Override
        public T next() {
            T returnVal = rb[wizardPosition];
            wizardPosition = plusOne(wizardPosition);
            return returnVal;
        }
    }
}

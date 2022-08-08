package synthesizer;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayRingBufferTest {
    @Test
    public void deEnTest() {
        BoundedQueue<Integer> x = new ArrayRingBuffer<>(4);
        x.enqueue(1);
        x.enqueue(2);
        x.enqueue(3);
        x.enqueue(4);
        int actual1 = x.peek();
        assertEquals(actual1, 1);
        assertFalse(x.isEmpty());
        assertTrue(x.isFull());

        x.dequeue();
        x.dequeue();
        int act2 = x.dequeue();
        assertEquals(act2, 3);
        x.dequeue();
        assertTrue(x.isEmpty());
    }
}

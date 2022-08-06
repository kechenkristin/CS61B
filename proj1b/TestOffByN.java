import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void TestEqualChars() {
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));  // true
        assertFalse(offBy5.equalChars('f', 'h'));  // false

        OffByN offByN2 = new OffByN(2);
        assertTrue(offByN2.equalChars('a', 'c'));
        assertTrue(offByN2.equalChars('i', 'k'));
    }
}

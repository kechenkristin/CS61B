import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your
    CharacterComparator interface and OffByOne class. **/

    static OffByOne offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        boolean a1 = offByOne.equalChars('a', 'b');
        assertTrue(a1);
        assertTrue(offByOne.equalChars('r', 'q'));

        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));

        assertTrue(offByOne.equalChars('&', '%'));

    }

}

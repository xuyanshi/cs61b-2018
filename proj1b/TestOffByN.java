import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByN = new OffByN(5);

    // Your tests go here.
    @Test
    public void testEqualChars() {
        boolean actual1 = offByN.equalChars('a', 'f');
        boolean actual2 = offByN.equalChars('f', 'a');
        boolean actual3 = offByN.equalChars('h', 'f');
        assertTrue(actual1);
        assertTrue(actual2);
        assertFalse(actual3);
    }
}

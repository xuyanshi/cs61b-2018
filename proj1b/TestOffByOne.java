import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        // CharacterComparator offByOne = new OffByOne();
        boolean actual = offByOne.equalChars('a', 'b');
        assertTrue(actual);
    }
}

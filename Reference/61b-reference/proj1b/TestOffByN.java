import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    static OffByN offBy5 = new OffByN(5);

    @Test
    public void testEqualChars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('a', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
        assertFalse(offBy5.equalChars('r', 'q'));
        assertFalse(offBy5.equalChars('&', '%'));
    }
}

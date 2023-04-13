import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    /**
     * Performs a few arbitrary tests to see if the isSameNumber method is correct
     */

    @Test
    public void testisSameNumber() {
        /* assertEquals for comparison of ints takes two arguments:
        assertEquals(expected, actual).
        if it is false, then the assertion will be false, and this test will fail.
        */
        assertTrue("True12", Flik.isSameNumber(12, 12));
        assertTrue("True129", Flik.isSameNumber(129, 129));
    }


}

package creatures;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
    }
}

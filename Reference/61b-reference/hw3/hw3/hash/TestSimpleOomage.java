package hw3.hash;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 15, 15);
        SimpleOomage ooB = new SimpleOomage(0, 15, 20);
        SimpleOomage ooB2 = new SimpleOomage(0, 15, 20);
        SimpleOomage ooC = new SimpleOomage(0, 0, 90);
        SimpleOomage ooC2 = new SimpleOomage(0, 5, 85);
        SimpleOomage ooD = new SimpleOomage(0, 5, 0);
        SimpleOomage ooD2 = new SimpleOomage(5, 0, 0);
        assertNotEquals(ooA.hashCode(), ooA2.hashCode());
        assertNotEquals(ooA.hashCode(), ooB.hashCode());
        assertNotEquals(ooA2.hashCode(), ooB.hashCode());
        assertNotEquals(ooC2.hashCode(), ooC.hashCode());

        assertNotEquals(ooD2.hashCode(), ooD.hashCode());
        assertEquals(ooB.hashCode(), ooB2.hashCode());
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }


    /** Calls tests for SimpleOomage. */
//    public static void main(String[] args) {
//        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
//    }
}

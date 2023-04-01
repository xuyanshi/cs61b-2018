package hw3.hash;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /*
     * that shows the flaw in the hashCode function.
     *
     * c001) Test testWithDeadlyParams (built-in hash code). (0/5)
     * Test Failed!
     * Assertion failed
     * at hw3.hash.TestComplexOomage.testWithDeadlyParams:57 (TestComplexOomage.java)
     * Running testWithDeadlyParams with a better hashCode.
     * Your testWithDeadlyParams should pass.
     * Your testWithDeadlyParams method failed, even when we replaced ComplexOomage's hash code
     * with something better. Specifically, for this test, it simply uses params.hashCode(), i.e.
     * the default implementation of hashCode for lists. It's possible you have simply identified
     * a case where the built-in hashCode for Lists performs badly.
     * More likely, your test always fails
     * even if the hash code is good. If you feel good about your test, post to Piazza.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        int N = 100;
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> params = new ArrayList<>();
            int k = 1;
            for (int j = 0; j < 10; j++) {
                k *= 2;
                params.add((2 * i * k) % 256);
            }
            deadlyList.add(new ComplexOomage(params));
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}

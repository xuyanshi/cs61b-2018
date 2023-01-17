import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestArrayDequeGold {

    @Test
    public void TestRandom() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        for (int i = 0; i < 1000; i++) {
            int randomInt = StdRandom.uniform(100);
            double randomMethod = StdRandom.uniform(-2.0, 2.0);
            if (randomMethod < -1 && sad1.size() > 0) {
                Integer s1 = sad1.removeFirst();
                Integer a1 = ads1.removeFirst();
                assertEquals(s1, a1);
            } else if (randomMethod < 0 && sad1.size() > 0) {
                Integer s1 = sad1.removeLast();
                Integer a1 = ads1.removeLast();
                assertEquals(s1, a1);
            } else if (randomMethod < 1) {
                sad1.addLast(randomInt);
                ads1.addLast(randomInt);
            } else if (randomMethod < 2) {
                sad1.addFirst(randomInt);
                ads1.addFirst(randomInt);
            }
        }
    }

}
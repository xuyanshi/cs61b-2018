import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    private String errorMessage = "";

    @Test
    public void test() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        for (int i = 0; i < 400; i += 1) {
            double randomNumber = StdRandom.uniform();
            if (randomNumber < 0.5) {
                if (!sol.isEmpty() && !sad.isEmpty() && randomNumber < 0.25) {
                    Integer actual = sad.removeLast();
                    Integer expected = sol.removeLast();
                    String methodCall = "removeLast()\n";
                    errorMessage = errorMessage + methodCall;
                    assertEquals(errorMessage, expected, actual);
                } else {
                    sad.addLast(i);
                    sol.addLast(i);
                    String methodCall = "addLast(" + i + ")\n";
                    errorMessage = errorMessage + methodCall;
                }
            } else {
                if (!sol.isEmpty() && !sad.isEmpty() && randomNumber < 0.75) {
                    Integer actual = sad.removeFirst();
                    Integer expected = sol.removeFirst();
                    String methodCall = "removeFirst()\n";
                    errorMessage = errorMessage + methodCall;
                    assertEquals(errorMessage, expected, actual);
                } else {
                    sad.addFirst(i);
                    sol.addFirst(i);
                    String methodCall = "addFirst(" + i + ")\n";
                    errorMessage = errorMessage + methodCall;
                }
            }
        }
    }

}

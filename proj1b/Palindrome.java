public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            deq.addLast(ch);
        }
        return deq;
    }
}

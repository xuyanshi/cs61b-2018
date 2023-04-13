public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            deque.addLast(c);
        }
        return deque;
    }

    private boolean isPalindromeHelper(Deque<Character> deque) {
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        } else if (deque.removeFirst() == deque.removeLast()) {
            return isPalindromeHelper(deque);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        while (!deque.isEmpty() && deque.size() != 1) {
            if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}

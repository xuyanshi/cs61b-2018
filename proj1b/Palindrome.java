public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            deq.addLast(ch);
        }
        return deq;
    }

    public boolean isPalindrome(String word) {
        int n = word.length();
        if (n < 2) {
            return true;
        }
        for (int i = 0; i < n / 2; i++) {
            if (word.charAt(i) != word.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int n = word.length();
        if (n < 2) {
            return true;
        }
        for (int i = 0; i < n / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(n - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}

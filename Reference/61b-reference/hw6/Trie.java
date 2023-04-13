import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {

    private class Node {
        boolean isKey = false;
        HashMap<Character, Node> links = new HashMap<>();
    }

    private Node root = new Node();

    public void addWord(String word) {
        Node addTo = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!addTo.links.containsKey(c)) {
                addTo.links.put(c, new Node());
            }
            addTo = addTo.links.get(c);
        }
        addTo.isKey = true;
    }

    public List<String> keysWithPrefix(String s) {
        Node curr = root;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!curr.links.containsKey(c)) {
                return list;
            }
            curr = curr.links.get(c);
        }
        keysWithPrefix(curr, s, list);
        return list;
    }

    private void keysWithPrefix(Node node, String curr, List<String> list) {
        if (node.isKey) {
            list.add(curr);
        }
        for (char c : node.links.keySet()) {
            keysWithPrefix(node.links.get(c), curr + c, list);
        }
    }

    public boolean hasPrefix(String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!curr.links.containsKey(c)) {
                return false;
            }
            curr = curr.links.get(c);
        }
        return true;
    }

    public boolean contain(String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!curr.links.containsKey(c)) {
                return false;
            }
            curr = curr.links.get(c);
        }
        return curr.isKey;
    }

    public int containOrPrefix(String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!curr.links.containsKey(c)) {
                return -1;
            }
            curr = curr.links.get(c);
        }
        if (curr.isKey) {
            return 1;
        } else {
            return 0;
        }
    }
}

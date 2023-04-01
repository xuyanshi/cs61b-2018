package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author xuyanshi
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    private V deletedValue;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            return p;
        }

        if (p.key.compareTo(key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size++;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void keySetHelper(Set<K> keys, Node p) {
        if (p == null) {
            return;
        }
        keys.add(p.key);
        keySetHelper(keys, p.left);
        keySetHelper(keys, p.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, root);
        return keys;
    }


    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }

    private Node removeHelper(Node p, K key) {
        if (p == null) {
            return null;
        }
        
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(p.left, key);
        } else if (cmp > 0) {
            p.right = removeHelper(p.right, key);
        } else {
            deletedValue = p.value;

            if (p.left == null) {
                return p.right;
            }
            if (p.right == null) {
                return p.left;
            }

            // Two subtrees both exist.
            // I will find the minimum node from the right subtree to replace p.
            Node node = p;
            p = min(node.right);
            p.right = deleteMin(node.right);
            p.left = node.left;
        }
        return p;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with a null key");
        }
        root = removeHelper(root, key);
        V returnValue = deletedValue;
        if (returnValue != null) {
            size--;
        }
        deletedValue = null;
        return returnValue;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
    }
}

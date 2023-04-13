package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int INIT_CAPACITY = 16;
    private int n;                                // number of key-value pairs
    private int m;                                // hash table size
    private double loadFactor = 0.75;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(INIT_CAPACITY);
    }

    public MyHashMap(int initialSize) {
        this.m = initialSize;
        this.buckets = createTable(m);
        this.n = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this(initialSize);
        this.loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        this.n = 0;
        this.buckets = createTable(m);
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        int i = hash(key);
        Collection<Node> bucket = buckets[i];
        for (Node b : bucket) {
            if (b.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int i = hash(key);
        Collection<Node> bucket = buckets[i];
        for (Node b : bucket) {
            if (b.key.equals(key)) {
                return b.value;
            }
        }
        return null;
    }

    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
    private int hash(K key) {
        int h = key.hashCode();
        return Math.floorMod(h, m);
    }

    private int hash(K key, int length) {
        int h = key.hashCode();
        return Math.floorMod(h, length);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.n;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            n++;
            put(key, value, buckets);
        } else {
            int i = hash(key);
            Collection<Node> bucket = buckets[i];
            for (Node b : bucket) {
                if (b.key.equals(key)) {
                    b.value = value;
                }
            }
        }
        if (this.n >= this.loadFactor * this.m) {
            resize(2 * m);
        }
    }

    private void put(K key, V value, Collection<Node>[] table) {
        int i = hash(key, table.length);
        Node node = createNode(key, value);
        table[i].add(node);
    }

    // resize the hash table to have the given number of chains,
    // rehashing all the keys
    private void resize(int chains) {
        Collection<Node>[] temp = createTable(chains);
        Set<K> set = keySet();
        for (K key : set) {
            put(key, get(key), temp);
        }
        this.m = chains;
        this.buckets = temp;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node b : bucket) {
                set.add(b.key);
            }
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            int i = hash(key);
            Collection<Node> bucket = buckets[i];
            for (Node b : bucket) {
                if (b.key.equals(key)) {
                    V value = b.value;
                    bucket.remove(b);
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}

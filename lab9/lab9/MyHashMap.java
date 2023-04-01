package lab9;

import org.hamcrest.Condition;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * @author xuyanshi
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int initialSize) {
        buckets = new ArrayMap[initialSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int keyHash = hash(key);
        return buckets[keyHash % buckets.length].get(key);
    }

    private void resize() {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[buckets.length * 2];
        for (int i = 0; i < buckets.length * 2; i++) {
            newBuckets[i] = new ArrayMap<>();
        }
        for (K key : keySet()) {
            int keyHash = hash(key);
            newBuckets[Math.floorMod(key.hashCode(), 2 * buckets.length)].put(key, buckets[keyHash].get(key));
        }
        buckets = newBuckets;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize();
        }

        int keyHash = hash(key);
        if (!buckets[keyHash % buckets.length].containsKey(key)) {
            size++;
        }
        buckets[keyHash % buckets.length].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            Set<K> keySetBucket = buckets[i].keySet();
            keys.addAll(keySetBucket);
        }
        return keys;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int keyHash = hash(key);
        if (buckets[keyHash % buckets.length].containsKey(key)) {
            size--;
        }
        return buckets[keyHash % buckets.length].remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int keyHash = hash(key);
        if (buckets[keyHash % buckets.length].get(key) == value) {
            size--;
        }
        return buckets[keyHash % buckets.length].remove(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        MyHashMap<String, String> a = new MyHashMap<String, String>(DEFAULT_SIZE);
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            b.put("hi" + i, i);
        }
        for (String s : b.keySet()) {
            System.out.print(s + ' ');
            System.out.println(b.get(s));
        }

    }
}

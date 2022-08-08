package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
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
    private V getHelper(K key, Node T) {
        if (T == null) {
            return null;
        }
        if (key.equals(T.key)) {
            return T.value;
        } else if (key.compareTo(T.key) < 0) {
            return getHelper(key, T.left);
        } else {
            return getHelper(key, T.right);
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
    private Node putHelper(Node T, K key, V value) {
        if (T == null) {
            return new Node(key, value);
        }
        if (key.compareTo(T.key) < 0) {
            T.left = putHelper(T.left, key, value);
        } else {
            T.right = putHelper(T.right, key, value);
        }
        return T;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }


    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void traverseAdd(Set<K> keys, Node T) {
        if (T == null) {
            return;
        }
        keys.add(T.key);
        traverseAdd(keys, T.left);
        traverseAdd(keys, T.right);
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        traverseAdd(keys, root);
        return keys;
    }

    private Node findMin(Node T) {
        if (T.left == null) {
            return T;
        }
        return findMin(T.left);
    }


    private Node removeHelper(K key, Node T) {
        int cmp = key.compareTo(T.key);
        if (cmp > 0) {
            T.right = removeHelper(key, T.right);
        } else if (cmp < 0) {
            T.left = removeHelper(key, T.left);
        } else {
            if (T.left == null && T.right == null) {
                T = null;
            }
            if (T.left == null) {
                return T.right;
            }
            if (T.right == null) {
                return T.left;
            }
            Node rightMin = findMin(T.right);
            T.right = removeHelper(rightMin.key, T.right);
        }
        return T;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V returnVal = get(key);
        if (returnVal == null) {
            return null;
        }
        root = removeHelper(key, root);
        size -= 1;
        return returnVal;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

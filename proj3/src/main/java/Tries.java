import java.util.*;

public class Tries<Value> {
    private static final int R = 256;   // extended ASCII

    private Node root;  // root of trie
    private int n;  // number of keys in trie

    private static class Node {
        private Object val;
        private HashMap<Character, Node> next;

        // default constructor
        public Node() {
            next = new HashMap<>();
        }
    }

    /**
     * Initializes an empty tries
     */
    public Tries() {
    }

    /**
     * Returns the value associated with the given key.
     */
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node node, String key, int index) {
        if (node == null) return null;
        if (index == key.length()) return node;
        char c = key.charAt(index);
        return get(node.next.get(c), key, index + 1);
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("key is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node node, String key, Value val, int index) {
        if (node == null) node = new Node();
        if (index == key.length()) {
            if (node.val == null) n++;
            node.val = val;
            return node;
        }
        char c = key.charAt(index);
        node.next.put(c,  put(node.next.get(c), key, val, index + 1));
        return node;
    }

    /**
     * collecting all the keys
     */
    public List<String> collect() {
        List<String> ret = new ArrayList<>();
        colHelp("", ret, root);
        return ret;
    }

    public List<String> keysWithPrefix(String prefix) {
        List<String> ret = new ArrayList<>();
        Node startNode = get(root, prefix, 0);
        colHelp(prefix, ret, startNode);
        return ret;
    }

    private void colHelp(String s, List<String> queue, Node node) {
        if (node == null) return;
        if (node.val != null) queue.add(s);
        for (Map.Entry<Character, Node> mapElement : node.next.entrySet()) {
            colHelp(s + mapElement.getKey(), queue, mapElement.getValue());
        }

    }


    public static void main(String[] args) {
        Tries Trie = new Tries();
        Trie.put("Same", null);
        Trie.put("S' am", 1);
        Trie.put("sap",2);
        Trie.put("sad",3);
        Trie.put("a",null);
        Trie.put("awls",5);
        // LinkedList<String> ret = Trie.StringListwithPrefix("sa");
        // System.out.println(ret.get(0));
        List<String> keys = Trie.collect();
        System.out.println(keys);

        List<String> preficx = Trie.keysWithPrefix("sa");
        System.out.println(preficx);
    }

}

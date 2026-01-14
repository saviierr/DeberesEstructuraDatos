package Logistica;

public class InventoryMap<K, V> {

    private Entry<K, V>[] table;
    private int capacity;

    @SuppressWarnings("unchecked")
    public InventoryMap(int capacity) {
        this.capacity = capacity;
        table = new Entry[capacity];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // O(1) promedio
    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> head = table[index];

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
    }

    // O(1) promedio
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> head = table[index];

        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }
        return null;
    }
}

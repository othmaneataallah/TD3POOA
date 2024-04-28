import java.util.NoSuchElementException;

import javax.management.openmbean.KeyAlreadyExistsException;

public class MapChain<K, V> implements IMap<K, V> {

    private class Entry<Key, Value> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private Entry<K, V> head;
    private int size;

    public MapChain() {
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        for (Entry<K, V> current = head; current != null; current = current.next) {
            if (current.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V search(K key) {
        for (Entry<K, V> current = head; current != null; current = current.next) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void add(K key, V value) {
        for (Entry<K, V> current = head; current != null; current = current.next) {
            if (current.getKey().equals(key)) {
                throw new KeyAlreadyExistsException();
            }
        }
        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        newEntry.next = head;
        head = newEntry;
        size++;
    }

    @Override
    public void update(K key, V value) {
        for (Entry<K, V> current = head; current != null; current = current.next) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove(K key) {
        if (head == null) {
            return;
        }

        if (head.getKey().equals(key)) {
            head = head.next;
            size--;
            return;
        }

        for (Entry<K, V> current = head; current.next != null; current = current.next) {
            if (current.next.getKey().equals(key)) {
                current.next = current.next.next;
                size--;
                return;
            }
        }

        throw new NoSuchElementException();
    }

}

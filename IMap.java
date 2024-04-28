public interface IMap<K, V> {
    int size();

    boolean containsKey(K key);

    V search(K key);

    void add(K key, V value);

    void update(K key, V value);

    void remove(K key);
}
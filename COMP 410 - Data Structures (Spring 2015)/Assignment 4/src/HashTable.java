/**
 * DO NOT MODIFY THIS FILE!
 * Defines how a hash table should be used.
 * TODO: Create a class which implements this.
 */
public interface HashTable<K, V> {

    /**
     * Associates the specified value with the specified key in this hash table.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value);

    /**
     * Returns the value to which the specified key is mapped.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if not found
     */
    public V get(K key);

    /**
     * Returns the value to which the specified key is mapped and removes the entry from the table.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if not found
     */
    public V remove(K key);

}

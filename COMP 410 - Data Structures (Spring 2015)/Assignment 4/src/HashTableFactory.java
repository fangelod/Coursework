/**
 * Factory used for creating hash tables
 */
public final class HashTableFactory {

    public static <K, V> HashTable<K, V> create() {
        return new HashTableImpl();
    }

}

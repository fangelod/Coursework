
public class HashTableImpl implements HashTable {
	private HashList[] table;
	private final static int tableSize = 2011;
	

	public HashTableImpl() {
		table = new HashList[tableSize];
	}
	
	
	public void put(Object key, Object value) {
		int index = key.hashCode() % tableSize;
		if (index < 0) {
			index = Math.abs(index);
		}
		
		if (table[index] == null || table[index].isEmpty()) {
			HashEntry thing = new HashEntry(key, value);
			HashList insert = new HashList();
			insert.enqueue(thing);
			table[index] = insert;
		} else {
			HashEntry thing = new HashEntry(key, value);
			HashList temp = table[index];
			temp.enqueue(thing);
			table[index] = temp;
		}
	}

	
	public Object get(Object key) {
		Object got = null;
		
		int index = key.hashCode() % tableSize;
		if (index < 0) {
			index = Math.abs(index);
		}
		
		HashNode check = null;
		
		if (!table[index].isEmpty()) {
			check = table[index].getFront();
			
		}
		
		if (check != null && check.getData().getKey() == key) {
			got = check.getData().getValue();
		}
		
		if (check != null) {
			while (check.data.getKey() != key) {
				if (check.next == null) {
					break;
				}
				check = check.next;
				if (check.data.getKey() == key) {
					got = check.data.getValue();
				}
			}
		}
		
		return got;
	}

	
	public Object remove(Object key) {
		Object removed = null;
		
		int index = key.hashCode() % tableSize;
		if (index < 0) {
			index = Math.abs(index);
		}
		
		if (!table[index].isEmpty()) {
			if (table[index].getFront().getData().getKey() == key) {
				removed = table[index].getFront().getData().getValue();
				table[index].dequeue();
				return removed;
			}
		}
		
		HashNode<Object> curr;
		HashNode<Object> prev;
		
		if (!table[index].isEmpty()) {
			curr = table[index].getFront();
			prev = null;
			while (curr != null && curr.getData().getKey() != key) {
				prev = curr;
				curr = curr.next;
			}
			if (curr == table[index].getFront() && curr.getData().getKey() == key) {
				removed = curr;
				table[index].setFront(table[index].getFront().next);
			} else if (curr != null) {
				removed = curr;
				prev.next = curr.next;
			}
		}
		

		return removed;
	}

}

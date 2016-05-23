
public class HashEntry {
	private Object key;
	private Object value;
		
	
	public HashEntry(Object key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public Object getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
}

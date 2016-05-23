package a5jedi;

public class BeltFullException extends Exception {
	private Belt belt_contents;
	
	public BeltFullException(Belt belt) {
		super("The belt is full");
		belt_contents = belt;
	}
	
	
	/* Input: none
	 * Output: Belt
	 * This method returns the contents on the belt that is full
	 */
	public Belt getBelt() {
		return belt_contents;
	}
}

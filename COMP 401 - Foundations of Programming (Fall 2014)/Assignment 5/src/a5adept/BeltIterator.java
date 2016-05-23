package a5adept;

import comp401.sushi.Plate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BeltIterator implements Iterator<Plate> {
	private int start;
	private Belt iBelt;
	
	
	public BeltIterator(Belt belt, int start_position) {
		iBelt = belt;
		
		if (start_position < 0) {
			start = ((start_position % iBelt.getSize()) 
					+ iBelt.getSize()) % iBelt.getSize();
		} else {
			start = start_position % iBelt.getSize();
		}
	}
	
	
	/* Input: none
	 * Output: boolean
	 * This method returns true if there is a next plate to iterate to.
	 * It returns false if there is not any other plates other than the
	 * start position
	 */
	public boolean hasNext() {
		int next_pos = start + 1;
		
		if (next_pos > iBelt.getSize() - 1) {
			next_pos = (start + 1) % iBelt.getSize();
		}
		
		if (iBelt.getPlateAtPosition(next_pos) != null) {
			return true;
		} else {
			int checker = 0;
			
			for (int i = next_pos; i < iBelt.getSize(); i++) {
				if (iBelt.getPlateAtPosition(i) != null) {
					checker++;
				}
			}
			
			for (int i = 0; i < next_pos; i++) {
				if (iBelt.getPlateAtPosition(i) != null) {
					checker++;
				}
			}
			
			if (checker > 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	/* Input: none
	 * Output: Plate
	 * This method returns the next plate in line. If there is not
	 * another plate on the belt, then this throws a 
	 * NoSuchElementException.
	 */
	public Plate next() {
		if (hasNext() == false) {
			throw new NoSuchElementException();
		}
		
		int next_pos = start + 1;
		
		if (next_pos > iBelt.getSize() - 1) {
				next_pos = (start + 1) % iBelt.getSize();
		}
		
		for (int i = next_pos; i < iBelt.getSize(); i++) {
			if (iBelt.getPlateAtPosition(i) != null) {
				return iBelt.getPlateAtPosition(i);
			}
		}
			
		for (int i = 0; i < next_pos; i++) {
			if (iBelt.getPlateAtPosition(i) != null) {
				return iBelt.getPlateAtPosition(i);
			}
		}
		
		return null;		
	}
	
	
	/* Input: none
	 * Output: none
	 * This method is not supported in adept. It throws an
	 * UnsupportedOperationException 
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

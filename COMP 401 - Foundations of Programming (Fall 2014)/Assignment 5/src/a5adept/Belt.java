package a5adept;

import java.util.NoSuchElementException;
import comp401.sushi.Plate;
import java.util.Iterator;

public class Belt implements Iterable<Plate>{
	private Plate[] belt_contents;
	private int belt_size;
	private Plate plate_on_belt;
	private int fix_position;
	
	public Belt(int size) {
		if (size == 0 || size < 0) {
			throw new IllegalArgumentException("Size cannot be zero or a negative value");
		} else {
			belt_size = size;
			belt_contents = new Plate[belt_size];
		}
	}
	
	
	/* Input: none
	 * Output: int
	 * This method returns the size of the belt
	 */
	public int getSize() {
		return belt_size;
	}
	
	
	/* Input: position
	 * Output: Plate
	 * This method returns the plate at the specified position.
	 */
	public Plate getPlateAtPosition(int position) {
		if (position < 0) {
			fix_position = ((position % belt_size) + belt_size) % belt_size;
		} else {
			fix_position = position % belt_size;
		}
		
		plate_on_belt = belt_contents[fix_position];
		
		return plate_on_belt;
	}
	
	
	/* Input: plate, position
	 * Output: none
	 * This method sets the position on the belt to the provided plate. If the
	 * inputted plate was empty, then it throws an IllegalArgumentException. If
	 * there is already a plate at the inputted position, then it throws a 
	 * BeltPlateException.
	 */
	public void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		if (plate == null) {
			throw new IllegalArgumentException("The provided plate was empty");
		} else if (getPlateAtPosition(position) != null) {
			throw new BeltPlateException(position, plate, null);
		} else {
			if (position < 0) {
				fix_position = ((position % belt_size) + belt_size) % belt_size;
			} else {
				fix_position = position % belt_size;
			}
			belt_contents[fix_position] = plate;
		}
	}
	
	
	/* Input: position
	 * Output: none
	 * This method sets the plate at the inputted position, to null, meaning
	 * that the position on the belt is now empty.
	 */
	public void clearPlateAtPosition(int position) {
		//setPlateAtPosition(null, position);
		if (position < 0) {
			fix_position = ((position % belt_size) + belt_size) % belt_size;
		} else {
			fix_position = position % belt_size;
		}
		
		belt_contents[fix_position] = null;
	}
	
	
	/* Input: position
	 * Output: Plate
	 * This method returns a reference to the plate at the inputted position.
	 * It sets the plate at the inputted position to be empty. If there is not
	 * a plate at the inputted position, then it throws a 
	 * NoSuchElementException
	 */
	public Plate removePlateAtPosition(int position) {
		Plate removed_plate = getPlateAtPosition(position);
		
		if (getPlateAtPosition(position) == null) {
			throw new NoSuchElementException("There is not plate at that position");
		} else {
			clearPlateAtPosition(position);
			
			return removed_plate;
		}
	}
	
	
	/* Input: plate, position
	 * Output: int
	 * This method returns the position where the inputted plate was placed.
	 * It sets the inputted plate at the nearest open position. If there is
	 * not an open position on the belt, it throws a BeltFullException
	 */
	public int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException {
		if (position < 0) {
			fix_position = ((position % belt_size) + belt_size) % belt_size;
		} else {
			fix_position = position % belt_size;
		}
		
		int pos_count = position;
		int placeholder = fix_position;
		
		while (getPlateAtPosition(fix_position) != null) {
			pos_count++;
			if (pos_count < 0) {
				fix_position = ((pos_count % belt_size) + belt_size) % belt_size;
			} else {
				fix_position = pos_count % belt_size;
			}
						
			fix_position = pos_count % belt_size;
			
			if (fix_position == placeholder) {
				throw new BeltFullException(this);
			}
		}
		
		if (getPlateAtPosition(fix_position) == null) {
			belt_contents[fix_position] = plate;
		}
		
		int where_placed = fix_position;
		
		return where_placed;
	}
	
	
	/* Input: none
	 * Output: Iterator<Plate>
	 * This method returns a new BeltIterator object with a starting
	 * position at 0.
	 */
	public Iterator<Plate> iterator() {
		Iterator<Plate> from_zero = new BeltIterator(this, 0);
		
		return from_zero;
	}
	
	
	/* Input: position
	 * Output: Iterator<Plate>
	 * This method returns a new BeltIterator object with a starting
	 * position at the inputted position.
	 */
	public Iterator<Plate> iteratorFromPosition(int position) {
		Iterator<Plate> from_pos = new BeltIterator(this, position);
		
		return from_pos;
	}
	
	
	/* Input: none
	 * Output: none
	 * This method rotates the belt so that the position of the contents
	 * on the belt are shifted over one
	 */
	public void rotate() {
		Plate[] rotated_contents = new Plate[belt_size];
				
		for (int i = 0; i < belt_size-1; i++) {
			rotated_contents[0] = belt_contents[i+1];
		}
		
		rotated_contents[belt_size-1] = belt_contents[0];
		
		belt_contents = rotated_contents;
	}
}

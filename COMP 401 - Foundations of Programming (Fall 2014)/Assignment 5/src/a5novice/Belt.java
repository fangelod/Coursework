package a5novice;

import java.util.NoSuchElementException;
import comp401.sushi.Plate;

public class Belt {
	private Plate[] belt_contents;
	private int belt_size;
	private Plate plate_on_belt;
	
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
	 * This method returns the plate at the specified position. If the inputted
	 * position is out of range, it throws an IllegalArgumentException.
	 */
	public Plate getPlateAtPosition(int position) {
		if (position < 0 || position > getSize()-1) {
			throw new IllegalArgumentException("The position is out of range");
		}
		else {
			plate_on_belt = belt_contents[position];
		}
		
		return plate_on_belt;
	}
	
	
	/* Input: plate, position
	 * Output: none
	 * This method sets the position on the belt to the provided plate. If the
	 * inputted plate was empty, then it throws an IllegalArgumentException. If
	 * the inputted position is out of range, it throws an 
	 * IllegalArgumentException. If there is already a plate at the inputted
	 * position, then it throws a BeltPlateException.
	 */
	public void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		if (plate == null) {
			throw new IllegalArgumentException("The provided plate was empty");
		} else if (position < 0 || position > getSize()-1) {
			throw new IllegalArgumentException("The position is out of range");
		} else if (getPlateAtPosition(position) != null) {
			throw new BeltPlateException(position, plate, null);
		} else {
			belt_contents[position] = plate;
		}
	}
	
	
	/* Input: position
	 * Output: none
	 * This method sets the plate at the inputted position, to null, meaning
	 * that the position on the belt is now empty. If the inputted position
	 * is out of range, then it throws an IllegalArgumentException.
	 */
	public void clearPlateAtPosition(int position) {
		if (position < 0 || position > getSize()-1) {
			throw new IllegalArgumentException("The position is out of range");
		} else {
			//setPlateAtPosition(null, position);
			belt_contents[position] = null;
		}
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
}

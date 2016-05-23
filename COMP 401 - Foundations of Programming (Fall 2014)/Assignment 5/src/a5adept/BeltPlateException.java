package a5adept;

import comp401.sushi.Plate;

public class BeltPlateException extends Exception {
	private int place;
	private Plate plate_set;
	private Belt on_belt;
	
	public BeltPlateException(int position, Plate plate_to_be_set, Belt belt) {
		super("There is already a plate at that position");
		place = position;
		plate_set = plate_to_be_set;
		on_belt = belt;
	}
	
	
	/* Input: none
	 * Output: int
	 * This method returns the location of the plate on the belt
	 */
	public int getPosition() {
		return place;
	}
	
	
	/* Input: none
	 * Output: Plate
	 * This method returns the plate that is to be set on the belt
	 */
	public Plate getPlateToSet() {
		return plate_set;
	}
	
	
	/* Input: none
	 * Output: Belt
	 * This method returns the belt
	 */
	public Belt getBelt() {
		return on_belt;
	}

}

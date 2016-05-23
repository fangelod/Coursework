package a7adept;

import java.util.NoSuchElementException;
import java.util.Observer;

import comp401.sushi.Plate;

public class Belt extends java.util.Observable {

	Plate[] belt;
	DecoratedPlate[] decorated_belt;
	PlateEvent added;
	PlateEvent removed;
	int rotate_count;
	
	public Belt(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Illegal belt size");
		}

		belt = new Plate[size];
		decorated_belt = new DecoratedPlate[size];
		
		rotate_count = 0;
	}
	
	public int getSize() {
		return belt.length;
	}
	
	public Plate getPlateAtPosition(int position) {
		return belt[correct_position(position)];
	}
	
	public void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		if (plate == null) {
			throw new IllegalArgumentException();
		}
		
		if (getPlateAtPosition(position) != null) {
			throw new BeltPlateException(position, plate, this);
		} else {
			DecoratedPlate temp_decPlate = new DecoratedPlateImpl(plate);
			temp_decPlate.setAgeOfPlate(rotate_count);
			
			belt[correct_position(position)] = plate;
			decorated_belt[correct_position(position)] = temp_decPlate;
		}
		
		// Notify of addition
		added = new PlateEvent(PlateEvent.EventType.PLATE_PLACED, 
				plate, correct_position(position));
		setChanged();
		notifyObservers(added);
	}

	public void clearPlateAtPosition(int position) {
		// Notify of removal
		removed = new PlateEvent(PlateEvent.EventType.PLATE_REMOVED, 
				belt[correct_position(position)], correct_position(position));
		setChanged();
		notifyObservers(removed);
		
		belt[correct_position(position)] = null;
		decorated_belt[correct_position(position)] = null;
	}
	
	public Plate removePlateAtPosition(int position) {
		Plate plate_at_position = getPlateAtPosition(position);
		
		if (plate_at_position == null) {
			throw new NoSuchElementException();
		}
		
		clearPlateAtPosition(position);
		
		return plate_at_position;
	}
	
	public int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException {
		for (int offset=0; offset < getSize(); offset++) {
			try {
				setPlateAtPosition(plate, position+offset);
				return position+offset;
			} catch (BeltPlateException e) {
			}
		}
		throw new BeltFullException(this);
	}

	public void rotate() {
		Plate last_plate = belt[getSize()-1];
		DecoratedPlate dec_last_plate = decorated_belt[getSize()-1];
		
		for (int i=getSize()-1; i>0; i--) {
			belt[i] = belt[i-1];
			decorated_belt[i] = decorated_belt[i-1];
		}
		
		belt[0] = last_plate;
		decorated_belt[0] = dec_last_plate;
		
		rotate_count++;
	}
	
	
	/* Input: int position
	 * Output: int age
	 * This methods returns the age of the plate at the entered position
	 */
	public int getAgeOfPlateAtPosition(int position) {
		int age;
		
		if (decorated_belt[correct_position(position)] == null) {
			age = -1;
		} else {
			age = rotate_count - decorated_belt[correct_position(position)].getAgeOfPlate();
		}

		return age;
	}
	
	
	
	/* Input: none
	 * Output: int rotate_count
	 * This method returns the current number of rotations that the belt has 
	 * 	undergone
	 */
	public int getRotateCount() {
		return rotate_count;
	}
	
	
	private int correct_position(int position) {
		if (position < 0) {
			return ((position%getSize())+getSize())%getSize();
		}
		return position%getSize();
	}
}
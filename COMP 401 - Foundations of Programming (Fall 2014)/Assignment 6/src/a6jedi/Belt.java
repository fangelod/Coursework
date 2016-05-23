package a6jedi;

import java.util.NoSuchElementException;
import java.util.Observer;

import comp401.sushi.Plate;

public class Belt extends java.util.Observable {

	Plate[] belt;
	Customer[] cust_array;
	
	public Belt(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Illegal belt size");
		}

		belt = new Plate[size];
		cust_array = new Customer[size];
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
			belt[correct_position(position)] = plate; 
		}
		
		// Notify of addition
		PlateEvent added = new PlateEvent(PlateEvent.EventType.PLATE_PLACED, 
				plate, correct_position(position));
		setChanged();
		notifyObservers(added);
	}

	public void clearPlateAtPosition(int position) {
		// Notify of removal
		PlateEvent removed = new PlateEvent(PlateEvent.EventType.PLATE_REMOVED, 
				belt[correct_position(position)], correct_position(position));
		setChanged();
		notifyObservers(removed);
		
		belt[correct_position(position)] = null;
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
		for (int i=getSize()-1; i>0; i--) {
			belt[i] = belt[i-1];
		}
		belt[0] = last_plate;
		
		/* belt notifies all registered customers if there is now a
		 * 	plate at the Customer's position by calling the Customer's
		 * 	observePlateOnBelt method.
		 */
		for (int i = 0; i < belt.length; i++) {
			if (cust_array[i] != null && belt[i] != null) {
				cust_array[i].observePlateOnBelt(this, belt[i], i);
			}
		}
	}
	
	private int correct_position(int position) {
		if (position < 0) {
			return ((position%getSize())+getSize())%getSize();
		}
		return position%getSize();
	}
	
	/* Input: Customer c, int position
	 * Output: none
	 * This method should throw a runtime exeception if the position is
	 * 	already registered with a different customer object. It should 
	 * 	also throw a runtime exception if the Customer is already 
	 * 	registered at a different position.
	 */
	public void registerCustomerAtPosition(Customer c, int position) {
		if (cust_array[correct_position(position)] != null) {
			throw new RuntimeException("There is already a customer in "
					+ "that position");
		} else {
			for (int i = 0; i < cust_array.length; i++) {
				if (cust_array[i] == c) {
					throw new RuntimeException("This customer is "
							+ "already registered in a position");
				}
			}
			cust_array[correct_position(position)] = c;
		}
		
	}
	
	
	/* Input: int position
	 * Output: Customer cust_ref
	 * This method should unregister the Customer at the specified
	 * 	position and return a reference to that customer. This method
	 * 	should return null if there is no Customer registered there.
	 */
	public Customer unregisterCustomerAtPosition(int position) {
		if (cust_array[correct_position(position)] == null) {
			return null;
		}
		Customer cust_ref = cust_array[correct_position(position)];
		cust_array[correct_position(position)] = null;
		return cust_ref;
	}
}

package a5jedi;

import comp401.sushi.Plate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BeltIterator implements Iterator<Plate> {
	private int start;
	private Belt iBelt;
	private int where = Integer.MAX_VALUE;
	private double price = 0;
	private Plate.Color color = null;
	
	
	public BeltIterator(Belt belt, int start_position) {
		iBelt = belt;
		
		if (start_position < 0) {
			start = ((start_position % iBelt.getSize()) 
					+ iBelt.getSize()) % iBelt.getSize();
		} else {
			start = start_position % iBelt.getSize();
		}
	}
	
	
	public BeltIterator(Belt belt, int start_position, double max_price) {
		iBelt = belt;
		
		if (start_position < 0) {
			start = ((start_position % iBelt.getSize()) 
					+ iBelt.getSize()) % iBelt.getSize();
		} else {
			start = start_position % iBelt.getSize();
		}
		
		price = max_price;
	}
	
	
	public BeltIterator(Belt belt, int start_position, Plate.Color color_filter) {
		iBelt = belt;
		
		if (start_position < 0) {
			start = ((start_position % iBelt.getSize()) 
					+ iBelt.getSize()) % iBelt.getSize();
		} else {
			start = start_position % iBelt.getSize();
		}
		
		color = color_filter;
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
		
		if (color == null && price == 0) {
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
		} else if (color == null && price != 0) {
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
		} else if (color != null && price == 0) {
			if (iBelt.getPlateAtPosition(next_pos) != null 
					&& iBelt.getPlateAtPosition(next_pos).getColor() == color) {
				return true;
			} else {
				int checker = 0;
				
				for (int i = next_pos; i < iBelt.getSize(); i++) {
					if (iBelt.getPlateAtPosition(i) != null 
							&& iBelt.getPlateAtPosition(i).getColor() == color) {
						checker++;
					}
				}
				
				for (int i = 0; i < next_pos; i++) {
					if (iBelt.getPlateAtPosition(i) != null 
							&& iBelt.getPlateAtPosition(i).getColor() == color) {
						checker++;
					}
				}
				
				if (checker > 0) {
					return true;
				} else {
					return false;
				}
			}
		} else if (color == null && price != 0) {
			if (iBelt.getPlateAtPosition(next_pos) != null 
					&& iBelt.getPlateAtPosition(next_pos).getPrice() <= price) {
				return true;
			} else {
				int checker = 0;
				
				for (int i = next_pos; i < iBelt.getSize(); i++) {
					if (iBelt.getPlateAtPosition(i) != null 
							&& iBelt.getPlateAtPosition(i).getPrice() <= price) {
						checker++;
					}
				}
				
				for (int i = 0; i < next_pos; i++) {
					if (iBelt.getPlateAtPosition(i) != null 
							&& iBelt.getPlateAtPosition(i).getPrice() <= price) {
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
		return false;
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
		
		if (color == null && price == 0) {
			for (int i = next_pos; i < iBelt.getSize(); i++) {
				if (iBelt.getPlateAtPosition(i) != null) {
					i = where;
					return iBelt.getPlateAtPosition(i);
				}
			}
				
			for (int i = 0; i < next_pos; i++) {
				if (iBelt.getPlateAtPosition(i) != null) {
					i = where;
					return iBelt.getPlateAtPosition(i);
				}
			}
		} else if (color != null && price == 0) {
			for (int i = next_pos; i < iBelt.getSize(); i++) {
				if (iBelt.getPlateAtPosition(i) != null 
						&& iBelt.getPlateAtPosition(i).getColor() == color) {
					i = where;
					
					return iBelt.getPlateAtPosition(i);
				}
			}
				
			for (int i = 0; i < next_pos; i++) {
				if (iBelt.getPlateAtPosition(i) != null 
						&& iBelt.getPlateAtPosition(i).getColor() == color) {
					i = where;
					
					return iBelt.getPlateAtPosition(i);
				}
			}
		} else if (color == null & price != 0) {
			for (int i = next_pos; i < iBelt.getSize(); i++) {
				if (iBelt.getPlateAtPosition(i) != null 
						&& iBelt.getPlateAtPosition(i).getPrice() <= price) {
					i = where;
					
					return iBelt.getPlateAtPosition(i);
				}
			}
				
			for (int i = 0; i < next_pos; i++) {
				if (iBelt.getPlateAtPosition(i) != null 
						&& iBelt.getPlateAtPosition(i).getPrice() <= price) {
					i = where;
					
					return iBelt.getPlateAtPosition(i);
				}
			}
		}
		return null;		
	}
	
	
	/* Input: none
	 * Output: none
	 * This method removes the next plate if next() has been called.
	 * This method cannot be called unless next() has been called. If
	 * next() has not been called, then it throws an 
	 * IllegalStateException. This method also cannot be called more
	 * than once after next() has been called. next() must be called
	 * again if remove() is to be called again.
	 */
	public void remove() {
		if (where < Integer.MAX_VALUE) {
			iBelt.clearPlateAtPosition(where);
			where = Integer.MAX_VALUE;
		} else {
			throw new IllegalStateException();
		}
	}
}

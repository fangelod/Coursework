package a7adept;

import java.util.Observable;

import comp401.sushi.Plate;


public class PlateCounter implements java.util.Observer {
	private int red_count = 0;
	private int green_count = 0;
	private int blue_count = 0;
	private int gold_count = 0;
	
	
	public PlateCounter() {
	
	}

	
	/* Input: Belt belt, PlateEvent event
	 * Output: None
	 * This method notifies the Observer that the 
	 * 	observed object has changed
	 */
	@Override
	public void update(Observable o, Object arg) {
		Belt belt = (Belt) o;
		PlateEvent event = (PlateEvent) arg;
		
		if (event.getType() == PlateEvent.EventType.PLATE_PLACED) {
			if (event.getPlate().getColor() == Plate.Color.RED) {
				red_count++;
			} else if (event.getPlate().getColor() == Plate.Color.GREEN) {
				green_count++;
			} else if (event.getPlate().getColor() == Plate.Color.BLUE) {
				blue_count++;
			} else if (event.getPlate().getColor() == Plate.Color.GOLD) {
				gold_count++;
			}
		} else if (event.getType() == PlateEvent.EventType.PLATE_REMOVED) {
			if (event.getPlate() == null) {
				// Doesn't affect count if there wasn't a plate to begin with
			} else {
				if (event.getPlate().getColor() == Plate.Color.RED) {
					red_count--;
				} else if (event.getPlate().getColor() == Plate.Color.GREEN) {
					green_count--;
				} else if (event.getPlate().getColor() == Plate.Color.BLUE) {
					blue_count--;
				} else if (event.getPlate().getColor() == Plate.Color.GOLD) {
					gold_count--;
				}
			}
			
		}
	}
	
	
	/* Input: None
	 * Output: int red_count
	 * This method returns the number of red 
	 * 	plates on the belt
	 */
	public int getRedPlateCount() {
		return red_count;
	}
	
	/* Input: None
	 * Output: int green_count
	 * This method returns the number of green 
	 * 	plates on the belt
	 */
	public int getGreenPlateCount() {
		return green_count;
	}
	
	
	/* Input: None
	 * Output: int blue_count
	 * This method returns the number of blue 
	 * 	plates on the belt
	 */
	public int getBluePlateCount() {
		return blue_count;
	}
	
	
	/* Input: None
	 * Output: int gold_count
	 * This method returns the number of gold 
	 * 	plates on the belt
	 */
	public int getGoldPlateCount() {
		return gold_count;
	}

}

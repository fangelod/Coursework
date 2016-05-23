package a7adept;

import java.util.Observable;

import comp401.sushi.Plate;

public class ProfitCounter implements java.util.Observer {
	private double total_profit = 0;
	private int plate_count = 0;
	
	public ProfitCounter() {
	
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
			double added = event.getPlate().getProfit();
			plate_count++;
			total_profit += added;
		} else if (event.getType() == PlateEvent.EventType.PLATE_REMOVED) {
			if (event.getPlate() == null) {
				//// Doesn't affect profit if there wasn't a plate to begin with
			} else {
				double removed = event.getPlate().getProfit();
				plate_count--;
				total_profit -= removed;
			}
			
		}
	}

	
	/* Input: none 
	 * Output: double
	 * This method returns the sum of all profit for all
	 * plates currently on the belt
	 */
	public double getTotalBeltProfit() {
		return total_profit;
	}
	
	
	/* Input: none
	 * Output: double
	 * This method returns the average profit per plate
	 * currently on the belt
	 */
	public double getAverageBeltProfit() {
		return total_profit / plate_count;
	}
}

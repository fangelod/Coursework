package a7jedi;

import java.util.Observable;

import comp401.sushi.Plate;

public class SpoilageCollector implements java.util.Observer {
	private double cost;
	private double shellfish;
	private double seafood;
	private double food;
	
	public SpoilageCollector() {
		cost = 0;
		shellfish = 0;
		seafood = 0;
		food = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		Belt belt = (Belt) o;
		PlateEvent event = (PlateEvent) arg;
		
		if (event.getType() == PlateEvent.EventType.PLATE_SPOILED) {
			if (event.getPlate().getContents().hasShellfish() == true) {
				cost += event.getPlate().getContents().getCost();
				
				for (int i = 0; i < event.getPlate().getContents().getIngredients().length; i++) {
					if (event.getPlate().getContents().getIngredients()[i].isShellfish() == true) {
						shellfish += event.getPlate().getContents().getIngredients()[i].getAmount();
						seafood += event.getPlate().getContents().getIngredients()[i].getAmount();
					}
					
					food += event.getPlate().getContents().getIngredients()[i].getAmount();
				}
			} else if (event.getPlate().getContents().hasShellfish() == false 
					&& event.getPlate().getContents().isVegetarian() == false) {
				cost += event.getPlate().getContents().getCost();
				
				for (int i = 0; i < event.getPlate().getContents().getIngredients().length; i++) {
					if (event.getPlate().getContents().getIngredients()[i].isShellfish() == false 
							&& event.getPlate().getContents().getIngredients()[i].isVegetarian() == false) {
						seafood += event.getPlate().getContents().getIngredients()[i].getAmount();
					}
					
					food += event.getPlate().getContents().getIngredients()[i].getAmount();
				}
			} else if (event.getPlate().getContents().isVegetarian() == true) {
				cost += event.getPlate().getContents().getCost();
				
				for (int i = 0; i < event.getPlate().getContents().getIngredients().length; i++) {
					food += event.getPlate().getContents().getIngredients()[i].getAmount();
				}
			}
			
			belt.clearPlateAtPosition(event.getPosition());
		}
	}
	
	
	/* Input: none
	 * Output: double cost
	 * This method returns the total cost of all the spoiled sushi
	 */
	public double getTotalSpoiledCost() {
		return cost;
	}
	
	
	/* Input: none
	 * Output: double shellfish
	 * This method returns the total amount of spoiled shellfish
	 */
	public double getTotalSpoiledShellfish() {
		return shellfish;
	}

	
	/* Input: none
	 * Output: double seafood
	 * This method returns the total amount of spoiled seafood
	 */
	public double getTotalSpoiledSeafood() {
		return seafood;
	}
	
	
	/* Input: none
	 * Output: double food
	 * This method returns the total amount of spoiled ingredients
	 */
	public double getTotalSpoiledFood() {
		return food;
	}
}

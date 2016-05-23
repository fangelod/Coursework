package a3adept;

public class Sashimi implements Sushi {
	public enum SashimiType {TUNA, SALMON, EEL, CRAB, SHRIMP};
	private Ingredient[] ingredients;
	private double cost = 0;
	private int rice_check = 0;
	private int shell_check = 0;
	private int veg_check = 0;
	private Ingredient[] temp_roll;
	
	public Sashimi(SashimiType type) {
		temp_roll = new Ingredient[1];
		
		switch (type) {
			case TUNA:
				Tuna tuna_roll = new Tuna(0.75);
				temp_roll[0] = tuna_roll;
				break;
			case SALMON: 
				Salmon salm_roll = new Salmon(0.75);
				temp_roll[0] = salm_roll;
				break;
			case EEL: 
				Eel eel_roll = new Eel(0.75);
				temp_roll[0] = eel_roll;
				break;
			case CRAB: 
				Crab crab_roll = new Crab(0.75);
				temp_roll[0] = crab_roll;
				break;
			case SHRIMP:
				Shrimp shri_roll = new Shrimp(0.75);
				temp_roll[0] = shri_roll;
				break;
		}
		
		ingredients = temp_roll.clone();
	}
	
	
	/* Input: none
	 * Output: ingredients
	 * This method returns an array of
	 * 		ingredient objects
	 */
	public Ingredient[] getIngredients() {
		return ingredients;
	}

	
	/* Input: none
	 * Output: cost
	 * This method returns the cost of a sashimi
	 * 		roll using the costs of each
	 * 		ingredient
	 */
	public double getCost() {
		cost = ingredients[0].getCost();
		
		return cost;
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not a sashimi roll has the
	 * 		ingredient rice
	 */
	public boolean hasRice() {
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i].isRice() == true) {
				rice_check++;
			}
		}
		
		if (rice_check > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not a sashimi roll has a
	 * 		shellfish ingredient in it
	 */
	public boolean hasShellfish() {
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i].isShellfish() == true) {
				shell_check++;
			}
		}
		
		if (shell_check > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the sashimi roll is
	 * 		vegetarian-friendly
	 */
	public boolean isVegetarian() {
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i].isVegetarian() == false) {
				veg_check++;
			}
		}
		
		if (veg_check > 0) {
			return false;
		}
		else {
			return true;
		}
	}
}

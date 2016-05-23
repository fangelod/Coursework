package a3adept;

public class Roll implements Sushi {
	private Ingredient[] ingredients;
	private double cost = 0;
	private int rice_check = 0;
	private int shell_check = 0;
	private int veg_check = 0;
	
	public Roll (Ingredient[] roll_ingredients) {
		ingredients = roll_ingredients.clone();
	}
	
	
	/* Input: none
	 * Output: ingredients
	 * This method returns an array of ingredient 
	 * 		objects
	 */
	public Ingredient[] getIngredients() {
		return ingredients;
	}

	
	/* Input: none
	 * Output: cost
	 * This method returns the cost of a sushi
	 * 		roll using the costs of the ingredients
	 */
	public double getCost() {
		cost = 0;
		
		for (int i = 0; i < ingredients.length; i++) {
			cost += ingredients[i].getCost();
		}
		
		return cost;
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the sushi roll has the
	 * 		ingredient rice in it
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
	 * 		whether or not the sushi roll has a
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
	 * 		whether or not the sushi roll is
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

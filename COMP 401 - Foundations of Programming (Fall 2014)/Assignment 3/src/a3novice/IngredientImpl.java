package a3novice;

public class IngredientImpl implements Ingredient {
	protected String name;
	protected double amount = 0;
	protected double price = 0;
	protected double cost;
	
	public IngredientImpl() {
	}

	
	/* Input: none
	 * Output: amount
	 * This method returns the amount of an ingredient
	 */
	public double getAmount() {
		return amount;
	}

	
	/* Input: none
	 * Output: cost
	 * This method returns the cost of an ingredient
	 * 		using the ingredient's amount and price
	 */
	public double getCost() {
		cost = amount * price;
		
		return cost;
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is rice
	 */
	public boolean isRice() {
		if (name.equalsIgnoreCase("rice")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is a
	 * 		shellfish
	 */
	public boolean isShellfish() {
		if (name.equalsIgnoreCase("crab")) {
			return true;
		}
		else if (name.equalsIgnoreCase("shrimp")) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is
	 * 		vegetarian-friendly
	 */
	public boolean isVegetarian() {
		if (name.equalsIgnoreCase("avocado")) {
			return true;
		}
		else if (name.equalsIgnoreCase("rice")) {
			return true;
		}
		else if (name.equalsIgnoreCase("seaweed")) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/* Input: none
	 * Output: name
	 * This method returns the name of an ingredient
	 */
	public String getName() {
		return name;
	}
}

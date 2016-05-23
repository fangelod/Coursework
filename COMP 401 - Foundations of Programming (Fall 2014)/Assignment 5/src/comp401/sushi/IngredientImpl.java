package comp401.sushi;

public class IngredientImpl implements Ingredient {
	private double amount;
	private double price_per_ounce;
	private String name;
	private boolean is_rice;
	private boolean is_shellfish;
	private boolean is_vegetarian;
	
	//protected String name;
	//protected double amount = 0;
	//protected double price = 0;
	//protected double cost;
	
	
	protected IngredientImpl(double amount, 
							 double price_per_ounce, 
							 boolean is_rice, 
							 boolean is_shellfish, 
							 boolean is_vegetarian, 
							 String name) {
		if (amount <= 0) {
			throw new RuntimeException("Illegal amount of ingredient");
		}
		
		this.amount = amount;
		this.price_per_ounce = price_per_ounce;
		this.is_rice = is_rice;
		this.is_shellfish = is_shellfish;
		this.is_vegetarian = is_vegetarian;
		this.name = name;
	}
	
	//public IngredientImpl() {
	//}
	
	
	@Override
	public double getAmount() {
		return amount;
	}
	
	/* Input: none
	 * Output: amount
	 * This method returns the amount of an ingredient
	 */
	//public double getAmount() {
	//	return amount;
	//}

	
	@Override
	public double getCost() {
		return amount * price_per_ounce;
	}
	
	/* Input: none
	 * Output: cost
	 * This method returns the cost of an ingredient
	 * 		using the ingredient's amount and price
	 */
	//public double getCost() {
	//	cost = amount * price;
	//	
	//	return cost;
	//}

	
	@Override
	public boolean isRice() {
		return is_rice;
	}
	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is rice
	 */
	//public boolean isRice() {
	//	if (name.equalsIgnoreCase("rice")) {
	//		return true;
	//	}
	//	else {
	//		return false;
	//	}
	//}
	
	
	@Override
	public boolean isShellfish() {
		return is_shellfish;
	}
	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is a
	 * 		shellfish
	 */
	//public boolean isShellfish() {
	//	if (name.equalsIgnoreCase("crab")) {
	//		return true;
	//	}
	//	else if (name.equalsIgnoreCase("shrimp")) {
	//		return true;
	//	}
	//	else {
	//		return false;
	//	}
	//}

	
	@Override
	public boolean isVegetarian() {
		return is_vegetarian;
	}
	
	/* Input: none
	 * Output: true/false
	 * This method returns a boolean determining
	 * 		whether or not the ingredient is
	 * 		vegetarian-friendly
	 */
	//public boolean isVegetarian() {
	//	if (name.equalsIgnoreCase("avocado")) {
	//		return true;
	//	}
	//	else if (name.equalsIgnoreCase("rice")) {
	//		return true;
	//	}
	//	else if (name.equalsIgnoreCase("seaweed")) {
	//		return true;
	//	}
	//	else {
	//		return false;
	//	}
	//}

	
	@Override
	public String getName() {
		return name;
	}
	
	/* Input: none
	 * Output: name
	 * This method returns the name of an ingredient
	 */
	//public String getName() {
	//	return name;
	//}
}

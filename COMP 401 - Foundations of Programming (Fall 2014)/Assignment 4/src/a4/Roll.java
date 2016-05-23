package a4;

import java.util.ArrayList;

public class Roll implements Sushi {
	private Ingredient[] ingredients;
	private double cost;
	private int rice_check = 0;
	private int shell_check = 0;
	private int veg_check = 0;
	private Ingredient[] temp_roll;
	private int rep_check = 0;
	private Ingredient[] fix_roll;
	private int combine1;
	private int combine2;
	private double new_amount;
	private Ingredient fix_ingr;
	private String test_ingr;
	private int seaw_check = 0;
	private ArrayList<Ingredient> fix_list = new ArrayList<Ingredient>();
	private int seaw_source;
	
	public Roll (Ingredient[] roll_ingredients) {
		temp_roll = roll_ingredients.clone();
		
		//Testing for valid ingredients
		hasValidIngredients();
		
		//Testing for repeats
		checkForRepeatIngredients();
		
		//Testing for seaweed requirement
		checkSeaweed();
		
		fix_roll = fix_list.toArray(new Ingredient[fix_list.size()]);
		ingredients = fix_roll.clone();
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
	
	
	/* Input: none
	 * Output: none
	 * This method checks the array of ingredients
	 * 		given to make sure that there is not a
	 * 		"null" ingredient or an invalid one
	 */
	public void hasValidIngredients() {
		for (int i = 0; i < temp_roll.length; i++) {
			if (temp_roll[i].getName().equalsIgnoreCase("avocado") ||
					temp_roll[i].getName().equalsIgnoreCase("crab") ||
					temp_roll[i].getName().equalsIgnoreCase("eel") ||
					temp_roll[i].getName().equalsIgnoreCase("rice") ||
					temp_roll[i].getName().equalsIgnoreCase("salmon") ||
					temp_roll[i].getName().equalsIgnoreCase("seaweed") ||
					temp_roll[i].getName().equalsIgnoreCase("shrimp") ||
					temp_roll[i].getName().equalsIgnoreCase("tuna")) {
			}
			else {
				throw new RuntimeException("There is an invalid ingredient");
			}
		}
	}
	
	
	/* Input: none
	 * Output: array list
	 * This method checks the array of ingredients
	 * 		given and combines repeated ingredients
	 * 		and takes those combinations and the
	 * 		other ingredients that had no repeats
	 * 		and stores them into an arraylist
	 */
	public ArrayList<Ingredient> checkForRepeatIngredients() {
		for (int i = 0; i < temp_roll.length; i++) {
			rep_check = 0;
			combine1 = -1;
			combine2 = -1;
			
			for (int j = 0; j < temp_roll.length; j++) {
				if (temp_roll[i].getName().equals(temp_roll[j].getName())) {
					if (i != j) {
						combine1 = i;
						combine2 = j;
						rep_check++;
					}
				}	
			}
			
			if (rep_check == 0) {
				fix_list.add(temp_roll[i]);
			}
			else if (rep_check == 1 && combine1 < combine2) {
				test_ingr = temp_roll[combine1].getName();
				new_amount = temp_roll[combine1].getAmount() +
						temp_roll[combine2].getAmount();
				
				fix_list.add(whichIngredient(new_amount));
			}
		}
		
		return fix_list;
	}
	
	
	/* Input: none
	 * Output: Ingredient object
	 * This method returns an Ingredient object that
	 * 		is the combination of the repeated
	 * 		ingredients in the given array
	 */
	public Ingredient whichIngredient(double amount) {
		if (test_ingr.equalsIgnoreCase("avocado")) {
			fix_ingr = new Avocado(amount);
		}
		else if (test_ingr.equalsIgnoreCase("crab")) {
			fix_ingr = new Crab(amount);
		}
		else if (test_ingr.equalsIgnoreCase("eel")) {
			fix_ingr = new Eel(amount);
		}
		else if (test_ingr.equalsIgnoreCase("rice")) {
			fix_ingr = new Rice(amount);
		}
		else if (test_ingr.equalsIgnoreCase("salmon")) {
			fix_ingr = new Salmon(amount);
		}
		else if (test_ingr.equalsIgnoreCase("seaweed")) {
			fix_ingr = new Seaweed(amount);
		}
		else if (test_ingr.equalsIgnoreCase("shrimp")) {
			fix_ingr = new Shrimp(amount);
		}
		else if (test_ingr.equalsIgnoreCase("tuna")) {
			fix_ingr = new Tuna(amount);
		}
		
		return fix_ingr;
	}
	
	
	/* Input: none
	 * Output: none
	 * This method checks the array of ingredients
	 * 		given to make sure that there is at
	 * 		least 0.1 ounces of seaweed. If there
	 * 		is not, this method removes the old
	 * 		seaweed in the arraylist and replaces
	 * 		it with a new Seaweed Ingredient
	 * 		object of 0.1 amount
	 */
	public void checkSeaweed() {
		seaw_check = 0;
		
		for (int i = 0; i < fix_list.size(); i++) {
			if (fix_list.get(i).getName().equalsIgnoreCase("seaweed")) {
				seaw_check++;
				seaw_source = i;
			}
		}
		
		if (seaw_check > 0) {
			if (fix_list.get(seaw_source).getAmount() < 0.1) {
				fix_list.remove(fix_list.get(seaw_source));
				fix_list.add(new Seaweed(0.1));
			}
		}
		else {
			fix_list.add(new Seaweed(0.1));
		}
	}
}

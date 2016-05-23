package a3jedi;

public class Crab extends IngredientImpl {
	public Crab(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "crab";
		super.price = 1.50;
	}
}

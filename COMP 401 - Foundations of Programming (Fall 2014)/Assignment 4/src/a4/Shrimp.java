package a4;

public class Shrimp extends IngredientImpl {
	public Shrimp(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "shrimp";
		super.price = 1.15;
	}
}

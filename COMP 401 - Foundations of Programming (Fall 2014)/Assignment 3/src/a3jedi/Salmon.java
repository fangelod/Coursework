package a3jedi;

public class Salmon extends IngredientImpl {
	public Salmon(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "salmon";
		super.price = 0.75;
	}
}

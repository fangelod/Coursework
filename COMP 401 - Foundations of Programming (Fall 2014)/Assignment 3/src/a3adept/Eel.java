package a3adept;

public class Eel extends IngredientImpl {
	public Eel(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "eel";
		super.price = 1.25;
	}
}

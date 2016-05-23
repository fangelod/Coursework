package a4;

public class Tuna extends IngredientImpl {
	public Tuna(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "tuna";
		super.price = 0.65;
	}
}

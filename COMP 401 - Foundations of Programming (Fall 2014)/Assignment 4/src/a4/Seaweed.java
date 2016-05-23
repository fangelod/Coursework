package a4;

public class Seaweed extends IngredientImpl {
	public Seaweed(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "seaweed";
		super.price = 0.40;
	}
}

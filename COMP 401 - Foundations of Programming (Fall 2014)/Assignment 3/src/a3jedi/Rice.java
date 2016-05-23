package a3jedi;

public class Rice extends IngredientImpl {
	public Rice(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "rice";
		super.price = 0.25;
	}
}

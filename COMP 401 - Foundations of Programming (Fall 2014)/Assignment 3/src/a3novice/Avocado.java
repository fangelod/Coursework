package a3novice;

public class Avocado extends IngredientImpl {
	public Avocado(double amount) {
		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}
		else {
			super.amount = amount;
		}
		
		super.name = "avocado";
		super.price = 0.80;
	}
}

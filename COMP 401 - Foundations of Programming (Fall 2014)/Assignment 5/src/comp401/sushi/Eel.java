package comp401.sushi;

public class Eel extends IngredientImpl {
	public Eel(double amount) {
		super(amount, 1.25, false, false, false, "eel");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "eel";
		//super.price = 1.25;
	}
}

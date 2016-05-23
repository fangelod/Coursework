package comp401.sushi;

public class Shrimp extends IngredientImpl {
	public Shrimp(double amount) {
		super(amount, 1.15, false, true, false, "shrimp");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "shrimp";
		//super.price = 1.15;
	}
}

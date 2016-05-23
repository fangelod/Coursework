package comp401.sushi;

public class Crab extends IngredientImpl {
	public Crab(double amount) {
		super(amount, 1.5, false, true, false, "crab");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "crab";
		//super.price = 1.50;
	}
}

package comp401.sushi;

public class Seaweed extends IngredientImpl {
	public Seaweed(double amount) {
		super(amount, 0.40, false, false, true, "seaweed");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "seaweed";
		//super.price = 0.40;
	}
}

package comp401.sushi;

public class Tuna extends IngredientImpl {
	public Tuna(double amount) {
		super(amount, 0.65, false, false, false, "tuna");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "tuna";
		//super.price = 0.65;
	}
}

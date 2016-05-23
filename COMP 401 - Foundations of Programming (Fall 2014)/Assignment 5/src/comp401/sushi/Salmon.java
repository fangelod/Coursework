package comp401.sushi;

public class Salmon extends IngredientImpl {
	public Salmon(double amount) {
		super(amount, 0.75, false, false, false, "salmon");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "salmon";
		//super.price = 0.75;
	}
}

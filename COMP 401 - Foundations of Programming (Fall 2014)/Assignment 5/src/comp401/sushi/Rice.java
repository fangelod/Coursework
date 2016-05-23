package comp401.sushi;

public class Rice extends IngredientImpl {
	public Rice(double amount) {
		super(amount, 0.25, true, false, true, "rice");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "rice";
		//super.price = 0.25;
	}
}

package comp401.sushi;

public class Avocado extends IngredientImpl {
	public Avocado(double amount) {
		super(amount, 0.80, false, false, true, "avocado");
		
		//if (amount <= 0) {
		//	throw new RuntimeException("Invalid amount");
		//}
		//else {
		//	super.amount = amount;
		//}
		//
		//super.name = "avocado";
		//super.price = 0.80;
	}
}

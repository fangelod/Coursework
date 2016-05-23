package comp401.sushi;

public class GoldPlate extends PlateImpl {

	public GoldPlate(Sushi contents, double price) throws PlatePriceException {
		super(Plate.Color.GOLD, contents, price);
		
		if (price < 5.00) {
			throw new IllegalArgumentException("Gold plate price must be greater than or equal to 5.0");
		}
		
		//super.color = Plate.Color.GOLD;
		//
		//if (price < 5.0) {
		//	throw new IllegalArgumentException();
		//}
		//else {
		//	super.price = price;
		//}
		//
		//if (contents != null) {
		//	if (contents.getCost() > 1.0) {
		//		throw new PlatePriceException();
		//	}
		//	else {
		//		setContents(contents);
		//	}
		//}
	}
}

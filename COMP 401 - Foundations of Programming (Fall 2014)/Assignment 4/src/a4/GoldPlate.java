package a4;

public class GoldPlate extends PlateImpl {

	public GoldPlate(Sushi contents, double price) throws PlatePriceException {
		super.color = Plate.Color.GOLD;
		
		if (price < 5.0) {
			throw new IllegalArgumentException();
		}
		else {
			super.price = price;
		}
		
		if (contents != null) {
			if (contents.getCost() > 1.0) {
				throw new PlatePriceException();
			}
			else {
				setContents(contents);
			}
		}
	}
}

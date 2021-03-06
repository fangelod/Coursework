package a4;

public class BluePlate extends PlateImpl {

	public BluePlate(Sushi contents) throws PlatePriceException {
		super.color = Plate.Color.BLUE;
		super.price = 4.0;
		
		if (contents != null) {
			if (contents.getCost() > 4.0) {
				throw new PlatePriceException();
			}
			else {
				setContents(contents);
			}
		}
	}
}

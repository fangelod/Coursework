package a4;

public class RedPlate extends PlateImpl {

	public RedPlate(Sushi contents) throws PlatePriceException {
		super.color = Plate.Color.RED;
		super.price = 1.0;
		
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

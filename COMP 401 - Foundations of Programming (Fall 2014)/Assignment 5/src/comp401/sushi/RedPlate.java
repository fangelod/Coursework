package comp401.sushi;

public class RedPlate extends PlateImpl {

	public RedPlate(Sushi contents) throws PlatePriceException {
		super(Plate.Color.RED, contents, 1.0);
		
		//super.color = Plate.Color.RED;
		//super.price = 1.0;
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

package comp401.sushi;

public class GreenPlate extends PlateImpl {

	public GreenPlate(Sushi contents) throws PlatePriceException {
		super(Plate.Color.GREEN, contents, 2.0);
		
		//super.color = Plate.Color.GREEN;
		//super.price = 2.0;
		//
		//if (contents != null) {
		//	if (contents.getCost() > 2.0) {
		//		throw new PlatePriceException();
		//	}
		//	else {
		//		setContents(contents);
		//	}
		//}
	}
}

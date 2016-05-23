package comp401.sushi;

public class BluePlate extends PlateImpl {

	public BluePlate(Sushi contents) throws PlatePriceException {
		super(Plate.Color.BLUE, contents, 4.0);
		
		//super.color = Plate.Color.BLUE;
		//super.price = 4.0;
		//
		//if (contents != null) {
		//	if (contents.getCost() > 4.0) {
		//		throw new PlatePriceException();
		//	}
		//	else {
		//		setContents(contents);
		//	}
		//}
	}
}

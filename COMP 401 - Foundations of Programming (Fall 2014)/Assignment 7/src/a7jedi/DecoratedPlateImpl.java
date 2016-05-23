package a7jedi;

import comp401.sushi.Plate;
import comp401.sushi.PlatePriceException;
import comp401.sushi.Sushi;

public class DecoratedPlateImpl implements DecoratedPlate {
	private Plate wrapped_plate;
	private int age;
	
	public DecoratedPlateImpl(Plate plate_obj) {
		wrapped_plate = plate_obj;
	}

	@Override
	public Sushi getContents() {
		return wrapped_plate.getContents();
	}

	@Override
	public Sushi removeContents() {
		return wrapped_plate.removeContents();
	}

	@Override
	public void setContents(Sushi s) throws PlatePriceException {
		wrapped_plate.setContents(s);
	}

	@Override
	public boolean hasContents() {
		return wrapped_plate.hasContents();
	}

	@Override
	public double getPrice() {
		return wrapped_plate.getPrice();
	}

	@Override
	public Color getColor() {
		return wrapped_plate.getColor();
	}

	@Override
	public double getProfit() {
		return wrapped_plate.getProfit();
	}

	@Override
	public void setAgeOfPlate(int rotations) {
		age = rotations;
	}

	@Override
	public int getAgeOfPlate() {
		return age;
	}
}

package lec09.ex8.v6;

public class BlackBear extends BearImpl {

	BlackBear(int id, Point3D location) {
		super(id, location);
	}

	public String getColor() {
		return "black";
	}

}

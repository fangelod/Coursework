package lec09.ex8.v5;

abstract public class AnimalImpl implements Animal {

	private Point3D location;
	private int id;

	AnimalImpl(int id, Point3D location) {
		this.id = id;
		this.location = location;
	}
	
	public int getID() {
		return id;
	}
	
	public Point3D getLocation() {
		return location;
	}
	
	public void move(Point3D destination) {
		location = destination;
	}
	
	abstract public void speak();
}

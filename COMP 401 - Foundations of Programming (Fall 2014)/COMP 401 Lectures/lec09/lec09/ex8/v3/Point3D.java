package lec09.ex8.v3;

public class Point3D {

	private int x;
	private int y;
	private int z;
	
	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public double distanceTo(Point3D other) {
		return Math.sqrt((x-other.getX())*(x-other.getX())+
				         (y-other.getY())*(y-other.getY())+
				         (z-other.getZ())*(z-other.getZ()));
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
	}
}


public class Point {
	private int x;
	private int y;
	private int z;
	private int id;
	public Point(String x, String y, String z) {
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
		this.z = Integer.parseInt(z);
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
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
	@Override
	public String toString() {
		//return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
		return "("+ x + "," + y + "," + z + ")";
	}
	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;
		if(this.x == point.getX() && this.y == point.getY() && this.z == point.getZ()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
}

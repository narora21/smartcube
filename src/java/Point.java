// Author: Nikhil Arora

class Point{
	public int x;
	public int y;
	public int z;
	public Point(int _x, int _y, int _z){
		x = _x;
		y = _y;
		z = _z;
	}
	public boolean equals(Point other){
		return x == other.x && y == other.y && z == other.z;
	}
	public String toString(){
		return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ", " + Integer.toString(z) + ")";
	}
}
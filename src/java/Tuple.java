// Author: Nikhil Arora

class Tuple{
	public int color;
	public String face;
	public Tuple(int c, String f){
		color = c;
		face = f;
	}
	public Tuple(){}
	public String toString(){
		return "{color: " + Integer.toString(color) + ", face: " + face + "}";
	}
}
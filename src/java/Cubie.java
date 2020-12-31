// Author: Nikhil Arora
import java.util.*;

abstract class Cubie{
	String type;
	ArrayList<Tuple> sides;
	public Cubie(){
		sides = new ArrayList<Tuple>();
	}
	public String getType(){
		return type;
	}
	public int getNumSides(){
		return sides.size();
	}
	public boolean hasColor(int color){
		for(int i = 0; i < sides.size(); i++){
			if(sides.get(i).color == color)
				return true;
		}
		return false;
	}
	// checks color equality
	public boolean equals(Cubie other){
		if(other.getType().equals(type)){
			for(int i = 0; i < sides.size(); i++){
				if(!other.hasColor(sides.get(i).color))
					return false;
			}
			return true;
		}
		return false;
	}
	public String toString(){
		String s = type + ": ";
		for(int i = 0; i < sides.size(); i++){
			s += sides.get(i).toString();
		}
		return s;
	}
	abstract public Tuple setSide(int side, int color, String face);
	abstract public Tuple getSide(int side);
}
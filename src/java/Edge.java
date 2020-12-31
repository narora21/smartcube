// Author: Nikhil Arora
import java.util.*;

class Edge extends Cubie{
	public Edge(){
		type = "edge";
		initializeSides();
	}
	void initializeSides(){
		sides = new ArrayList<Tuple>();
		sides.add(new Tuple());
		sides.add(new Tuple());
	}
	// sets side, returns that side or null on failure
	public Tuple setSide(int side, int color, String face){
		if(side != 0 && side != 1)
			return null;
		sides.get(side).color = color;
		sides.get(side).face = face;
		return sides.get(side);
	}
	// rgets side, returns null on failure
	public Tuple getSide(int side){
		if(side != 0 && side != 1)
			return null;
		return sides.get(side);
	}
}

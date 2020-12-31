// Author: Nikhil Arora
import java.util.function.*;
import java.util.*;

// Performs Iterative Deepening A* search on the 3x3 cube
class IDAStar{
	// user supplied heuristic (hopefully admissible)
	Heuristic h;
	public IDAStar(Heuristic heuristic) throws Exception{
		if(heuristic == null){
			throw new Exception("Heuristic function cannot be null");
		}
		h = heuristic;
	}

	// converts array of twists into cubing notation
	public static String getNotation(ArrayList<Twist> twists){
		if(twists == null)
			return null;
		String moves = "";
		for(int i = 0; i < twists.size(); i++){
			Twist curr = twists.get(i);
			String m = "";
			switch(curr.face){
				case "front":
					m += "F";
					break;
				case "back":
					m += "B";
					break;
				case "left":
					m += "L";
					break;
				case "right":
					m += "R";
					break;
				case "upper":
					m += "U";
					break;
				case "down":
					m += "D";
					break;
				default:
					return null;
			}
			switch(curr.angle){
				case 90:
					m += "'";
					break;
				case -90:
					break;
				case 180:
					m += "2";
					break;
				default:
					return null;
			}
			moves += m;
		}
		return moves;
	}

	// returns best path or null on failure
	public ArrayList<Twist> doAStar(Cube root){
		Double bound = h.heuristic(root);
		Stack<Cube> path = new Stack<Cube>();
		path.push(root);
		Stack<Twist> twists = new Stack<Twist>();
		while(true){
			Double t = search(path, twists, 0.0, bound);
			if(t == null)
				break;
			if(t == Double.POSITIVE_INFINITY)
				return null;
			bound = t;
		}
		ArrayList<Twist> ret = new ArrayList<Twist>();
		while(!twists.empty()){
			ret.add(0, twists.pop());
		}
		return ret;
	}

	// searches for path, given a bound and current cost
	// returns null if found
	// returns infinity or a finite bound otherwise
	Double search(Stack<Cube> path, Stack<Twist> twists, Double g, Double bound){
		Cube node = path.peek();
		Double f = g + h.heuristic(node);
		if(f > bound)
			return f;
		if(node.solved())
			return null;
		Double min = Double.POSITIVE_INFINITY;
		ArrayList<Transition> successors = getSuccessors(node, twists);
		for(int i = 0; i < successors.size(); i++){
			Cube succ_node = successors.get(i).child;
			Twist succ_twist = successors.get(i).twist;
			if(!path.contains(succ_node)){
				path.push(succ_node);
				twists.push(succ_twist);
				Double t = search(path, twists, g + 1, bound);
				if(t == null)
					return null;
				if(t < min)
					min = t;
				path.pop();
				twists.pop();
			}
		}
		return min;
	}

	// generates successors of a cube state
	ArrayList<Transition> getSuccessors(Cube node, Stack<Twist> twists){
		// prune of moves that involve face that was just twisted
		String last_face_turned = twists.peek().face;
		ArrayList<String> faces = pruneTwists(last_face_turned);
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		for(int i = 0; i < faces.size(); i++){
			Cube c1 = new Cube(node);
			Twist t1 = new Twist(faces.get(i), -90);
			c1.twist(faces.get(i), -90);
			transitions.add(new Transition(c1, t1));
			Cube c2 = new Cube(node);
			Twist t2 = new Twist(faces.get(i), 90);
			c2.twist(faces.get(i), 90);
			transitions.add(new Transition(c2, t2));
			Cube c3 = new Cube(node);
			Twist t3 = new Twist(faces.get(i), 180);
			c3.twist(faces.get(i), 180);
			transitions.add(new Transition(c3, t3));
		}
		return null;
	}

	// performs advanced move pruning
	// if half of first half of faces were turned, prune them
	// if second half were turned, prune them and opposite face
	ArrayList<String> pruneTwists(String previous){
		String[] f = {"front", "left", "upper", "back", "right", "down"};
		ArrayList<String> faces = new ArrayList<String>(Arrays.asList(f));
		faces.remove(previous);
		if(previous.equals("front"))
			faces.remove("back");
		if(previous.equals("left"))
			faces.remove("right");
		if(previous.equals("upper"))
			faces.remove("down");
		return faces;
	}
}

class Transition{
	public Cube child;
	public Twist twist;
	public Transition(Cube c, Twist t){
		child = c;
		twist = t;
	}
}


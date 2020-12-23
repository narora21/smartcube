import java.util.function.*;
import java.util.*;

class Main{
	public static void main(String[] args){
		// get the input cube
		Cube c = new Cube();
		c.twist("front", -90);

		// choose a heuristic
		Function<Cube, Double> h = cube -> Manhattan1.heuristic(cube);

		// compute solution
		Stack<Twist> solution = new Stack<Twist>();
		try{
			solution = new IDAStar(h).doAStar(c);
		}
		catch(Exception e){
			System.out.println(e);
			System.exit(1);
		}

		// print solution
		ArrayList<String> move_list = IDAStar.getNotation(solution);
		String moves = "";
		for(int i = 0; i < move_list.size(); i++){
			moves += move_list.get(i);
		}
		System.out.println(moves);

		System.exit(0);
	}
}
import java.util.*;
import java.util.function.*;

class Test{
	public static void main(String[] args){

		// TEST TWISTING WITH SCRAMBLE AND UNSCRAMBLE
		scramble_test();		
		
		// TEST UNSOLVABILITY CHECKER
		unsolvable_test();

		// TEST POINT AND CUBIE TWISTING FUNCTIONALITY
		cubie_and_point_test();

		// TEST IDA* SOLVER WITH MANHATTAN1 HEURISTIC
		manhattan1_solve_test();

		// TEST IDA* SOLVER WITH MANHATTAN2 HEURISTIC

		// TEST IDA* SOLVER WITH PDB HEURISTIC
		
		System.out.println("All tests passed!");
	}

	// randomly scrambles a cube and returns array list of inverse operations
	static ArrayList<Twist> scramble(Cube c, int n){
		String[] faces = {"front", "back", "left", "right", "upper", "down"};
		int[] angles = {90, -90, 180};
		ArrayList<Twist> inverse = new ArrayList<Twist>();
		do{
			inverse.clear();
			for(int i = 0; i < n; i++){
				String face = faces[(int)(Math.random() * 6)];
				int angle = angles[(int)(Math.random() * 3)];
				int rev_angle = angle == 90 ? -90 : (angle == -90 ? 90 : angle);
				inverse.add(0, new Twist(face, rev_angle));
				c.twist(face, angle);
			}
		}while(c.solved());
		return inverse;
	}

	// TEST TWISTING WITH SCRAMBLE AND UNSCRAMBLE
	static void scramble_test(){
		Cube c = new Cube();
		ArrayList<Twist> sol = scramble(c, 20);
		assert !c.solved() : "Scramble error";
		assert sol.size() == 20 : "Unscramble error";
		for(int i = 0; i < sol.size(); i++){
			c.twist(sol.get(i));
		}
		assert c.solved() : "Scramble test: Error when scrambling/unscrambling";
	}

	// TEST UNSOLVABILITY CHECKER
	static void unsolvable_test(){
		Cube c = new Cube();
		// TO DO: IMPLEMENT THIS
	}

	// TEST POINT AND CUBIE TWISTING FUNCTIONALITY
	static void cubie_and_point_test(){
		Cube c = new Cube();
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				for(int z = 0; z < 3; z++){
					Point p = new Point(x,y,z);
					assert c.cubieSolved(p) : "Cubie test: Cubie goal test failed";
				}
			}
		}
		scramble(c, 20);
		String[] faces = {"front", "back", "left", "right", "upper", "down"};
		int[] angles = {90, -90, 180};
		for(int i = 0; i < angles.length; i++){
			for(int j = 0; j < faces.length; j++){
				for(int x = 0; x < 3; x++){
					for(int y = 0; y < 3; y++){
						for(int z = 0; z < 3; z++){
							if(x == 1 && y == 1 && z == 1)
								continue;
							Point p = new Point(x,y,z);
							Cubie cubie = c.getCubieValue(p);
							Twist t = new Twist(faces[j], angles[i]);
							c.twist(t);
							Point q = Cube.pointAfterTwist(p, t);
							assert c.findCubieLocation(cubie).equals(q): "Cubie test: Location test after twist failed";
							assert c.getCubieValue(q).equals(cubie): "Cubie test: Value test after twist failed";
						}
					}
				}
			}
		}
	}

	// TEST SOLVER WITH MANHATTAN1 HEURISTIC
	static void manhattan1_solve_test(){
		Cube c = new Cube();
		scramble(c, 20);

		Cube cpy = new Cube(c);
		ArrayList<Twist> solution = null;
		try{
			solution = new IDAStar(new Manhattan1()).doAStar(c);
		}
		catch(Exception e){
			System.out.println(e);
		}
		assert solution != null : "Manhattan1 test: Solution is null";
		//System.out.println("Reported solution: " + IDAStar.getNotation(solution));
		assert cpy.equals(c): "Manhattan1 test: Cube state was changed";
		assert c.solve(solution) : "Manhattan1 test: Solver with Manhattan1 is incorrect";
	}

	// TEST SOLVER WITH MANHATTAN2 HEURISTIC
	static void manhattan2_solve_test(){
		Cube c = new Cube();
		scramble(c, 20);

		Cube cpy = new Cube(c);
		ArrayList<Twist> solution = null;
		try{
			solution = new IDAStar(new Manhattan2()).doAStar(c);
		}
		catch(Exception e){
			System.out.println(e);
		}
		assert solution != null : "Manhattan2 test: Solution is null";
		//System.out.println("Reported solution: " + IDAStar.getNotation(solution));
		assert cpy.equals(c): "Manhattan2 test: Cube state was changed";
		assert c.solve(solution) : "Manhattan2 test: Solver with Manhattan2 is incorrect";
	}

	// TEST SOLVER WITH PDB HEURISTIC
	static void pdb_solve_test(){
		// TO DO: IMPLEMENT THIS
	}

}
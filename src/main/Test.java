import java.util.ArrayList;
import java.util.function.*;

class Test{
	public static void main(String[] args){
		/*
		ArrayList<Integer> row1 = new ArrayList<Integer>();
		ArrayList<Integer> row2 = new ArrayList<Integer>();
		ArrayList<Integer> row3 = new ArrayList<Integer>();
		row1.add(1); row1.add(3); row1.add(2);
		row2.add(1); row2.add(6); row2.add(2);
		row3.add(3); row3.add(5); row3.add(1);
		ArrayList<ArrayList<Integer>> colors = new ArrayList<ArrayList<Integer>>();
		colors.add(row1); colors.add(row2); colors.add(row3);
		Face f = null;
		try{
			f = new Face(colors);
		}
		catch(Exception e){
			System.out.println("Failed to construct face: " + e);
		}
		f.printFace();
		System.out.println("rotating clockwise");
		f.rotateFace(true);
		f.printFace();
		System.out.println("rotating counter-clockwise");
		f.rotateFace(false);
		f.printFace();
		*/

		// TEST 1: TEST TWIST FUNCTIONALITY
		Cube c = new Cube();
		// scramble
		c.twist("front", 90);
		c.twist("back", -90);
		c.twist("left", 90);
		c.twist("right", 180);
		c.twist("down", 90);
		c.twist("upper", -90);
		c.twist("right", -90);
		c.twist("upper", 90);
		c.twist("left", 90);
		c.twist("down", 180);
		c.twist("back", 90);
		c.twist("front", -90);
		c.twist("right", 90);
		c.twist("upper", 90);
		c.twist("left", -90);
		c.twist("back", 180);
		c.twist("down", 180);

		assert !c.solved() : "Solved Function Test Failed";

		// unscramble
		c.twist("down", 180);
		c.twist("back", 180);
		c.twist("left", 90);
		c.twist("upper", -90);
		c.twist("right", -90);
		c.twist("front", 90);
		c.twist("back", -90);
		c.twist("down", 180);
		c.twist("left", -90);
		c.twist("upper", -90);
		c.twist("right", 90);
		c.twist("upper", 90);
		c.twist("down", -90);
		c.twist("right", 180);
		c.twist("left", -90);
		c.twist("back", 90);
		c.twist("front", -90);

		assert c.solved() : "Unscramble Test Failed";
		
		// TEST 2: TEST UNSOLVABILITY CHECKER
		c = new Cube();

		// TEST 3: TEST IDA WITH MANHATTAN1
		Function<Cube, Double> h = cube -> Manhattan1.heuristic(cube);
		c = new Cube();
		c.twist("front", 90);
		c.twist("back", -90);
		c.twist("left", 90);
		c.twist("right", 180);
		c.twist("down", 90);
		c.twist("upper", -90);
		c.twist("right", -90);
		c.twist("upper", 90);
		c.twist("left", 90);
		c.twist("down", 180);
		c.twist("back", 90);
		c.twist("front", -90);
		c.twist("right", 90);
		c.twist("upper", 90);
		c.twist("left", -90);
		c.twist("back", 180);
		c.twist("down", 180);
		c.twist("right", 90);
		c.twist("upper", 180);
		c.twist("down", -90);
		Cube cpy = new Cube(c);
		Stack<Twist> solution = new Stack<Twist>();
		try{
			solution = new IDAStar(h).doAStar(c);
		}
		catch(Exception e){
			System.out.println(e);
		}
		assert cpy.equals(c);
		assert c.solve(solution) : "Manhattan1 solution incorrect";

		// TEST 4: TEST IDA WITH MANHATTAN2

		// TEST 5: TEST IDA WITH PDB
		
		System.out.println("All tests passed!");
	}
}
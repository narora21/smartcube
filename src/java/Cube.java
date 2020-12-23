// Author: Nikhil Arora

import java.util.*;

/* Represents a 3x3 face of a cube */
class Face{
	ArrayList<ArrayList<Integer>> face;
	// constructs face of one color
	public Face(int color){
		face = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 3; i++){
			face.add(new ArrayList<Integer>());
			for(int j = 0; j < 3; j++)
				face.get(i).add(color);
		}
	}
	// constructs face given a 3x3 grid of colors
	public Face(ArrayList<ArrayList<Integer>> colors) throws Exception{
		if(colors.size() != 3 || colors.get(0).size() != 3){
			throw new Exception("Input should be of dimensions: 3x3");
		}
		ArrayList<Integer> row1 = new ArrayList<Integer>(colors.get(0));
		ArrayList<Integer> row2 = new ArrayList<Integer>(colors.get(1));
		ArrayList<Integer> row3 = new ArrayList<Integer>(colors.get(2));
		face = new ArrayList<ArrayList<Integer>>();
		face.add(row1); face.add(row2); face.add(row3);
	}

	// gets the 3x3 grid of colors
	public ArrayList<ArrayList<Integer>> getFace(){
		return face;
	}

	// gets nth row on the face, null on failure
	public ArrayList<Integer> getRow(int n){
		if(n < 0 || n > 2){
			return null;
		}
		ArrayList<Integer> row = new ArrayList<Integer>(face.get(n));
		return row;
	}

	// gets nth column on the face, null on failure
	public ArrayList<Integer> getCol(int n){
		if(n < 0 || n > 2){
			return null;
		}
		ArrayList<Integer> col = new ArrayList<Integer>();
		col.add(face.get(0).get(n));
		col.add(face.get(1).get(n));
		col.add(face.get(2).get(n));
		return col;
	}
	// sets nth row on the face (returns -1 on failure)
	public int setRow(int n, ArrayList<Integer> row){
		if(n < 0 || n > 2 || row.size() != 3){
			return -1;
		}
		face.get(n).set(0, row.get(0));
		face.get(n).set(1, row.get(1));
		face.get(n).set(2, row.get(2));
		return 0;
	}

	// sets nth column on the face (returns -1 on failure)
	public int setCol(int n, ArrayList<Integer> col){
		if(n < 0 || n > 2 || col.size() != 3){
			return -1;
		}
		face.get(0).set(n, col.get(0));
		face.get(1).set(n, col.get(1));
		face.get(2).set(n, col.get(2));
		return 0;
	}

	// rotates the face in the direction specified
	public void rotateFace(boolean clockwise){
		ArrayList<Integer> upperrow = getRow(0);
		ArrayList<Integer> rightcol = getCol(2);
		ArrayList<Integer> botrow = getRow(2);
		ArrayList<Integer> leftcol = getCol(0);
		if(clockwise){
			Collections.reverse(rightcol);
			Collections.reverse(leftcol);
			setCol(0, botrow);
			setRow(0, leftcol);
			setCol(2, upperrow);
			setRow(2, rightcol);
		}
		else{
			Collections.reverse(upperrow);
			Collections.reverse(botrow);
			setRow(2, leftcol);
			setCol(2, botrow);
			setRow(0, rightcol);
			setCol(0, upperrow);
		}
	}

	// if the face is one color, return the color, else return -1
	public int oneColor(){
		int color = face.get(1).get(1); // middle color
		for(int i = 0; i < face.size(); i++){
			for(int j = 0; j < face.get(i).size(); j++){
				if(face.get(i).get(j) != color)
					return -1;
			}
		}
		return color;
	}

	// compares two faces, color by color
	public boolean equals(Face other){
		ArrayList<Integer> row1 = face.get(0);
		ArrayList<Integer> row2 = face.get(1);
		ArrayList<Integer> row3 = face.get(2);
		ArrayList<Integer> other_row1 = other.getFace().get(0);
		ArrayList<Integer> other_row2 = other.getFace().get(1);
		ArrayList<Integer> other_row3 = other.getFace().get(2);
		for(int i = 0; i < 3; i++)
			if(row1.get(i) != other_row1.get(i) 
				|| row2.get(i) != other_row2.get(i) 
				|| row3.get(i) != other_row3.get(i))
				return false;
		return true;
	}

	// prints the face's 3x3 color grid
	public void printFace(){
		String row1 = "[" + face.get(0).get(0) + "][" + face.get(0).get(1) + "][" + face.get(0).get(2) + "]";
		String row2 = "[" + face.get(1).get(0) + "][" + face.get(1).get(1) + "][" + face.get(1).get(2) + "]";
		String row3 = "[" + face.get(2).get(0) + "][" + face.get(2).get(1) + "][" + face.get(2).get(2) + "]";
		System.out.println(row1);
		System.out.println(row2);
		System.out.println(row3);
	}
}

/* Represents a 3x3 puzzle */
class Cube{
	Face front;
	Face back;
	Face left;
	Face right;
	Face upper;
	Face down;
	// create a solved cube
	public Cube(){
		front = new Face(1);
		back = new Face(2);
		left = new Face(3);
		right = new Face(4);
		upper = new Face(5);
		down = new Face(6);
	}

	// initialize cube with certain permuatation
	public Cube(Face front_f, Face back_f, Face left_f, Face right_f, Face upper_f, Face down_f){
		try{
			front = new Face(front_f.getFace());
			back = new Face(back_f.getFace());
			left = new Face(left_f.getFace());
			right = new Face(right_f.getFace());
			upper = new Face(upper_f.getFace());
			down = new Face(down_f.getFace());
		}
		catch(Exception e){
			front = new Face(1);
			back = new Face(2);
			left = new Face(3);
			right = new Face(4);
			upper = new Face(5);
			down = new Face(6);;
		}
	}

	// copy constructor
	public Cube(Cube other){
		try{
			front = new Face(other.getFace("front").getFace());
			back = new Face(other.getFace("back").getFace());
			left = new Face(other.getFace("left").getFace());
			right = new Face(other.getFace("right").getFace());
			upper = new Face(other.getFace("upper").getFace());
			down = new Face(other.getFace("down").getFace());
		}
		catch(Exception e){
			front = new Face(1);
			back = new Face(2);
			left = new Face(3);
			right = new Face(4);
			upper = new Face(5);
			down = new Face(6);;
		}
	}

	// gets a given face, null on failure
	public Face getFace(String face){
		switch(face){
			case "front":
				return front;
			case "back":
				return back;
			case "left":
				return left;
			case "right":
				return right;
			case "upper":
				return upper;
			case "down":
				return down;
			default:
				return null;
		}
	}

	// checks if the cube permutation is not valid
	boolean invalidPerm(){
		// check that there are 9 of each color
		ArrayList<Integer> colors = new ArrayList<Integer>();
		for(int i = 1; i <= 6; i++)
			colors.add(0);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				int color = front.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
				color = back.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
				color = left.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
				color = right.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
				color = upper.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
				color = down.getFace().get(i).get(j);
				colors.set(color, colors.get(color) + 1);
			}
		}
		for(int i = 1; i <= 6; i++){
			if(colors.get(i) != 9)
				return true;
		}

		// check permutation parity
		// TO DO 
		// check corner parity
		// TO DO
		// check edge parity
		// TO DO
		return false;
	}

	// checks if the cube is solved
	public boolean solved(){
		ArrayList<Integer> colors = new ArrayList<Integer>();
		int i = front.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		i = back.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		i = left.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		i = right.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		i = upper.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		i = down.oneColor();
		if(i == -1 || colors.contains(i))
			return false;
		colors.add(i);
		return true;
	}

	// twist a face returns -1 on failure
	public int twist(Twist t){
		return twist(t.face, t.angle);
	}

	// twist a face, returns -1 on failure
	public int twist(String face, int angle){
		// face should denote which face on the cube to twist
		// angle should denote how much to twist it (90, -90, 180)
		// 90 degrees: anticlockwise (prime)
		// -90 degrees: clockwise (non-prime)
		boolean clockwise = true;
		int times = 1;
		switch(angle){
			case 90:
				clockwise = false;
				times = 1;
				break;
			case -90:
				clockwise = true;
				times = 1;
				break;
			case 180:
				times = 2;
				break;
			default:
				return -1;
		}
		// twist the face and twist the cols/rows of adjacent faces
		ArrayList<Integer> adj1;
		ArrayList<Integer> adj2;
		ArrayList<Integer> adj3;
		ArrayList<Integer> adj4;
		for(int i = 0; i < times; i++){
			switch(face){
				case "front":
					front.rotateFace(clockwise);
					adj1 = upper.getRow(2);
					adj2 = right.getCol(0);
					adj3 = down.getRow(0);
					adj4 = left.getCol(2);
					if(clockwise){
						Collections.reverse(adj4);
						Collections.reverse(adj2);
						upper.setRow(2, adj4);
						right.setCol(0, adj1);
						down.setRow(0, adj2);
						left.setCol(2, adj3);
					}
					else{
						Collections.reverse(adj1);
						Collections.reverse(adj3);
						upper.setRow(2, adj2);
						right.setCol(0, adj3);
						down.setRow(0, adj4);
						left.setCol(2, adj1);
					}
					break;
				case "back":
					back.rotateFace(clockwise);
					adj1 = upper.getRow(0);
					adj2 = right.getCol(2);
					adj3 = left.getCol(0);
					adj4 = down.getRow(2);
					if(clockwise){
						Collections.reverse(adj1);
						Collections.reverse(adj4);
						upper.setRow(0, adj2);
						right.setCol(2, adj4);
						left.setCol(0, adj1);
						down.setRow(2, adj3);
					}
					else{
						Collections.reverse(adj2);
						Collections.reverse(adj3);
						upper.setRow(0, adj3);
						right.setCol(2, adj1);
						left.setCol(0, adj4);
						down.setRow(2, adj2);
					}
					break;
				case "left":
					left.rotateFace(clockwise);
					adj1 = front.getCol(0);
					adj2 = upper.getCol(0);
					adj3 = back.getCol(2);
					adj4 = down.getCol(0);
					if(clockwise){
						Collections.reverse(adj3);
						Collections.reverse(adj4);
						front.setCol(0, adj2);
						upper.setCol(0, adj3);
						back.setCol(2, adj4);
						down.setCol(0, adj1);
					}
					else{
						Collections.reverse(adj2);
						Collections.reverse(adj3);
						front.setCol(0, adj4);
						upper.setCol(0, adj1);
						back.setCol(2, adj2);
						down.setCol(0, adj3);
					}
					break;
				case "right":
					right.rotateFace(clockwise);
					adj1 = front.getCol(2);
					adj2 = upper.getCol(2);
					adj3 = back.getCol(0);
					adj4 = down.getCol(2);
					if(clockwise){
						Collections.reverse(adj2);
						Collections.reverse(adj3);
						front.setCol(2, adj4);
						upper.setCol(2, adj1);
						back.setCol(0, adj2);
						down.setCol(2, adj3);
					}
					else{
						Collections.reverse(adj4);
						Collections.reverse(adj3);
						front.setCol(2, adj2);
						upper.setCol(2, adj3);
						back.setCol(0, adj4);
						down.setCol(2, adj1);
					}
					break;
				case "upper":
					upper.rotateFace(clockwise);
					adj1 = front.getRow(0);
					adj2 = right.getRow(0);
					adj3 = back.getRow(0);
					adj4 = left.getRow(0);
					if(clockwise){
						front.setRow(0, adj2);
						right.setRow(0, adj3);
						back.setRow(0, adj4);
						left.setRow(0, adj1);
					}
					else{
						front.setRow(0, adj4);
						right.setRow(0, adj1);
						back.setRow(0, adj2);
						left.setRow(0, adj3);
					}
					break;
				case "down":
					down.rotateFace(clockwise);
					adj1 = front.getRow(2);
					adj2 = right.getRow(2);
					adj3 = back.getRow(2);
					adj4 = left.getRow(2);
					if(clockwise){
						front.setRow(2, adj4);
						right.setRow(2, adj1);
						back.setRow(2, adj2);
						left.setRow(2, adj3);						
					}
					else{
						front.setRow(2, adj2);
						right.setRow(2, adj3);
						back.setRow(2, adj4);
						left.setRow(2, adj1);
					}
					break;
				default:
					return -1;
			}
		}
		// twist the cols/rows of adjacent faces
		return 0;
	}

	// attempts to solve cube with IDA* solution
	public boolean solve(Stack<Twist> solution){
		Stack<Twist> inverted = new Stack<Twist>();
		while(!solution.empty()){
			inverted.push(solution.pop());
		}
		while(!inverted.empty()){
			Twist t = inverted.pop();
			twist(t.face, t.angle);
		}
		return solved();
	}

	// compares two cubes face by face
	public boolean equals(Cube other){
		return front.equals(other.getFace("front"))
			&& back.equals(other.getFace("back"))
			&& left.equals(other.getFace("left"))
			&& right.equals(other.getFace("right"))
			&& upper.equals(other.getFace("upper"))
			&& down.equals(other.getFace("down"));
	}

	// prints out state of a face
	public void printFace(String face){
		Face curr;
		if(face == "front")
			curr = front;
		else if(face == "back")
			curr = back;
		else if(face == "left")
			curr = left;
		else if(face == "right")
			curr = right;
		else if(face == "upper")
			curr = upper;
		else if(face == "down")
			curr = down;
		else{
			System.out.println("No such face: " + face);
			return;
		}
		curr.printFace();
	}

	// prints out state of cube
	public void printCube(){
		System.out.println("Front Face:");
		printFace("front");
		System.out.println("Back Face:");
		printFace("back");
		System.out.println("Left Face:");
		printFace("left");
		System.out.println("Right Face:");
		printFace("right");
		System.out.println("Upper Face:");
		printFace("upper");
		System.out.println("Bottom Face:");
		printFace("down");	
	}
}

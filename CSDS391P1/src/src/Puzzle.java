package src;
import java.util.*;

public class Puzzle {
	private char[][] puzzle = new char[3][3];
	private char[][] goal = {{'b','1','2'},{'3','4','5'},{'6','7','8' }};
	private int[] blank = new int[2];
	
	Puzzle(Puzzle puzzle) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				this.puzzle[row][col] = puzzle.puzzle[row][col];
			}
		}
		this.blank = this.getCoord(puzzle.puzzle, 'b');
	}
	
	Puzzle(char[][] puzzle) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				this.puzzle[row][col] = puzzle[row][col];
			}
		}
		this.blank = getCoord(puzzle, 'b');
	}
	
	
	
	protected void move(String direction) {
		
		int row = this.blank[0];
		int col = this.blank[1];
		
		if (direction.equals("down")) {
			row++;
		} else if (direction.equals("up")) {
			row--;
		}
		if (direction.equals("left")) {
			col--;
		}
		if (direction.equals("right")) {
			col++;
		}
		if (validMove(row, col)) {
			swap(this.blank[0], this.blank[1], row, col);
		}
	}
	
	private void swap(int r1, int c1, int r2, int c2) {
		char temp = this.puzzle[r1][c1];
		this.puzzle[r1][c1] = this.puzzle[r2][c2];
		this.puzzle[r2][c2] = temp;
		blank = getCoord(this.puzzle, 'b');
	}
	
	protected void randomizeState(int n) {
		int count = 0;
		Random r = new Random();
		r.setSeed(20);
		while (count < n) {
			int row = blank[0];
			int col = blank[1];
			switch (r.nextInt(4)) {
			case 1: {
				col = col - 1;
				break;
			}
			case 2: {
				col = col + 1; 
				break;
			}
			case 3: {
				row = row - 1; 
				break;
			}
			case 0: {
				row = row + 1; 
				break;
			}
			}
			
			if (validMove(row, col)) {
				swap(blank[0], blank[1], row, col);
				count++;
			}
			
		}
		
	}
	
	private boolean validMove(int row, int col) {
		return row >= 0 && row <= 2 && col >= 0 && col <=2;
	}
	
	protected Iterable<Puzzle> neighbors() {
		int row = getCoord(this.puzzle, 'b')[0];
		int col = getCoord(this.puzzle, 'b')[1];
		ArrayList<Puzzle> neighbors= new ArrayList<>();
		if (validMove(row - 1, col)) {
			Puzzle temp = new Puzzle(this);
			temp.move("up");
			neighbors.add(temp);
		}
		if (validMove(row + 1, col)) {
			Puzzle temp = new Puzzle(this);
			temp.move("down");
			neighbors.add(temp);
		}
		if (validMove(row, col + 1)) {
			Puzzle temp = new Puzzle(this);
			temp.move("right");
			neighbors.add(temp);
		}
		if (validMove(row, col - 1)) {
			Puzzle temp = new Puzzle(this);		
			temp.move("left");
			neighbors.add(temp);
		}
		
		return neighbors;
	}
	
	private int[] getCoord(char[][] state, char search) {
		int[] coord = new int[2];
		for (int row = 0; row < 3; row ++) {
			for (int col = 0; col < 3; col++) {
				if (state[row][col] == search) {
					coord[0] = row;
					coord[1] = col;
				}
			}
		}
		return coord;
	}
	
	protected int[] getCoord(char search) {
		return getCoord(this.puzzle, search);
	}
	
	
	protected int hamming() {
		int count = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (puzzle[row][col] == 'b') continue;
				int goalrow = getCoord(goal, puzzle[row][col])[0];
				int goalcol = getCoord(goal, puzzle[row][col])[1];
				if (goalrow != row || goalcol != col) count++;
			}
		}
		return count;
	}
	
	protected int manhattan() {
		int count = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (puzzle[row][col] == 'b') continue;
				int goalrow = getCoord(goal, puzzle[row][col])[0];
				int goalcol = getCoord(goal, puzzle[row][col])[1];
				count += Math.abs(goalrow-row) + Math.abs(goalcol-col);
			}
		}
		return count;
	}
	
	protected boolean atGoal() {
		return (manhattan() == 0);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean ret = true;
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) {
			return false;
		}
		Puzzle other1 = (Puzzle) other;
		for (int row = 0; row < 3; row++ ) {
			for (int col = 0; col < 3; col++) {
				if (this.puzzle[row][col] != other1.puzzle[row][col]) {
					ret = false;
				}
			}
		}
		return ret;
	}
	protected void printState() {
		String out = "";
		for (int row = 0; row < 3; row++ ) {
			out += Arrays.toString(this.puzzle[row]) + "\n";
		}
		System.out.print(out);
	}
	
	@Override
    public int hashCode() {
		String ret = "";
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    ret += puzzle[row][col];
                }
            }

        return ret.hashCode();
    }
}

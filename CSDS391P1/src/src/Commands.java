package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Commands {
	Puzzle puzzle;
	int maxnodes;
	
	Commands() {
		puzzle = null;
		maxnodes = 100000;
				
	}
	
	public void fileInput(File file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			scanner.useDelimiter("\n");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().replaceAll("\n", "");
				line = line.replaceAll("<", "");
				line = line.replaceAll(">", "");
	                if (line.length() == 0) continue;

	                // split line into arguments
	                String[] args = line.split(" ");

	                processArgs(args);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void maxNodes(int maxnodes) {
		this.maxnodes = maxnodes;
		System.out.println("Max nodes is " + maxnodes);
	}
	
	private void move(String dir) {
		if (puzzle != null) {
			puzzle.move(dir);
			 System.out.println("Moved " + dir);
		}
	}
	
	private void solveAStar(String heuristic) {
		if (puzzle!= null) {
			AStarSearch astar = new AStarSearch(this.puzzle, maxnodes, heuristic);
			astar.solver();
			System.out.println("Astar: ");
            System.out.println("Heuristic: " + heuristic);
            System.out.println("Number of moves: " + astar.moves());
            System.out.println("Explored nodes: " + astar.getExploredNodes());
            for (String dir : astar.solution()) {
            	System.out.println(dir);
            }
		} 	
	}
	
	private void solveBeam(int width) {
        if (puzzle != null) {
        	this.puzzle.printState();
        	System.out.print("");
            BeamSearch beam = new BeamSearch(this.puzzle, maxnodes, width);
            beam.solver();
            System.out.println("Beam: ");
            System.out.println("Beam width: " + width);
            System.out.println("Number of moves: " + beam.moves());
            System.out.println("Explored nodes: " + beam.getExploredNodes());
            for (String dir : beam.solution()) {
            	System.out.println(dir);
            }
        } 
    }
	
	private void printState() {
        if (puzzle != null) {
            System.out.println("State: ");
            puzzle.printState();
        } 
    } 
	
	public void setState(String[] rows) {
		char[][] init = new char[3][3];
		for (int row = 0; row < 3; row ++) {
			for (int col = 0; col < 3; col ++) {
				init[row][col] = rows[row].charAt(col);
		 	}
		 }
	     this.puzzle = new Puzzle(init);
	     System.out.println("State is set to: ");
	     puzzle.printState();
	}
	
	
	private void randomizeState(int moves) {
		if (puzzle != null) {
			puzzle.randomizeState(moves);
			System.out.println("Puzzle had " + moves + " random moves");
		}
	}
	
	public void processArgs(String[] args) {
			if (args[0].equalsIgnoreCase("printstate")) printState();

			else if (args[0].equalsIgnoreCase("randomizestate")) {
				try {
					randomizeState(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} else if (args[0].equalsIgnoreCase("move")) move(args[1]);
			else if (args[0].equalsIgnoreCase("maxnodes")) {
				try {
					maxNodes(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} 	
			else if (args[0].equalsIgnoreCase("solve")) {
				if (args[1].equalsIgnoreCase("A-star")) {
					solveAStar(args[2]);
				} else if (args[1].equalsIgnoreCase("beam")) {
					try {
						solveBeam(Integer.parseInt(args[2]));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
			else if (args[0].equalsIgnoreCase("setstate")) {
				String[] rows = new String[3];
				System.arraycopy(args, 1, rows, 0, 3);
				setState(rows);
			}

	}
	
	
}

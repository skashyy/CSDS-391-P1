package src;

import java.util.*;

public abstract class GeneralSearch {

	protected abstract void solver();

	protected static class NodeSearch implements Comparable<NodeSearch> {
		protected Puzzle puzzle;
		protected int heuristic;
		protected NodeSearch prevnode;
		protected static String heuristictype = "h2";
		protected int moves = 0;
		protected int priority;

		public NodeSearch(Puzzle puzzle, NodeSearch prevnode) {
			Objects.requireNonNull(puzzle);
			this.puzzle = puzzle;

			if (prevnode != null) {
				this.moves = prevnode.moves + 1;
				this.prevnode = prevnode;
			}

			if (NodeSearch.heuristictype.equals("h1")) {
				this.heuristic = puzzle.hamming();
			} else
				this.heuristic = puzzle.manhattan();

			priority();
		}

		public void priority() {
			this.priority = this.moves + this.heuristic;
		}

		public static void setTypeHeuristic(String heuristictype) {
			NodeSearch.heuristictype = heuristictype;
		}

		@Override
		public int compareTo(NodeSearch node) {
			if (node.priority > this.priority)
				return -1;
			else if (node.priority < this.priority)
				return 1;
			else
				return 0;
		}
	}

	protected NodeSearch current;
	protected Puzzle init;
	protected int maxNodes;
	protected int exploredNodes;

	protected boolean solveable = false;
	

	public GeneralSearch(Puzzle init, int maxnodes) {
		this.init = init;
		this.maxNodes = maxnodes;
	}

	public NodeSearch getCurrent() {
		return this.current;
	}

	public boolean solveable() {
		return solveable;
	}

	public int moves() {
		if (solveable()) {
			return getCurrent().moves;
		} else
			return -1;
	}

	public int getExploredNodes() {
		return exploredNodes;
	}

	public Iterable<String> solution() {
		List<String> movelist= new ArrayList<>();
		this.solver();
		NodeSearch temp = new NodeSearch(getCurrent().puzzle, getCurrent().prevnode);

		if (solveable()) {
			while (temp.prevnode != null) {
				int[] current = temp.puzzle.getCoord('b');
				int[] prev = temp.prevnode.puzzle.getCoord('b');
				if (current[0] == prev[0] + 1) {
					movelist.add("right");
				}
				else if (current[0] == prev[0] - 1) {
					movelist.add("left");
				}
				else if (current[1] == prev[1] + 1) {
					movelist.add("down");
				}
				else movelist.add("up");
				temp = temp.prevnode;
			}
			Collections.reverse(movelist);
			
			return movelist;
		} else return null;
	}
}
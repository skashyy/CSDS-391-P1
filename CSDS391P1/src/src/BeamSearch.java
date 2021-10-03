package src;
import java.util.*;
public class BeamSearch extends GeneralSearch {
	private int width;
	
	static class NodeSearch extends GeneralSearch.NodeSearch {
		
		public NodeSearch(Puzzle puzzle, BeamSearch.NodeSearch prevNode) {
			super(puzzle, prevNode);
		}
		
		@Override
		public void priority() {
			this.priority = this.heuristic;
		}
		
		@Override
		public int compareTo(GeneralSearch.NodeSearch node) {
			BeamSearch.NodeSearch nodesearch = (BeamSearch.NodeSearch) node;
			nodesearch.priority();
			if (this.priority < nodesearch.priority) return -1;
			else if (this.priority < nodesearch.priority) return 1;
			else return 0;
		}
	}
	
	protected BeamSearch.NodeSearch current;
	
	public BeamSearch(Puzzle init, int maxnodes, int width) {
		super(init, maxnodes);
		this.width = width;
		NodeSearch.setTypeHeuristic("h2");
	}
	
	@Override
	public BeamSearch.NodeSearch getCurrent() {
		return current;
	}
	
	@Override 
	protected void solver() {
		PriorityQueue<BeamSearch.NodeSearch> priorityqueue = new PriorityQueue<>();
		PriorityQueue<BeamSearch.NodeSearch> child = new PriorityQueue<>();
		Set<Puzzle> visitednodes = new HashSet<>();
		current = new BeamSearch.NodeSearch(init, null);
		priorityqueue.add(current);
		visitednodes.add(init);
		while (!current.puzzle.atGoal() && visitednodes.size() < maxNodes) {
			current = priorityqueue.poll();
			assert current != null;
			for (Puzzle neighbor : current.puzzle.neighbors()) {
				if ((current.prevnode == null || !neighbor.equals(current.prevnode.puzzle)) && !visitednodes.contains(neighbor) ) {
					child.add(new BeamSearch.NodeSearch(neighbor, current));
					visitednodes.add(neighbor);
					exploredNodes++;
					
				}
			}
			if (priorityqueue.isEmpty()) {
				priorityqueue = fill(child);
			}
		}
		if (visitednodes.size() > maxNodes) {
			System.out.println("Max nodes reached");
		}
		if (current.puzzle.atGoal()) {
			solveable = true;
		}
	}
	
	public PriorityQueue<NodeSearch> fill(PriorityQueue<NodeSearch> child) {
		PriorityQueue<BeamSearch.NodeSearch> temp = new PriorityQueue<>();
		while (!child.isEmpty() && temp.size() < width) {
			temp.add(child.poll());
		}
		child.clear();
		return temp;
	}
}

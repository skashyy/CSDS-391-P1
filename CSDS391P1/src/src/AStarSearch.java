package src;

import java.util.*;

public class AStarSearch extends GeneralSearch {

	public AStarSearch(Puzzle init, int maxnodes, String heuristic) {
		super(init, maxnodes);
		NodeSearch.setTypeHeuristic(heuristic);
	}

	@Override
	protected void solver() {
		PriorityQueue<NodeSearch> priorityqueue = new PriorityQueue<>();
		Map<Puzzle, Integer> visitednodes = new HashMap<>();
		current = new NodeSearch(init, null);
		priorityqueue.add(current);
		visitednodes.put(init, current.moves);

		while (!current.puzzle.atGoal() && visitednodes.size() < maxNodes) {
			current = priorityqueue.poll();
			assert current != null;
			PriorityQueue<NodeSearch> duplicates = new PriorityQueue<>();
			for (Puzzle neighbor : current.puzzle.neighbors()) {
				boolean key = visitednodes.containsKey(neighbor);
					if ((current.prevnode == null || !neighbor.equals(current.prevnode.puzzle)) && !key) {
						priorityqueue.add(new NodeSearch(neighbor, current));
						visitednodes.put(neighbor, current.moves + 1);
	                    exploredNodes++;
					} else if (key) {
						if (visitednodes.get(neighbor) > current.moves + 1) {
							duplicates.add(new NodeSearch(neighbor,current));
							visitednodes.put(neighbor,current.moves + 1);
							exploredNodes++;
						}
					}
			}
			priorityqueue.addAll(duplicates);
			System.out.println(visitednodes.size());
		}
		if (visitednodes.size() > maxNodes) {
			System.out.println("Max nodes reached");
		}
		if (current.puzzle.atGoal()) {
			solveable = true;
		}
	}

}

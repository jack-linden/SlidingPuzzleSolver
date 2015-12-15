   

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Heuristic best first search solver for 3x3 4x4 and 5x5 sliding puzzles. Uses
 * a greedy heuristic search that is not guaranteed to be optimal.
 *
 * @author jacklinden
 *
 */
public class GreedySolver extends Solver {

	public GreedySolver(PuzzleState initState, PuzzleState goalState, String heuristicName) {
		this.initState = initState;
		this.goalState = goalState;
		this.heuristic = HeuristicFactory.getHeuristic(heuristicName, goalState);
	}

	/**
	 * Solves the sliding puzzle. Sets the solutionPath field of the superclass
	 * with the resulting sequence of moves that solves the puzzle.
	 */
	public void solve() {
		HashSet<PuzzleState> exploredSet = new HashSet<PuzzleState>();
		PriorityQueue<PuzzleState> queue = new PriorityQueue<PuzzleState>();
		queue.add(initState);

		while (!queue.isEmpty()) {
			// Get next best state from priority queue
			PuzzleState currState = queue.poll();

			exploredSet.add(currState);
			// Get move list and add to priority queue according to HX value
			ArrayList<PuzzleMove> moves = currState.getMoveList();
			for (PuzzleMove move : moves) {

				PuzzleState childState = new PuzzleState(currState);
				childState.makeMove(move);
				childState.setEvaluationValue(heuristic.getHXvalue(childState, true));
				if (exploredSet.contains(childState)) {
					continue;
				} else if (childState.isGoalState()) {
					this.solutionPath = childState.path;
					return;
				}

				queue.add(childState); // Enqueue new state
			}

		}
	}

}
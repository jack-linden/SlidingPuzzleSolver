   

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class IDAStarSolver extends Solver{

	public IDAStarSolver(PuzzleState initState, PuzzleState goalState, String heuristicName) {
		this.initState = initState;
		this.goalState = goalState;
		this.heuristic = HeuristicFactory.getHeuristic(heuristicName,goalState);
	}
	
	/**
	 * Solves the sliding puzzle. Sets the solutionPath field of the superclass
	 * with the resulting sequence of moves that solves the puzzle. IDA* always finds an
	 * optimal solution for the given puzzle. Can handle large boards because its iterative deepening
	 * reduces memory consumption. 
	 */
	public void solve() {
		double cutoff = heuristic.getHXvalue(initState,true);
		while(true){
			
			HashSet<PuzzleState> explored = new HashSet<PuzzleState>();
			PriorityQueue<PuzzleState> queue = new PriorityQueue<PuzzleState>();
			double min_above = Integer.MAX_VALUE;
			queue.add(initState);
			
			while (!queue.isEmpty()) {
				PuzzleState currState = queue.poll();
				explored.add(currState);
				
				//Get move list and add child states to priority queue
				ArrayList<PuzzleMove> moves = currState.getMoveList();
				for (PuzzleMove move : moves) {
					PuzzleState childState = new PuzzleState(currState);
					childState.makeMove(move);
					//double GX = 0;//childState.path.getLength();
					int HX = heuristic.getHXvalue(childState,true);
					childState.setEvaluationValue(HX);
					if( explored.contains(childState)){
						continue;
					}
					//Update the cutoff for the next iterative deepening
					if( childState.evaluationValue > cutoff ){
						if( childState.evaluationValue < min_above ){
							min_above = childState.evaluationValue;
						}
						continue;
					}
					//Check for the goal state
					if (childState.isGoalState()) {
						this.solutionPath = childState.path;
						this.goalState = childState;
						return;
					}
	
					queue.add(childState);
				}
	
			}
			cutoff = min_above;
		}
	}

}

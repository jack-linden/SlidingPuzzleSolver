package puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BidirectionalGreedySolver extends Solver {

	// Synchronized maps for forwards and backwards threads
	private Map<PuzzleState, PuzzleState> explored_F = Collections.synchronizedMap(new HashMap<PuzzleState, PuzzleState>());
	private Map<PuzzleState, PuzzleState> explored_B = Collections.synchronizedMap(new HashMap<PuzzleState, PuzzleState>());

	public BidirectionalGreedySolver(PuzzleState initState, PuzzleState goalState, String heuristicName) {
		this.initState = initState;
		this.goalState = goalState;
		this.heuristic = HeuristicFactory.getHeuristic(heuristicName, initState, goalState);

	}

	/**
	 * Solves the sliding puzzle. Sets the solutionPath field of the superclass
	 * with the resulting sequence of moves that solves the puzzle. Uses
	 */
	public void solve() {
		setup();

		// Initialize and start the forwards search thread
		ForwardThread forwardThread = new ForwardThread();
		forwardThread.start();
		// Initialize and start backwards search thread
		BackwardsThread backwardsThread = new BackwardsThread();
		forwardThread.setSisterThread(backwardsThread);
		backwardsThread.setSisterThread(forwardThread);
		backwardsThread.start();

		try {
			forwardThread.join();
			backwardsThread.join();
		} catch (Exception e) {
			System.out.println("Something went wrong with threads");
		}

	}

	/*
	 * Clears explored sets
	 */
	private void setup() {

		explored_F.clear();
		explored_B.clear();

	}

	/*
	 * Sets the solution path 
	 */
	private synchronized void setSolutionPath(Path<PuzzleMove> forwardPath, Path<PuzzleMove> backwardsPath) {
		// The other thread had already set the path
		if (solutionPath != null) {
			return;
		}
		// Piece the paths together
		Path<PuzzleMove> path = new Path<PuzzleMove>(forwardPath);
		for (int i = backwardsPath.getLength() - 1; i >= 0; i--) {
			path.addMove(backwardsPath.getMove(i));
		}
		this.solutionPath = path;

	}

	/*
	 * Forwards search thread. Starts from the initial state and works toward the
	 * goal state
	 */
	private class ForwardThread extends Thread {

		private BackwardsThread sisterThread;
		private volatile boolean run;

		ForwardThread() {
			this.run = true;
		}

		public void setSisterThread(BackwardsThread thread) {
			sisterThread = thread;
		}

		@Override
		public void run() {
			solve();
		}

		/*
		 * Starts from the initial board configuration and works towards the
		 * goal state (sorted board). A solution path is found when a state is
		 * reached that has also been explored by the backwards thread.
		 */
		public void solve() {
			
			PriorityQueue<PuzzleState> queue_F = new PriorityQueue<PuzzleState>();
			initState.setEvaluationValue(heuristic.getHXvalue(initState, true));
			queue_F.add(initState);
			
			while (!queue_F.isEmpty() && run) {
				PuzzleState currState = queue_F.poll();
				explored_F.put(currState, currState);
				
				//Get move list and add child states to priority queue
				ArrayList<PuzzleMove> moves = currState.getMoveList();
				for (PuzzleMove move : moves) {

					PuzzleState childState = new PuzzleState(currState);
					childState.makeMove(move);
					
					//Goal state when we find a state that the other thread has already explored
					if (explored_B.containsKey(childState)) {
						PuzzleState backwardsState = explored_B.get(childState);
						run = false;
						sisterThread.run = false;
						setSolutionPath(childState.getPath(), backwardsState.getPath());
						return;
					}
					//Already explored this state so skip it
					if (explored_F.containsKey(childState)) {
						continue;
					}

					childState.setEvaluationValue(heuristic.getHXvalueImproved(currState, move, currState.evaluationValue, true));
					queue_F.add(childState);

				}
			}
		}

	}
	
	/*
	 * Backwards search thread. Starts from the goal state and works toward the
	 * initial state
	 */
	private class BackwardsThread extends Thread {

		ForwardThread sisterThread;
		private volatile boolean run;

		BackwardsThread() {
			run = true;
		}

		public void setSisterThread(ForwardThread thread) {
			sisterThread = thread;
		}

		@Override
		public void run() {

			solve();
		}

		/*
		 * Starts from the goal board configuration and works towards the
		 * initial state. A solution path is found when a state is
		 * reached that has also been explored by the forwards thread.
		 */
		public void solve() {
			PriorityQueue<PuzzleState> queue_B = new PriorityQueue<PuzzleState>();
			goalState.setEvaluationValue(heuristic.getHXvalue(goalState, false));
			queue_B.add(goalState);
			
			while (!queue_B.isEmpty() && run) {
				PuzzleState currState = queue_B.poll();

				explored_B.put(currState, currState);
				
				//Get move list and add child states to priority queue
				ArrayList<PuzzleMove> moves = currState.getMoveList();
				for (PuzzleMove move : moves) {
					PuzzleState childState = new PuzzleState(currState);
    				childState.makeMove(move);

					//Goal state when we find a state that the other thread has already explored
					if (explored_F.containsKey(childState)) {
						PuzzleState forwardState = explored_F.get(childState);
						run = false;
						sisterThread.run = false;
						setSolutionPath(forwardState.getPath(), childState.getPath());
						return;
					}

					//Already explored this state so skip it
					if (explored_B.containsKey(childState)) {
						continue;
					}
					childState.setEvaluationValue(heuristic.getHXvalueImproved(currState, move, currState.evaluationValue, false));
					queue_B.add(childState);

				}
			}
		}

	}

}

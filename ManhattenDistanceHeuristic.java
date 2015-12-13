package puzzle;

import java.util.HashMap;

public class ManhattenDistanceHeuristic extends Heuristic {
	HashMap<Integer, Integer[]> initTileCoords = new HashMap<Integer, Integer[]>();
	HashMap<Integer, Integer[]> goalTileCoords = new HashMap<Integer, Integer[]>();

	//Setup hash maps for quick lookup
	public ManhattenDistanceHeuristic(PuzzleState init, PuzzleState goal) {
		int[][] initBoard = init.board.board;
		int[][] goalBoard = goal.board.board;
		for (int i = 0; i < initBoard.length; i++) {
			for (int j = 0; j < initBoard.length; j++) {
				initTileCoords.put(initBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
				goalTileCoords.put(goalBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
			}
		}
	}
	
	public ManhattenDistanceHeuristic(PuzzleState goal) {
		int[][] goalBoard = goal.board.board;
		for (int i = 0; i < goalBoard.length; i++) {
			for (int j = 0; j < goalBoard.length; j++) {
				goalTileCoords.put(goalBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
			}
		}
	}

	/**
	 * Calculates and returns Manhattan distance for the given state's board.
	 */
	public double getHXvalue(PuzzleState state, boolean toGoal) {
		HashMap<Integer,Integer[]> locations = toGoal ? goalTileCoords : initTileCoords;
		
		int[][] board = ((PuzzleState) state).board.board;
		double dist = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				int val = board[x][y];
				if( val == 0 )continue;
				Integer[] coords = locations.get(val);
				dist += (Math.abs(x-coords[0])+Math.abs(y-coords[1]));
			}
		}
		return dist;
	}
}

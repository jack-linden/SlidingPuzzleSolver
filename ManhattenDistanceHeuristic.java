   

import java.util.HashMap;

public class ManhattenDistanceHeuristic extends Heuristic {
	HashMap<Byte, Integer[]> initTileCoords = new HashMap<Byte, Integer[]>();
	HashMap<Byte, Integer[]> goalTileCoords = new HashMap<Byte, Integer[]>();

	//Setup hash maps for quick lookup
	public ManhattenDistanceHeuristic(PuzzleState init, PuzzleState goal) {
		byte[][] initBoard = init.board.board;
		byte[][] goalBoard = goal.board.board;
		for (int i = 0; i < initBoard.length; i++) {
			for (int j = 0; j < initBoard.length; j++) {
				initTileCoords.put(initBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
				goalTileCoords.put(goalBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
			}
		}
	}
	
	public ManhattenDistanceHeuristic(PuzzleState goal) {
		byte[][] goalBoard = goal.board.board;
		for (int i = 0; i < goalBoard.length; i++) {
			for (int j = 0; j < goalBoard.length; j++) {
				goalTileCoords.put(goalBoard[i][j], new Integer[]{new Integer(i), new Integer(j)});
			}
		}
	}

	/**
	 * Calculates and returns Manhattan distance for the given state's board.
	 */
	public int getHXvalue(PuzzleState state, boolean toGoal) {
		HashMap<Byte,Integer[]> locations = toGoal ? goalTileCoords : initTileCoords;
		
		byte[][] board = ((PuzzleState) state).board.board;
		int dist = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				byte val = board[x][y];
				if( val == 0 )continue;
				Integer[] coords = locations.get(val);
				dist += (Math.abs(x-coords[0])+Math.abs(y-coords[1]));
			}
		}
		return dist;
	}

	/**
	 * Calculates the Manhattan distance in constant time by observing the difference the Manhattan distances of the 
	 * current tile position and where it will land in the empty space.
	 */
	@Override
	public int getHXvalueImproved(PuzzleState state, PuzzleMove move, int oldEvaluationValue, boolean isGoal) {
		HashMap<Byte,Integer[]> locations = isGoal ? goalTileCoords : initTileCoords;

		int moveX = move.x;
		int moveY = move.y;
		int emptyX = state.board.emptyX;
		int emptyY = state.board.emptyY;
		byte val = state.board.get(moveX, moveY);
		Integer[] coords = locations.get(val);
		
		int preMoveDist = (Math.abs(moveX-coords[0])+Math.abs(moveY-coords[1]));
		int postMoveDist = (Math.abs(emptyX-coords[0])+Math.abs(emptyY-coords[1]));
		int difference = Math.abs(preMoveDist-postMoveDist);
		int toReturn = preMoveDist > postMoveDist ? oldEvaluationValue-difference : oldEvaluationValue+difference;	
		return toReturn;
	}
}

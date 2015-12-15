   

import java.util.ArrayList;

public class PuzzleState implements Comparable<PuzzleState> {
	
	PuzzleBoard board; //The current board configuration
	Path<PuzzleMove> path; //The path leading to this state
	int evaluationValue; //The value representing how "good" the board is

	public PuzzleState(PuzzleBoard board) {
		this.board = new PuzzleBoard(board);
		this.path = new Path<PuzzleMove>();
		this.evaluationValue = 0;
	}

	public PuzzleState(PuzzleState state) {
		this.board = new PuzzleBoard(state.board);
		this.path = new Path<PuzzleMove>(state.path);
		this.evaluationValue = 0;
	}

	public Path<PuzzleMove> getPath() {

		return this.path;
	}
	
	//Because we're just doing a greedy search, a board at the goal state
	// should have a heuristic HX score of 0
	public boolean isGoalState() {
		return evaluationValue == 0;
	}

	public void setEvaluationValue(int val) {
		this.evaluationValue = val;
	}

	public PuzzleBoard getBoard() {
		return board;
	}

	//Returns all possible orthogonal moves to the empty square
	public ArrayList<PuzzleMove> getMoveList() {
		ArrayList<PuzzleMove> moves = new ArrayList<PuzzleMove>();
		int emptyX = board.emptyX;
		int emptyY = board.emptyY;

		// Above
		if (emptyX - 1 >= 0) {
			moves.add(new PuzzleMove(board.get(emptyX - 1, emptyY), emptyX - 1, emptyY));
		}
		// Left
		if (emptyY - 1 >= 0) {
			moves.add(new PuzzleMove(board.get(emptyX, emptyY - 1), emptyX, emptyY - 1));
		}
		// Below
		if (emptyX + 1 < board.SIZE) {
			moves.add(new PuzzleMove(board.get(emptyX + 1, emptyY), emptyX + 1, emptyY));
		}
		// Right
		if (emptyY + 1 < board.SIZE) {
			moves.add(new PuzzleMove(board.get(emptyX, emptyY + 1), emptyX, emptyY + 1));
		}
		return moves;
	}
	//Swaps move(x,y) with emptyX and emptyY and updates these values
	public void makeMove(PuzzleMove move) {
		board.swapWithEmpty(move.x, move.y);
		path.addMove(move);
	}

	public int compareTo(PuzzleState other) {
		return new Double(this.evaluationValue).compareTo(new Double(other.evaluationValue));
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (this.getClass() != other.getClass()) {
			return false;
		}
		final PuzzleState otherState = (PuzzleState) other;
		return this.board.equals(otherState.board);

	}

	@Override
	public int hashCode() {
		return this.board.hashCode();
	}

	public String toString() {
		return board.toString();
	}

}

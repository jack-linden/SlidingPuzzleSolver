package puzzle;

public abstract class Heuristic {
	
	public abstract int getHXvalue(PuzzleState state, boolean isGoal);
	public abstract int getHXvalueImproved(PuzzleState state, PuzzleMove move, int oldEvaluationValue, boolean isGoal);
}

package puzzle;

public abstract class Heuristic {
	
	public abstract double getHXvalue(PuzzleState state, boolean isGoal);
}

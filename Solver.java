   

public abstract class Solver {
	
	PuzzleState initState;
	PuzzleState goalState;
	Path<PuzzleMove> solutionPath;
	Heuristic heuristic;
	
	public abstract void solve();
	
	public void printSolutionPath() {
		System.out.println(solutionPath);
	}
}

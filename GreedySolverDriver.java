   

public class GreedySolverDriver {

	public static void main(String[] args){
		double start,end;
		GreedySolver solver = new GreedySolver(new PuzzleState(new PuzzleBoard(args[0])),new PuzzleState(new PuzzleBoard(args[1])),"manhattan");
		start = System.nanoTime();
		solver.solve();
		end = System.nanoTime();
		System.out.println((end-start)/1000000000);
		solver.printSolutionPath();
	}
}

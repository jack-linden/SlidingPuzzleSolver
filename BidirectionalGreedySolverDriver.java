package puzzle;

public class BidirectionalGreedySolverDriver {

	public static void main(String[] args){
		double start,end;
		BidirectionalGreedySolver bps = new BidirectionalGreedySolver(new PuzzleState(new PuzzleBoard(args[0])), new PuzzleState(new PuzzleBoard(args[1])), "manhattan");
		start = System.nanoTime();
		bps.solve();
		end = System.nanoTime();
		System.out.println((end-start)/1000000000);
		bps.printSolutionPath();
	}
}

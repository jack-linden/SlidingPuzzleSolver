   

/*
 * Factory class for returning heuristics. 
 * Only have Manhattan distance implemented but plan on others
 * later.
 */
public class HeuristicFactory{
	
	public static Heuristic getHeuristic(String name, PuzzleState initState, PuzzleState goalState){
		
		if( name.equals("manhattan")){
			return new ManhattenDistanceHeuristic(initState,goalState);
		}
		return null;
	}
	public static Heuristic getHeuristic(String name, PuzzleState goalState){
		
		if( name.equals("manhattan")){
			return new ManhattenDistanceHeuristic(goalState);
		}
		return null;
	}
}
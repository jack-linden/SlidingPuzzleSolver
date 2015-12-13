package puzzle;

import java.util.ArrayList;
/**
 * Class for storing a path of states
 * @author jacklinden
 *
 * @param <PuzzleMove>
 */
@SuppressWarnings("hiding")
public class Path<PuzzleMove> {
	ArrayList<PuzzleMove> moves;
	
	public Path(){
		this.moves = new ArrayList<PuzzleMove>();
	}
	
	public Path(Path<PuzzleMove> other){
		this.moves = new ArrayList<PuzzleMove>(other.moves);
	}
	
	public void addMove(PuzzleMove move){
		this.moves.add(move);
	}
	
	public PuzzleMove getMove(int index){
		return moves.get(index);
	}
	
	public int getLength() {
		return this.moves.size();
	}

	
	public String toString(){
		StringBuilder sb =  new StringBuilder();
		for(PuzzleMove move : moves){
			sb.append(move+"\n");
		}
		return sb.toString();
	}
}

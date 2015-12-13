package puzzle;

public class PuzzleMove {

	//The tile number
	int number;
	//The (x,y) coordinates
	int x,y;
	public PuzzleMove(int number, int x, int y){
		this.x = x;
		this.y = y;
		this.number = number;
	}
	
	public String toString(){
		return "Moved " + number + " to the empty square";
	}
}

package puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class PuzzleBoard {

	byte[][] board;
	int SIZE;
	// (x,y) coordinates of the empty square
	int emptyX;
	int emptyY;

	/*
	 * Construct board from file.
	 */
	public PuzzleBoard(String filename) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			SIZE = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
		}
		try {
			board = new byte[SIZE][SIZE];
			for (int i = 0; i < SIZE; i++) {
				board[i] = getByteArr(reader.readLine().split(" "));
				for (int j = 0; j < SIZE; j++) {
					if (board[i][j] == 0) {
						this.emptyX = i;
						this.emptyY = j;
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("I/O problem");
			System.exit(0);
		}

	}

	/*
	 * Returns value at (x,y)
	 */
	public byte get(int x, int y) {
		return board[x][y];
	}

	//Creates deep copy of board
	public PuzzleBoard(PuzzleBoard board) {
		this.SIZE = board.SIZE;
		this.board = new byte[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			this.board[i] = Arrays.copyOf(board.board[i], SIZE);
		}
		this.emptyX = board.emptyX;
		this.emptyY = board.emptyY;
	}

	//Helper method for constructors
	private byte[] getByteArr(String[] arr) {
		byte[] toReturn = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			toReturn[i] = Byte.parseByte(arr[i]);
		}
		return toReturn;
	}

	//Takes swaps tiles (emptyX,empty) and (x,y)
	public void swapWithEmpty(int x, int y) {
		byte temp = board[emptyX][emptyY];
		board[emptyX][emptyY] = board[x][y];
		board[x][y] = temp;
		emptyX = x;
		emptyY = y;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (this.getClass() != other.getClass()) {
			return false;
		}
		final PuzzleBoard otherBoard = (PuzzleBoard) other;
		return Arrays.deepEquals(this.board, otherBoard.board);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(board);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sb.append("" + this.board[i][j] + " ");
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}

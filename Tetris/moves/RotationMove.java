package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a rotation move which is either clockwise or anti-clockwise.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class RotationMove implements Move {
	/**
	 * Rotate a given number of steps in a clockwise direction (if positive), or an
	 * anti-clockwise direction (if negative).
	 */
	private final int rotation;

	public RotationMove(int steps) {
		this.rotation = steps;
	}

	@Override
	public boolean isValid(Board board) {
		//CREATES A TEMPORARY TETROMINO AND EXECUTES THE REQUESTED ROTATION
		Tetromino tet = board.getTetromino().rotate(rotation);
		
		//HOLDS ALL FOUR CORNER CELLS OF THE BOUNDING BOX
		int rightX = tet.getBoundingBox().getMaxX();
		int upY = tet.getBoundingBox().getMaxY();
		int leftX = tet.getBoundingBox().getMinX();
		int lowY = tet.getBoundingBox().getMinY();
		
		//CHECKS TO SEE IF THE MOVE IS OUT OF BOUNDS
		if(leftX < 0 || rightX > board.getWidth()-1 || lowY < 0 || upY > board.getHeight()) { return false; }
			for(int x = leftX; x <= rightX; x++) {
				for(int y = lowY; y <= upY; y++) {
					if(tet.isWithin(x, y) && board.getPlacedTetrominoAt(x, y) != null) { return false; }
				}
			}
		return true;
	}

	/**
	 * Get the number of rotation steps in this move.
	 *
	 * @return
	 */
	public int getRotation() {
		return rotation;
	}

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Create a copy of this board which will be updated.
		ActiveTetromino tetromino = board.getTetromino().rotate(rotation);
		// Apply the move to the new board, rather than to this board.
		board.updateTetromino(tetromino);
		// Return updated version of this board.
		return board;
	}

	@Override
	public String toString() {
		return "rotate " + rotation;
	}

}

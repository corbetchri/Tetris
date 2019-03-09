package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a translation move.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class TranslationMove implements Move {
	/**
	 * Amount to translate x-coordinate.
	 */
	private int dx;
	/**
	 * Amount to translate y-coordinate.
	 */
	private int dy;

	/**
	 * Construct new TranslationMove for a given amount of horizontal and vertical
	 * translation.
	 *
	 * @param dx
	 *            Amount to translate in horizontal direction.
	 * @param dy
	 *            Amount to translate in vertical direction.
	 */
	public TranslationMove(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public boolean isValid(Board board) {
		//CREATES A TEMPORARY TETROMINO AND MOVES IT IN THE REQUESTED DIRECTION
		Tetromino tet = board.getTetromino().translate(dx, dy);
		
		//HOLDS ALL FOUR CORNER CELLS OF THE BOUNDING BOX
		int rightX = tet.getBoundingBox().getMaxX();
		int upY = tet.getBoundingBox().getMaxY();
		int leftX = tet.getBoundingBox().getMinX();
		int lowY = tet.getBoundingBox().getMinY();
		
		//CHECKS TO SEE IF THE MOVE IS OUT OF BOUNDS
		if(leftX < 0 || rightX > board.getWidth()-1 || lowY < 0) { return false; }
		for(int x = leftX; x <= rightX; x++) {
			for(int y = lowY; y <= upY; y++) {
				if(tet.isWithin(x, y) && board.getPlacedTetrominoAt(x, y) != null) { return false; }
			}
		}
		return true;
	}

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Apply translation for this move
		ActiveTetromino tetromino = board.getTetromino().translate(dx, dy);
		// Apply the move to the new board.
		board.updateTetromino(tetromino);
		// Return updated version of board
		return board;
	}

	@Override
	public String toString() {
		if (dx > 0) {
			return "right " + dx;
		} else if (dx < 0) {
			return "left " + dx;
		} else {
			return "drop " + dy;
		}
	}
}

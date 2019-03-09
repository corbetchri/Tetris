package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a "landing" move. That is, when the tetromino is placed on the
 * board properly.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class LandingMove implements Move {
	private int lines;

	public LandingMove() {
		this.lines = -1;
	}

	public LandingMove(int lines) {
		this.lines = lines;
	}

	@Override
	public boolean isValid(Board board) {
//CHECK THAT THE NUMBER OF LINES MATCH THE NUMBER OF FULL ROWS
		int fullRow = 0;
			
		for(int y = 0; y < board.getHeight(); y++){ 
			int cellCount = 0;		//RESETS THE OCCUPIED HORIZONTAL ROW CELL COUNT
			for(int x = 0; x < board.getWidth(); x++) {
				if(board.getTetrominoAt(x, y) != null) { cellCount++; }
			}
			if(cellCount == board.getWidth()) { fullRow++; }
		}
		if(fullRow != lines) { return false; }
		
//CREATES A TEMPORARY TETROMINO AND CHECKS TO SEE IF THE DESIRED LANDING REQUEST IS EITHER 
//TOUCHING THE BASE OF THE BOARD AND/OR IN CONTACT WITH ANOTHER PLACED TETROMINO
		Tetromino tet = board.getTetromino();
		
		int rightX = tet.getBoundingBox().getMaxX();
		int leftX = tet.getBoundingBox().getMinX();
		int lowY = tet.getBoundingBox().getMinY();
		
		if(lowY != 0) {
			int cellBelow = 0;
			for(int x = leftX; x <= rightX; x++) {		//CHECK THE BOTTOM ROW OF THE BOUNDING BOX
				if(tet.isWithin(x, lowY) && board.getPlacedTetrominoAt(x, lowY-1) != null) { cellBelow++; }
			}
			if(cellBelow == 0) { return false; }
			else { return true; }
		}
		return true;
	}
		

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Place tetromino on board
		board.put(board.getTetromino());
		// Reset active tetromino
		board.updateTetromino(null);
		// Remove any full rows
		removeFullRows(board);
		// DOne!
		return board;
	}

	/**
	 * Remove any rows on the board which are now full.
	 *
	 * @param board
	 * @return
	 */
	private void removeFullRows(Board board) {
		//ITERATE UP THE BOARD STARTING FROM THE BOTTOM
		for(int y = 0; y < board.getHeight(); y++){
			int cellCount = 0;
			for(int x = 0; x < board.getWidth(); x++) {
				if(board.getPlacedTetrominoAt(x, y) != null) { cellCount++; }
				}
			if(cellCount == board.getWidth()) {
				for(int aboveY = y+1; aboveY < board.getHeight(); aboveY++) {
					for(int i = 0; i < board.getWidth(); i++) {
						board.setPlacedTetromino(i, aboveY-1, board.getPlacedTetrominoAt(i, aboveY));
					}
				}	
				y--;		//DROPS ONE LOWER IN THE Y COLUMN. 
							//WHEN ITERATED UP THE BOARD WE CAN LOOK TO SEE IF THE ROW JUST MOVED IS FULL
				}
			}
	}


	@Override
	public String toString() {
		return "landing " + lines;
	}
}

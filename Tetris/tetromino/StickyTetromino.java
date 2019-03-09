package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;

/**
 * Represents a tetromino which can only perform one rotation operation.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class StickyTetromino implements Tetromino {
	
	int rCount;
	Tetromino stickyTet;

	public StickyTetromino(int count, Tetromino tetromino) {
		this.rCount = count;
		this.stickyTet = tetromino;
	}

	@Override
	public Color getColor() {
		if(rCount != 0) {
			return stickyTet.getColor();
		}
		return Tetromino.Color.DARK_GRAY;
	}

	@Override
	public Orientation getOrientation() {
		return stickyTet.getOrientation();
	}

	@Override
	public boolean isWithin(int x, int y) {
		return stickyTet.isWithin(x, y);
	}

	@Override
	public Rectangle getBoundingBox() {
		return stickyTet.getBoundingBox();
	}

	@Override
	public Tetromino rotate(int steps) {
		if(rCount < 0) { return new StickyTetromino(0, stickyTet); }
	
		int rotation;
		if(rCount > steps) { rotation = steps; }
		else { rotation = rCount; }
		
		return new StickyTetromino(rCount - rotation, stickyTet.rotate(rotation));
		}
}

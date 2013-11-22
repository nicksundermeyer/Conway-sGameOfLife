import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	private int myX, myY; // x,y position on grid
	private boolean myAlive; // alive (true) or dead (false)
	private int myNeighbors; // count of neighbors with respect to x,y
	private boolean myAliveNextTurn; // Used for state in next iteration
	private Color myColor; // Based on alive/dead rules
	private final Color DEFAULT_ALIVE = Color.ORANGE;
	private final Color DEFAULT_DEAD = Color.GRAY;

	public Cell(int x, int y) {
		this(x, y, false, Color.GRAY);
	}

	public Cell(int row, int col, boolean alive, Color color) {
		myAlive = alive;
		myColor = color;
		myX = col;
		myY = row;
	}

	public boolean getAlive() {
		return myAlive;
	}

	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	public Color getColor() {
		return myColor;
	}

	public void setAlive(boolean alive) {
		if (alive) {
			setAlive(true, DEFAULT_ALIVE);
		} else {
			setAlive(false, DEFAULT_DEAD);
		}
	}

	public void setAlive(boolean alive, Color color) {
		myColor = color;
		myAlive = alive;
	}

	public void setAliveNextTurn(boolean alive) {
		myAliveNextTurn = alive;
	}

	public boolean getAliveNextTurn() {
		return myAliveNextTurn;
	}

	public void setColor(Color color) {
		myColor = color;
	}

	public int getNeighbors() {
		return myNeighbors;
	}

	public void calcNeighbors(Cell[][] cell) {
	/* The method calcNeighbors calculates the number of alive neighbors 
	 * 
	 */
		myNeighbors = 0;
		int row = this.getY();
		int col = this.getX();
		
		boolean myLeftNeighborIsAlive = false;
		
		//initializing neighbor cells
		Cell myLeftNeighbor = cell[row][col];
		Cell myRightNeighbor = cell[row][col];
		Cell myTopNeighbor = cell[row][col];
		Cell myBottomNeighbor = cell[row][col];
		Cell myTopRightNeighbor = cell[row][col];
		Cell myBottomRightNeighbor = cell[row][col];
		Cell myTopLeftNeighbor = cell[row][col];
		Cell myBottomLeftNeighbor = cell[row][col];
		
		
		
		if (row - 1 > 0 && col - 1 > 0 && row + 1 < Display.ROWS && col + 1 < Display.COLS) {
		myLeftNeighbor = cell[row][col - 1];
		myRightNeighbor = cell[row][col + 1];
		
		myTopNeighbor = cell[row + 1][col];
		myBottomNeighbor = cell[row - 1][col];
		
		myTopRightNeighbor = cell[row + 1][col + 1];
		myBottomRightNeighbor = cell[row -1][col + 1];
		
		myTopLeftNeighbor = cell[row + 1][col -1];
		myBottomLeftNeighbor = cell[row -1][col-1];
		}
		
		//basic top/bottom/left/right
		if (row == 0) myTopNeighbor = cell[79][col];
		if (row == 99) myBottomNeighbor = cell[0][col];
		if (col == 0) myLeftNeighbor = cell[row][99];
		if (col == 99) myRightNeighbor = cell[row][0];
		
		//top left
		
		
		//top right
		
		
		//bottom left
		//if (row == 99 && col == 0) myLeftNeighbor = cell[row][Display.COLS]; myRightNeighbor = cell[row][];
		
		//bottom right
		
		
		
		/*
		if (row == 0) myTopLeftNeighbor = cell[79][col-1];
		if (row == 99) myBottomLeftNeighbor = cell[0][col-1];
		if (row == 0) myTopRightNeighbor = cell[79][col+1];
		if (row == 99) myBottomRightNeighbor = cell[0][col+1];
		
		if (col == 0) myTopLeftNeighbor = cell[row-1][99];
		if (col == 99) myTopRightNeighbor = cell[row-1][0];
		if (col == 0) myBottomLeftNeighbor = cell[row+1][99];
		if (col == 99) myBottomRightNeighbor = cell[row+1][0];
		
		
		if (col == 0) myTopRightNeighbor = cell[row][99];
		if (col == 99) myRightNeighbor = cell[row][0];
		*/

		/*
		this code doesn't work because nothing has been written to do the diagonal cases
		because of this, the code gets an error when it attempts to test the cases for the corners of the grid
		if we can't find another simpler way to do it, this will work, but i'd rather have something else that is simpler if possible
		*/


//far left
		else if (col==0){
			myLeftNeighbor = cell[row][Display.COLS - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[row + 1][col];
			myBottomNeighbor = cell[row - 1][col];
			
			myTopRightNeighbor = cell[row + 1][col + 1];
			myBottomRightNeighbor = cell[row -1][col + 1];
			
			myTopLeftNeighbor = cell[row + 1][Display.COLS - 1];
			myBottomLeftNeighbor = cell[row -1][Display.COLS - 1];
		}
//far top
		else if (row==0){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[Display.ROWS-1][col];
			myBottomNeighbor = cell[row - 1][col];
			
			myTopRightNeighbor = cell[Display.ROWS-1][col + 1];
			myBottomRightNeighbor = cell[Display.ROWS-1][col + 1];
			
			myTopLeftNeighbor = cell[row + 1][col -1];
			myBottomLeftNeighbor = cell[row -1][col-1];
		}
//far right
		else if (col==Display.COLS){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][0];
			
			myTopNeighbor = cell[row + 1][col];
			myBottomNeighbor = cell[row - 1][col];
			
			myTopRightNeighbor = cell[row + 1][0];
			myBottomRightNeighbor = cell[row -1][0];
			
			myTopLeftNeighbor = cell[row + 1][col -1];
			myBottomLeftNeighbor = cell[row -1][col-1];
		}
//far bottom
		else if (row==Display.ROWS){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[row + 1][col];
			myBottomNeighbor = cell[0][col];
			
			myTopRightNeighbor = cell[row + 1][col + 1];
			myBottomRightNeighbor = cell[0][col + 1];
			
			myTopLeftNeighbor = cell[row + 1][col -1];
			myBottomLeftNeighbor = cell[0][col-1];
		}
//top left
		else if (col==0 && row==0){
			myLeftNeighbor = cell[row][Display.COLS - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[Display.ROWS-1][col];
			myBottomNeighbor = cell[row - 1][col];
			
			myTopRightNeighbor = cell[row + 1][col + 1];
			myBottomRightNeighbor = cell[row -1][col + 1];
			
			myTopLeftNeighbor = cell[row + 1][Display.COLS - 1];
			myBottomLeftNeighbor = cell[row -1][Display.COLS - 1];
		}

		

		
		//Now to count the number of cells that are alive.
		
		if (myLeftNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myRightNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myTopNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myBottomNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myTopRightNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myBottomRightNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myTopLeftNeighbor.getAlive()) {
			myNeighbors++;
		}
		
		if (myBottomLeftNeighbor.getAlive()) {
			myNeighbors++;
		}
		}


	public void willIBeAliveNextTurn() {
		
		if (this.getAlive()) {
			
			/* with fewer than two live neighbors dies, as if caused by under-population
			 * with two or three live neighbors lives on to the next generation.
			 * with more than three live neighbors dies, as if by overcrowding
			 */
			
			if (this.getNeighbors() < 2) {
				this.setAliveNextTurn(false);
			}
			
			if (this.getNeighbors() == 2 || this.getNeighbors() == 3) {
				this.setAliveNextTurn(true);
			}
			
			if (this.getNeighbors() > 3) {
				this.setAliveNextTurn(false);
			}
			
		}
		else {
		/* 
		 * with exactly three live neighbors becomes a live cell, as if by reproducing
		 */
			if (this.getNeighbors() == 3) {
				this.setAliveNextTurn(true);
			}
			else {
				this.setAliveNextTurn(false);
			}
		 
		}
		
	}

	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {
		// I leave this understanding to the reader
		int xleft = x_offset + 1 + (myX * (width + 1));
		int xright = x_offset + width + (myX * (width + 1));
		int ytop = y_offset + 1 + (myY * (height + 1));
		int ybottom = y_offset + height + (myY * (height + 1));
		Color temp = g.getColor();

		g.setColor(myColor);
		g.fillRect(xleft, ytop, width, height);
	}
}



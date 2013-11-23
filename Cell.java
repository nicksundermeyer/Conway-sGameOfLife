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
	/*
	 *  The method calcNeighbors calculates the number of alive neighbors 
	 */
		
		myNeighbors = 0; //setting myNeighbors to 0 to initialize the neighbor count
		int row = this.getY(); //getting x and y values of the cells to find the position of each cell
		int col = this.getX();
		
		//initializing neighbor cells around each cell
		Cell myLeftNeighbor = cell[row][col];
		Cell myRightNeighbor = cell[row][col];
		Cell myTopNeighbor = cell[row][col];
		Cell myBottomNeighbor = cell[row][col];
		Cell myTopRightNeighbor = cell[row][col];
		Cell myBottomRightNeighbor = cell[row][col];
		Cell myTopLeftNeighbor = cell[row][col];
		Cell myBottomLeftNeighbor = cell[row][col];
		
		//the basic neighbor settings, used for any cell not in the outer edges of the grid
		if (row > 0 && col > 0 && row < (Display.ROWS-1) && col < (Display.COLS-1)) {
		myLeftNeighbor = cell[row][col - 1];
		myRightNeighbor = cell[row][col + 1];
		
		myTopNeighbor = cell[row + 1][col];
		myBottomNeighbor = cell[row - 1][col];
		
		myTopRightNeighbor = cell[row - 1][col + 1];
		myBottomRightNeighbor = cell[row + 1][col + 1];
		
		myTopLeftNeighbor = cell[row - 1][col -1];
		myBottomLeftNeighbor = cell[row + 1][col-1];
		}

/*
 * this is going about it the "brute force way", looking through every single condition and changing values
 * if we were to continue editing this code, we would find a way to simply make it so that when you reached the end of a row/column
 * it simply looped back to the beginning of that row/column	
 */
		

		//setting values of far left column, not including corners
		else if (col==0 && row!=0 && row!=(Display.ROWS-1)){
			myLeftNeighbor = cell[row][Display.COLS - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[row-1][col];
			myBottomNeighbor = cell[row + 1][col];
			
			myTopRightNeighbor = cell[row - 1][col + 1];
			myBottomRightNeighbor = cell[row+1][col + 1];
			
			myTopLeftNeighbor = cell[row - 1][Display.COLS - 1];
			myBottomLeftNeighbor = cell[row+1][Display.COLS - 1];
		}
		//setting values for far top row, not including corners
		else if (row==0 && col!=0 && col!=(Display.COLS-1)){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[Display.ROWS-1][col];
			myBottomNeighbor = cell[row+1][col];
			
			myTopRightNeighbor = cell[Display.ROWS-1][col + 1];
			myBottomRightNeighbor = cell[row + 1][col + 1];
			
			myTopLeftNeighbor = cell[Display.ROWS-1][col-1];
			myBottomLeftNeighbor = cell[row+1][col-1];
		}
		//setting values for far right column, not including corners
		else if (col==(Display.COLS-1) && row!=0 && row!=(Display.ROWS-1)){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][0];
			
			myTopNeighbor = cell[row - 1][col];
			myBottomNeighbor = cell[row + 1][col];
			
			myTopRightNeighbor = cell[row - 1][0];
			myBottomRightNeighbor = cell[row + 1][0];
			
			myTopLeftNeighbor = cell[row - 1][col -1];
			myBottomLeftNeighbor = cell[row + 1][col-1];
		}
		//setting values for far bottom row, not including corners
		else if (row==(Display.ROWS-1) && col!=0 && col!=(Display.COLS-1)){
			myLeftNeighbor = cell[row][col - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[row - 1][col];
			myBottomNeighbor = cell[0][col];
			
			myTopRightNeighbor = cell[row - 1][col + 1];
			myBottomRightNeighbor = cell[0][col + 1];
			
			myTopLeftNeighbor = cell[row - 1][col - 1];
			myBottomLeftNeighbor = cell[0][col - 1];
		}
		//setting values for top left corner
		else if (col==0 && row==0){
			myLeftNeighbor = cell[row][Display.COLS - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[Display.ROWS-1][col];
			myBottomNeighbor = cell[row + 1][col];
			
			myTopRightNeighbor = cell[Display.ROWS-1][col + 1];
			myBottomRightNeighbor = cell[row+1][col+1];
			
			myTopLeftNeighbor = cell[Display.ROWS-1][Display.COLS - 1];
			myBottomLeftNeighbor = cell[row+1][Display.COLS - 1];
		}
		//setting values for top right corner
		else if (col==(Display.COLS-1) && row==0){
			myLeftNeighbor = cell[row][col-1];
			myRightNeighbor = cell[row][0];
			
			myTopNeighbor = cell[Display.ROWS-1][col];
			myBottomNeighbor = cell[row + 1][col];
			
			myTopRightNeighbor = cell[Display.ROWS-1][0];
			myBottomRightNeighbor = cell[row+1][0];
			
			myTopLeftNeighbor = cell[Display.ROWS-1][col-1];
			myBottomLeftNeighbor = cell[row+1][col-1];
		}
		//setting values for bottom left corner
		else if (col==0 && row==(Display.ROWS-1)){
			myLeftNeighbor = cell[row][Display.COLS - 1];
			myRightNeighbor = cell[row][col + 1];
			
			myTopNeighbor = cell[row-1][col];
			myBottomNeighbor = cell[0][col];
			
			myTopRightNeighbor = cell[row - 1][col + 1];
			myBottomRightNeighbor = cell[0][col+1];
			
			myTopLeftNeighbor = cell[row-1][Display.COLS - 1];
			myBottomLeftNeighbor = cell[0][Display.COLS - 1];
		}
		//setting values for bottom right corner
		else if (col==(Display.COLS-1) && row==(Display.ROWS-1)){
			myLeftNeighbor = cell[row][col-1];
			myRightNeighbor = cell[row][0];
			
			myTopNeighbor = cell[row-1][col];
			myBottomNeighbor = cell[0][col];
			
			myTopRightNeighbor = cell[row - 1][0];
			myBottomRightNeighbor = cell[0][0];
			
			myTopLeftNeighbor = cell[row-1][col-1];
			myBottomLeftNeighbor = cell[0][col-1];
		}
		
		//now the code to count the number of neighbors each cell has, based on the neighbors found above
		myNeighbors = 0;
		
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
			
			/* 
			 * Rules:
			 * 1. fewer than two live neighbors dies, as if caused by under-population
			 * 2. with two or three live neighbors lives on to the next generation.
			 * 3. with more than three live neighbors dies, as if by overcrowding
			 */
			
			//1
			if (this.getNeighbors() < 2) {
				this.setAliveNextTurn(false);
			}
			//2
			if (this.getNeighbors() == 2 || this.getNeighbors() == 3) {
				this.setAliveNextTurn(true);
			}
			//3
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
		int xleft = x_offset + 1 + (myX * (width + 1));
		int xright = x_offset + width + (myX * (width + 1));
		int ytop = y_offset + 1 + (myY * (height + 1));
		int ybottom = y_offset + height + (myY * (height + 1));
		Color temp = g.getColor();

		g.setColor(myColor);
		g.fillRect(xleft, ytop, width, height);
	}
}



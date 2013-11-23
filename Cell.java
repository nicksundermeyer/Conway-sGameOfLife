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
	// The method calcNeighbors calculates the number of alive neighbors. 
		
		myNeighbors = 0; //setting myNeighbors to 0 to initialize the neighbor count
		int row = this.getY(); //getting x and y values of the cells to find the position of each cell
		int col = this.getX();
		
		//initializing neighbor cells around each cell
		Cell myLeftNeighbor = cell[row][col]; //the left neighbor
		Cell myRightNeighbor = cell[row][col]; //the right neighbor
		Cell myTopNeighbor = cell[row][col]; // the top neighbor
		Cell myBottomNeighbor = cell[row][col]; //the bottom neighbor
		Cell myTopRightNeighbor = cell[row][col]; //the top right neighbor
		Cell myBottomRightNeighbor = cell[row][col]; //the bottom right neighbor
		Cell myTopLeftNeighbor = cell[row][col]; //the top left neighbor
		Cell myBottomLeftNeighbor = cell[row][col]; // the bottom left neighbor
		
		//the basic neighbor settings, used for any cell not in the outer edges of the grid
		if (row > 0 && col > 0 && row < (Display.ROWS-1) && col < (Display.COLS-1)) {
		myLeftNeighbor = cell[row][col - 1]; //defines the left neighbor
		myRightNeighbor = cell[row][col + 1]; //defines the left neighbor
		
		myTopNeighbor = cell[row + 1][col]; //defines the top neighbor
		myBottomNeighbor = cell[row - 1][col]; //defines the left neighbor
		
		myTopRightNeighbor = cell[row - 1][col + 1]; //defines the top right neighbor
		myBottomRightNeighbor = cell[row + 1][col + 1]; //defines the bottom right neighbor
		
		myTopLeftNeighbor = cell[row - 1][col -1]; //defines the top left neighbor
		myBottomLeftNeighbor = cell[row + 1][col-1]; //defines the bottom left neighbor
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
		
		//myNeighbors stores the number of alive neighbors there are
		if (myLeftNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive left neighbors to the total tally
		}
		
		if (myRightNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive right neighbors to the total tally
		}
		
		if (myTopNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive top neighbors to the total tally
		}
		
		if (myBottomNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive bottom neighbors to the total tally
		}
		
		if (myTopRightNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive top right neighbors to the total tally
		}
		
		if (myBottomRightNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive bottom right neighbors to the total tally
		}
		
		if (myTopLeftNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive top left neighbors to the total tally
		}
		
		if (myBottomLeftNeighbor.getAlive()) {
			myNeighbors++;
		//adds alive bottom left neighbors to the total tally
		}
	}


	public void willIBeAliveNextTurn() {
	//This is the method that basically determines if a cell will be alive
	//the next turn based on the number of alive neighbors it has.
	
	 	if (this.getAlive()) {
			
			/* HERE ARE THE RULES
			 * Rules:
			 * Rule 1. fewer than two live neighbors dies, as if caused by under-population
			 * Rule 2. with two or three live neighbors lives on to the next generation.
			 * Rule 3. with more than three live neighbors dies, as if by overcrowding
			 */
			
			//Rule 1
			if (this.getNeighbors() < 2) {
				this.setAliveNextTurn(false);
			}
			//Rule 2
			if (this.getNeighbors() == 2 || this.getNeighbors() == 3) {
				this.setAliveNextTurn(true);
			}
			//Rule 3
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



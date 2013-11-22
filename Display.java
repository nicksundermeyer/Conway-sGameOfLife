import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

// Note that the JComponent is set up to listen for mouse clicks
// and mouse movement.  To achieve this, the MouseListener and
// MousMotionListener interfaces are implemented and there is additional
// code in init() to attach those interfaces to the JComponent.


public class Display extends JComponent implements MouseListener, MouseMotionListener {
	public static final int ROWS = 80;
	public static final int COLS = 100;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int CELL_WIDTH = 5;
	private final int CELL_HEIGHT = 5;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH;   
	private final int DISPLAY_HEIGHT;
	private StartButton startStop;
	private StepButton step;
	private ClearButton clear;
	private ChooseButton choose;
	private ExitButton exit;
	private boolean paintloop = false;


	public Display(int width, int height) {
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();
	}


	public void init() {
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells();

		addMouseListener(this);
		addMouseMotionListener(this);

		// Example of setting up a button.
		// See the StartButton class nested below.
		startStop = new StartButton();
<<<<<<< HEAD
		startStop.setBounds(100, 550, 100, 36); //sets the position of the Start button
=======
		startStop.setBounds(100, 550, 100, 36); //position of the start button
>>>>>>> test-branch-code
		add(startStop);
		startStop.setVisible(true);
		repaint();
		
		step = new StepButton();
		step.setBounds(205, 550, 100, 36); //sets the position of the Step button
		add(step);
		step.setVisible(true);
		
		clear = new ClearButton();
		clear.setBounds(310, 550, 100, 36); //sets the position of the Clear button
		add(clear);
		clear.setVisible(true);
		
		choose = new ChooseButton();
		choose.setBounds(520, 550, 100, 36); //sets the position of the Choose button
		add(choose);
		choose.setVisible(true);
		
		exit = new ExitButton();
		exit.setBounds(415, 550, 100, 36); //sets the position of the Exit button
		add(exit);
		exit.setVisible(true);
	}


	public void paintComponent(Graphics g) {
		int TIME_BETWEEN_REPLOTS = 100; // change to your liking

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawButtons();

		if (paintloop) {
			try {
				Thread.sleep(TIME_BETWEEN_REPLOTS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextGeneration();
			repaint();
		}
		
		repaint();
	}


	public void initCells() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col);
			}
		}
		
		cell[36][22].setAlive(true); // sample use of cell mutator method
		cell[36][23].setAlive(true); // sample use of cell mutator method
		cell[36][24].setAlive(true); // sample use of cell mutator method
		cell[25][25].setAlive(true); 
		cell[26][25].setAlive(true);
		cell[37][25].setAlive(true);
		cell[42][25].setAlive(true);
		cell[41][26].setAlive(true);
		cell[45][24].setAlive(true);
		cell[56][24].setAlive(true);
		cell[44][25].setAlive(true);
		
		
	}

	

	public void togglePaintLoop() {
		paintloop = !paintloop;
	}


	public void setPaintLoop(boolean value) {
		paintloop = value;
	}


	void drawGrid(Graphics g) {
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(X_GRID_OFFSET,
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
					+ COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
					+ (row * (CELL_HEIGHT + 1)));
		}
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
					+ ROWS * (CELL_HEIGHT + 1));
		}
	}

	
	void drawCells(Graphics g) {
		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);
			}
		}
	}


	private void drawButtons() {
		startStop.repaint();
	}


	private void nextGeneration() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col].calcNeighbors(cell);
				cell[row][col].willIBeAliveNextTurn();
			}
		}
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
			boolean resultOfWillIBeAlive = cell[row][col].getAliveNextTurn();
				cell[row][col].setAlive(resultOfWillIBeAlive);
			}
		}
	}


	public void mouseClicked(MouseEvent arg0) {
		int xCoordinate = (arg0.getX() - 25)/6; //gets the col number of the cell clicked on
		int yCoordinate = (arg0.getY() - 40)/6;	//gets the row number of the cell clicked on
		
		if (cell[yCoordinate][xCoordinate].getAlive() == false) {
			cell[yCoordinate][xCoordinate].setAlive(true);
		}
		else {
			cell[yCoordinate][xCoordinate].setAlive(false);
		}
	}


	public void mouseEntered(MouseEvent arg0) {
	
	}


	public void mouseExited(MouseEvent arg0) {

	}


	public void mousePressed(MouseEvent arg0) {
		
	}


	public void mouseReleased(MouseEvent arg0) {
		
	}


	public void mouseDragged(MouseEvent arg0) {
		int xCoordinate = (arg0.getX() - 25)/6;
		int yCoordinate = (arg0.getY() - 40)/6;		
		
		if (choose.buttonState == true) {
			cell[yCoordinate][xCoordinate].setAlive(true);
		}
		else{
			cell[yCoordinate][xCoordinate].setAlive(false);
		}
		
	//	if (cell[yCoordinate][xCoordinate].
	
	}


	public void mouseMoved(MouseEvent arg0) {
		//DOES NOTHING
	}
	
	private class ChooseButton extends JButton implements ActionListener {
		public boolean buttonState;
		
		ChooseButton() {
			super("Alive");
			addActionListener(this);
			buttonState = true;
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("Alive")) {
				setText("Dead");
				buttonState = false;
			} else if (this.getText().equals("Dead")){
				setText("Alive");
				buttonState = true;
			}
			repaint();
		}
	}
	
	private class ClearButton extends JButton implements ActionListener {
		ClearButton() {
			super("Clear");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			// nextGeneration(); // test the clear button
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					cell[row][col].setAlive(false);
				}
			}
			
			repaint();
		}
	}
	
	private class SpeedButton extends JButton implements ActionListener {
		SpeedButton() {
			super("Slow");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			 nextGeneration(); // test the start button
			if (this.getText().equals("Slow")) {
				setText("Medium");
			} else if (this.getText().equals("Medium")){

				setText("Fast");
			}
			else if (this.getText().equals("Fast")) {
				setText("Slow");
			}
			repaint();
		}
	}
	
	private class ExitButton extends JButton implements ActionListener {
		ExitButton() {
			super("Exit");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	private class StepButton extends JButton implements ActionListener {
		StepButton() {
			super("Step");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			nextGeneration();
			
			if (this.getText().equals("Step")) {
				nextGeneration();
				setText("Step");
			} else {
				nextGeneration();
				setText("Step");
			}
			repaint();
		}
	}
	
	private class StartButton extends JButton implements ActionListener {
		StartButton() {
			super("Start");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			 nextGeneration(); // test the start button
			if (this.getText().equals("Start")) {
				togglePaintLoop();
				setText("Pause");
			} else if (this.getText().equals("Pause")){
				togglePaintLoop();
				setText("Start");
			}
			repaint();
		}
	}
}




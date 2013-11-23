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
	private PauseButton pause;
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

		startStop = new StartButton();
		startStop.setBounds(15, 550, 100, 36); //sets the position of the Start button
		add(startStop);
		startStop.setVisible(true);
		
		pause = new PauseButton();
		pause.setBounds(120, 550, 100, 36); //sets the position of the pause button
		add(pause);
		pause.setVisible(true);
		
		step = new StepButton();
		step.setBounds(225, 550, 100, 36); //sets the position of the Step button
		add(step);
		step.setVisible(true);
		
		clear = new ClearButton();
		clear.setBounds(330, 550, 100, 36); //sets the position of the Clear button
		add(clear);
		clear.setVisible(true);
		
		choose = new ChooseButton();
		choose.setBounds(540, 550, 100, 36); //sets the position of the Choose button
		add(choose);
		choose.setVisible(true);
		
		exit = new ExitButton();
		exit.setBounds(435, 550, 100, 36); //sets the position of the Exit button
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
	//This is the method that controls what the mouse does
	//when it clicks on a certain cell. 
		int xCoordinate = (arg0.getX() - 25)/6; //Subtract 25 because columns are
		//offset by 25. Divide by 6 because each cell is 6 units 
		
		int yCoordinate = (arg0.getY() - 40)/6;	//Subtract 40 because the rows are
		//offset by 40. Divide by 6 because each cell is 6 units
		
		if (choose.buttonState == true) {//If statement also looks at whether
		//the choose button is set to alive or dead. Based on that, it will
		//either kill of make cells alive when clicking. 
			cell[yCoordinate][xCoordinate].setAlive(true);
		}
		else{
			cell[yCoordinate][xCoordinate].setAlive(false);
		}
	}


	public void mouseEntered(MouseEvent arg0) {
	//These do nothing
	}


	public void mouseExited(MouseEvent arg0) {
	//These do nothing
	}


	public void mousePressed(MouseEvent arg0) {
	//These do nothing	
	}


	public void mouseReleased(MouseEvent arg0) {
	//These do nothing	
	}


	public void mouseDragged(MouseEvent arg0) {
		int xCoordinate = (arg0.getX() - 25)/6; //Subtract 25 because columns are
		//offset by 25. Divide by 6 because each cell is 6 units 
		
		int yCoordinate = (arg0.getY() - 40)/6;	//Subtract 40 because the rows are
		//offset by 40. Divide by 6 because each cell is 6 units	
		
		if (choose.buttonState == true) { //If statement also looks at whether
		//the choose button is set to alive or dead. Based on that, it will
		//either kill of make cells alive when dragging.
			cell[yCoordinate][xCoordinate].setAlive(true);
		}
		else{
			cell[yCoordinate][xCoordinate].setAlive(false);
		}
	
	}


	public void mouseMoved(MouseEvent arg0) {
		//These do nothing
	}
	
	private class ChooseButton extends JButton implements ActionListener {
	//This is for the Choose Button.
		public boolean buttonState;
		
		ChooseButton() {
			super("Live");
			addActionListener(this);
			buttonState = true;
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("Live")) {
				setText("Die");
				buttonState = false;
			} else if (this.getText().equals("Die")){
				setText("Live");
				buttonState = true;
			}
			repaint();
		}
	}
	
	private class ClearButton extends JButton implements ActionListener {
	//This is for the clear button
		ClearButton() {
			super("Clear");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					cell[row][col].setAlive(false);
				}
			}
			
			repaint();
		}
	}
	
	private class ExitButton extends JButton implements ActionListener {
	//This is for the exit button.
		ExitButton() {
			super("Exit");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	private class StepButton extends JButton implements ActionListener {
	//This is the step button
		StepButton() {
			super("Step");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			
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
	//This is the start button
		StartButton() {
			super("Start");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("Start")) {
				togglePaintLoop();
				setText("Stop");
			} else if (this.getText().equals("Stop")){
				togglePaintLoop();
				setText("Start");
			}
			repaint();
		}
	}
	
	private class PauseButton extends JButton implements ActionListener {
	//This is for the pause button
		PauseButton() {
			super("Pause");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			startStop.setText("Start");
			paintloop = false;
			repaint();
		}
	}
}




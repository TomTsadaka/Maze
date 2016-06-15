package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Position;

/**
 * Canvas to display a 3d maze game.
 */
public class MazeDisplayer3d extends MazeDisplayer {

	Image wall = new Image(getShell().getDisplay(), "images\\wall.png");
    Image smurf = new Image(getShell().getDisplay(), "images\\Smurf.png");
    Image goal = new Image(getShell().getDisplay(), "images\\goal.png");
    Image finish = new Image(getShell().getDisplay(), "images\\finish.png");
    Image step = new Image(getShell().getDisplay(), "images\\step.png");
    Image upstairs = new Image(getShell().getDisplay(), "images\\up.png");
    Image downstairs = new Image(getShell().getDisplay(), "images\\down.png");
    Image upDown = new Image(getShell().getDisplay(), "images\\both.png");
	final Color path = new Color(null, 255, 255, 255);
	final Color background = new Color(null, 190, 190, 190);
	ArrayList<Position> solution = new ArrayList<Position>();
	Position hint = new Position(-1, -1, -1);
	
	/**
	 * Constructor to set parent composite and style, and paint the maze.
	 * <p>
	 * If no maze was set, the character image will be shown on the canvas.<br>
	 * If a maze was set, it will paint 3 floors of it using paintMaze method.
	 * @param parent
	 * @param style
	 */
	MazeDisplayer3d(Composite parent, int style)
	{
		super(parent, style);
		setBackground(background);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{

				if (maze == null)
				{
					e.gc.setForeground(new Color(null, 0,0,0));
					e.gc.setBackground(new Color(null, 0,0,0));
					return;
				}
				
				//paintMaze(e, characterPosition.x, (getSize().y/4)+(getSize().y/6));
				paintMaze(e, characterPosition.x);
			}
		});
	}

	/**
	 * Paints a maze on the canvas, according to its data.
	 * @param e
	 * @param floor
	 * @param mazeHeight
	 */
	public void paintMaze(PaintEvent e, int floor)
	{	
		e.gc.setBackground(path);
		
		int windowWidth = getSize().x;
		int windowHeight = getSize().y;
		
		int cellWidth = windowWidth/maze.getY();
		int cellHeight = windowHeight/maze.getZ();
		//HERE
		for (int i =0; i <maze.getZ(); i++){
			
			for (int j =0; j <maze.getY(); j++){
				
				int y = j*cellWidth;
				int z = i*cellHeight;
				if(maze.getMaze()[floor][j][i] == 1)
				{
					e.gc.drawImage(wall, 0, 0, wall.getBounds().width, wall.getBounds().height, y, z, cellWidth, cellHeight);
				}
				if(maze.getMaze()[floor][j][i] == 0)
				{
					//Paint direction for unseen up and down
					if (floor - 1 >= 0){
						if (maze.getMaze()[floor - 1][j][i] == 0){
							if (floor + 1 < maze.getX()){
								if (maze.getMaze()[floor + 1][j][i] == 0){
									e.gc.drawImage(upDown, 0, 0, upDown.getBounds().width, upDown.getBounds().height, y, z, cellWidth, cellHeight);
								}
								else{
									e.gc.drawImage(downstairs, 0, 0, downstairs.getBounds().width, downstairs.getBounds().height, y, z, cellWidth, cellHeight);
								}
							}
							else{
								e.gc.drawImage(downstairs, 0, 0, downstairs.getBounds().width, downstairs.getBounds().height, y, z, cellWidth, cellHeight);
							}
							
						}
						else if (floor + 1 < maze.getX()){
							if (maze.getMaze()[floor + 1][j][i] == 0)
								e.gc.drawImage(upstairs, 0, 0, upstairs.getBounds().width, upstairs.getBounds().height, y, z, cellWidth, cellHeight);
						}
						else{
							e.gc.fillRectangle(y,z,cellWidth,cellHeight);
						}
					}
					else{
						e.gc.fillRectangle(y,z,cellWidth,cellHeight);
					}
						
				}
			}
		}
		
		
		// Display solution
		if (isDisplayedSolution())
		{
			for (Position p : solution)
			{
				// Paint goal position
				if (p.x == characterPosition.x)
				{
					e.gc.drawImage(step, 0, 0, step.getBounds().width, step.getBounds().height, cellWidth * p.y, cellHeight * p.z+((getSize().y/4)+(getSize().y/6)), cellWidth, cellHeight);
				}
			}
		}		
		
		if (characterPosition.equals(maze.getGoalp()))
		{
			new MessagePopUp(getShell(), "You are the lucky one who won!!!");
			hasWon = true;
		}
		
		if (hasWon)
		{
			return;
		}
		
		// Paint character image
		//e.gc.drawImage(smurf, 0, 0, 128, 128, pos.x * cellWidth, pos.y * cellHeight, cellWidth, cellHeight);
	    e.gc.drawImage(smurf,0,0,smurf.getBounds().width,smurf.getBounds().height,cellWidth*characterPosition.y, cellHeight*characterPosition.z, cellWidth, cellHeight);
	    
		// Paint goal position
		if (maze.getGoalp().x == characterPosition.x)
		{
			e.gc.drawImage(goal, 0, 0, goal.getBounds().width, goal.getBounds().height, cellWidth * maze.getGoalp().y, cellHeight * maze.getGoalp().z, cellWidth, cellHeight);
		}
		
	}

	/**
	 * Sets the character position.
	 */
	@Override
	public void setCharacterPosition(Position position) {
		characterPosition = position;
	}

	private void moveCharacter(Position p){
		setCharacterPosition(p);
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});
	}
	
	/**
	 * 
	 * Returns up down or both for direction painting
	 * @return direction
	 */
	private String directionPaint(Position pos)
	{
		String[] moves;
		boolean up = false;
		boolean down = false;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if left is possible
			if (moves[i] == "Up")
				up = true;
			else if (moves[i] == "Down")
				down = true;
		}
		if (up)
			if (down)
				return "Both";
				
		return "";
	}

	/**
	 * Method to be ran when a player presses the left key.<br>
	 * Moves the character to the left.
	 */
	@Override
	public void moveLeft() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if left is possible
			if (moves[i] == "Left")
				isPossible = true;
		}
		if (isPossible){
			x = p.x;
			y = p.y;
			z = p.z - 1;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}
	}
	
	/**
	 * Method to be ran when a player presses the right key.<br>
	 * Moves the character to the right.
	 */
	@Override
	public void moveRight() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if right is possible
			if (moves[i] == "Right")
				isPossible = true;
		}
		if (isPossible){
			x = p.x;
			y = p.y;
			z = p.z + 1;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}

	}

	/**
	 * Method to be ran when a player presses the up key.<br>
	 * Moves the character forwards.
	 */
	@Override
	public void moveForwards() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if forwards is possible
			if (moves[i] == "Backwards")
				isPossible = true;
		}
		if (isPossible){
			x = p.x;
			y = p.y - 1;
			z = p.z;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}

	}

	/**
	 * Method to be ran when a player presses the down key.<br>
	 * Moves the character backwards.
	 */
	@Override
	public void moveBackwards() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if backwards is possible
			if (moves[i] == "Forward")
				isPossible = true;
		}
		if (isPossible){
			x = p.x;
			y = p.y + 1;
			z = p.z;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}

	}
	
	/**
	 * Method to be ran when a player presses the pageUp key.<br>
	 * Moves the character to the upper floor.
	 */
	public void moveUp() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if up is possible
			if (moves[i] == "Up")
				isPossible = true;
		}
		if (isPossible){
			x = p.x - 1;
			y = p.y;
			z = p.z;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}
	}
	
	/**
	 * Method to be ran when a player presses the pageDown key.<br>
	 * Moves the character to the floor beneath.
	 */
	public void moveDown() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if down is possible
			if (moves[i] == "Up")
				isPossible = true;
		}
		if (isPossible){
			x = p.x + 1;
			y = p.y;
			z = p.z;
			p.x = x;
			p.y = y;
			p.z = z;
			moveCharacter(p);
		}
	}
	
	public void displaySolution(ArrayList<String> solutionStrings)
	{
		for (String step : solutionStrings)
		{
			Position tempPosition = new Position(0,0,0);
			List<String> coordinates = new ArrayList<String>(Arrays.asList(step.split(",")));
			tempPosition.x = (Integer.parseInt(coordinates.get(0)));
			tempPosition.y = (Integer.parseInt(coordinates.get(1)));
			tempPosition.z = (Integer.parseInt(coordinates.get(2)));
			solution.add(tempPosition);
		}
		getDisplay().syncExec(new Runnable() {
			public void run() {
				redraw();				
			}
		});
	}
}

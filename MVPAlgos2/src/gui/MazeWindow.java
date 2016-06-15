package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import presenter.Presenter;
import presenter.Properties;
import view.View;

/**
 * Main window to present the game.
 * <p>
 * Contains a menu on the left, and a game on the right side.<br>
 * Each part can be easily modified by using different classes.
 */
public class MazeWindow extends BasicWindow implements View {

	String userInput;
	MazeDisplayer3d maze3dDisplay;
	HashMap<String, String> inputHashMap;
	String defaultMazeSolveAlgo = "BFS";
	String defaultMazeName = "aaa";
	String defaultX = "3";//z
	String defaultY = "4";//y
	String defaultZ = "10";//x
	String tempMazeName = null;
	
	/**
	 * Constructor to set window size.
	 * @param width
	 * @param height
	 */
	public MazeWindow(int width, int height) {
		super(width, height);
	}

	/**
	 * Sets all widgets in the window.
	 */
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2, false));
		setUserInput("load_properties Properties.xml");
		
		// Generate maze
		Button generateMaze  = new Button(shell, SWT.PUSH);
		generateMaze.setText("Generate Maze");
		generateMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		generateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GenerateMazeDialog generateMazeDialog = new GenerateMazeDialog(shell, defaultMazeName, defaultX, defaultY, defaultZ);
				inputHashMap = generateMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("generate_3d_maze " + inputHashMap.get("mazeName") + " " + inputHashMap.get("x") +
							" " + inputHashMap.get("y") + " " + inputHashMap.get("z"));					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		maze3dDisplay = new MazeDisplayer3d(shell, SWT.BORDER);
		maze3dDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 11));

		// Display Maze button
		Button displayMaze = new Button(shell, SWT.PUSH);
		displayMaze.setText("Display a maze");
		displayMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		displayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DisplayMazeDialog displayMazeDialog = new DisplayMazeDialog(shell);
				inputHashMap = displayMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("display " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Save Maze button
		Button saveMaze = new Button(shell, SWT.PUSH);
		saveMaze.setText("Save Maze");
		saveMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		saveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				SaveMazeDialog saveMazeDialog = new SaveMazeDialog(shell);
				inputHashMap = saveMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("save_maze " + inputHashMap.get("mazeName") + " " + inputHashMap.get("filePath"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Load Maze button
		Button loadMaze = new Button(shell, SWT.PUSH);
		loadMaze.setText("Load Maze");
		loadMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		loadMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoadMazeDialog loadMazeDialog = new LoadMazeDialog(shell);
				inputHashMap = loadMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("load_maze " + inputHashMap.get("filePath") + " " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


		// Maze size button
		Button mazeSize = new Button(shell, SWT.PUSH);
		mazeSize.setText("Maze Size");
		mazeSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		mazeSize.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MazeSizeDialog mazeSizeDialog = new MazeSizeDialog(shell);
				inputHashMap = mazeSizeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("maze_size " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// File Size button 
		Button fileSize = new Button(shell, SWT.PUSH);
		fileSize.setText("File Size");
		fileSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		fileSize.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e){
				FileSizeDialog fileSizeDialog = new FileSizeDialog(shell);
				inputHashMap = fileSizeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("file_size " + inputHashMap.get("filePath"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Solve Maze button
		Button solveMaze = new Button(shell, SWT.PUSH);
		solveMaze.setText("Solve Maze");
		solveMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		solveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				SolveMazeDialog solveMazeDialog = new SolveMazeDialog(shell, defaultMazeSolveAlgo);
				inputHashMap = solveMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("solve_maze " + inputHashMap.get("mazeName") + " " + inputHashMap.get("algorithm"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Display Solution button
		Button displaySolution = new Button(shell, SWT.PUSH);
		displaySolution.setText("Display Solution");
		displaySolution.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		displaySolution.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (maze3dDisplay.getMazeName() != null)
				{
					maze3dDisplay.setDisplayedSolution(true);
					setUserInput("display_solution " + maze3dDisplay.getMazeName());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Load Properties button
		Button loadProperties = new Button(shell, SWT.PUSH);
		loadProperties.setText("Load Properties");
		loadProperties.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		loadProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoadPropertiesDialog loadPropertiesDialog = new LoadPropertiesDialog(shell);
				inputHashMap = loadPropertiesDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("load_properties " + inputHashMap.get("filePath"));					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Exit button
		Button exit = new Button(shell, SWT.PUSH);
		exit.setText("Exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		exit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setUserInput("exit");
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		// Move the character on the board
		maze3dDisplay.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if (!maze3dDisplay.hasWon)
				{
					if (e.keyCode == SWT.ARROW_UP) {
						maze3dDisplay.moveLeft();
					}
					if (e.keyCode == SWT.ARROW_DOWN) {
						maze3dDisplay.moveRight();
					}
					if (e.keyCode == SWT.ARROW_LEFT) {
						maze3dDisplay.moveForwards();
					}
					if (e.keyCode == SWT.ARROW_RIGHT) {
						maze3dDisplay.moveBackwards();
					}
					if (e.keyCode == SWT.PAGE_UP) {
						maze3dDisplay.moveUp();
					}
					if (e.keyCode == SWT.PAGE_DOWN) {
						maze3dDisplay.moveDown();
					}
				}
				
			}
		});
		
		// Dispose listener for closing the window
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				setUserInput("exit");
				shell.dispose();
			}
		});
	}

	/**
	 * Sets the userInput.
	 */
	public void setUserInput(String userInput) {
		setChanged();
		notifyObservers(userInput);
	}

	@Override
	public void displayMessage(String message) {
		display.syncExec(new Runnable() {
			public void run() {
				new MessagePopUp(shell, message);
			}
		});
		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		maze3dDisplay.setMaze(maze);
		maze3dDisplay.setMazeName(tempMazeName);
		maze3dDisplay.redraw();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub
		
	}
}

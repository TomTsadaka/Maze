package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import view.View;

public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay; 
	
	@Override
	public void displayMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		this.run();
	}

	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);
		
		Composite buttonsGroup = new Composite(shell, SWT.BORDER);
		buttonsGroup.setLayout(new FillLayout(SWT.VERTICAL));
		
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				/*MessageBox msg = new MessageBox(shell);
				msg.setMessage("Generating maze...");				
				msg.open();*/
				
				String commandLine = "generate_3d_maze testmaze 3 20 20";
				setChanged();
				notifyObservers(commandLine);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		Button btnSolveMaze = new Button(buttonsGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		Button btnDisplayMaze = new Button(buttonsGroup, SWT.PUSH);
		btnDisplayMaze.setText("Display maze");
		
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String command = "display_maze aaa";
				setChanged();
				notifyObservers(command);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnExit = new Button(buttonsGroup, SWT.PUSH);
		btnExit.setText("Exit");
		
		mazeDisplay = new Maze3DDisplay(shell, SWT.BORDER);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub
		
	}

}

package gui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to set all data needed for the Solve Maze operation.
 */
public class SolveMazeDialog extends BasicDialog {

	String defaultMazeSolvingAlgorithm;
	
	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public SolveMazeDialog(Shell parent, String mazeSolvingAlgorithm)
	{
		// Pass the default styles here
		this(parent, mazeSolvingAlgorithm, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public SolveMazeDialog(Shell parent, String mazeSolvingAlgorithm, int style)
	{
		// Let users override the default styles
		super(parent, style);
		setText("Solve maze");
		defaultMazeSolvingAlgorithm = mazeSolvingAlgorithm;
	}
	
	/**
	 * Creates the contents of the sub menu.
	 * @param shell the dialog window
	 */
	@Override
	protected void createContents(Shell shell) {
		shell.setLayout(new GridLayout(2, true));
	    
	    // Display maze name label
	    Label mazeNameLabel = new Label(shell, SWT.NONE);
	    mazeNameLabel.setText("Name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text mazeNameText = new Text(shell, SWT.BORDER);
	    mazeNameText.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));	 
	    mazeNameText.addListener(SWT.Verify, new Listener()
	    {
		    @Override
			public void handleEvent(Event e)
		    {
		    	e = checkSpaces(e);
			}
		});
	    
	    // Display the solution type label
	    Label solutionTypeLabel = new Label(shell, SWT.NONE);
	    solutionTypeLabel.setText("Name:");
	    solutionTypeLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Best First Search algorithm
	    Button bfs = new Button(shell, SWT.RADIO);
	    bfs.setText("Best First Search Algorithm");
	    bfs.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
	    
	    // Breadth First Search algorithm
	    Button breadthFS = new Button(shell, SWT.RADIO);
	    breadthFS.setText("Breadth First Search Algorithm");
	    breadthFS.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
	    
	    // DFS algorithm
	    Button dfs = new Button(shell, SWT.RADIO);
	    dfs.setText("DFS Algorithm");
	    dfs.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
	    
	    // Mark default algorithm
	    if (defaultMazeSolvingAlgorithm.equals("BFS"))
	    {
	    	bfs.setSelection(true);
	    }
	    else if (defaultMazeSolvingAlgorithm.equals("BreadthFirstSearch"))
	    {
	    	breadthFS.setSelection(true);
	    }
	    else if (defaultMazeSolvingAlgorithm.equals("DFS"))
	    {
	    	dfs.setSelection(true);
	    }
    
	    // Create the OK button and add a handler, so that pressing it will set the HashMap with the user's input
	    Button solve = new Button(shell, SWT.PUSH);
	    solve.setText("Solve");
	    solve.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    solve.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event)
	      {
	    	  input = new HashMap<String,String>();
	    	  input.put("mazeName", mazeNameText.getText());
	    	  if (bfs.getSelection())
	    		  input.put("algorithm", "BFS");
	    	  if (breadthFS.getSelection())
	    		  input.put("algorithm", "BreadthFirstSearch");
	    	  if (dfs.getSelection())
	    		  input.put("algorithm", "DFS");
	    	  shell.close();
	      }
	    });

	    // Create the cancel button and add a handler so that pressing it will set the HashMap to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, true, 1, 1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the OK button as default
	    shell.setDefaultButton(solve);	  
	}
}

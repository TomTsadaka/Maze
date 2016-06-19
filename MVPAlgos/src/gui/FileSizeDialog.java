package gui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to set all data needed for the File Size operation.
 */
public class FileSizeDialog extends BasicDialog {

	private static final String[] FILTER_TYPES = {"Maze3d files (*.maz)"};
	private static final String[] FILTER_SUFFIX = {"*.maz"};
	
	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public FileSizeDialog(Shell parent)
	{
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public FileSizeDialog(Shell parent, int style)
	{
		// Let users override the default styles
		super(parent, style);
		setText("File size");
	}
	
	/**
	 * Creates the contents of the sub menu.
	 * @param shell the dialog window
	 */
	@Override
	protected void createContents(Shell shell) {
		shell.setLayout(new GridLayout(3, true));
	    
		 // Display file path label
	    Label filePathLabel = new Label(shell, SWT.NONE);
	    filePathLabel.setText("File path:");
	    filePathLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 3, 1));
	    
	    // Display the file path box
	    final Text filePathText = new Text(shell, SWT.BORDER);
	    filePathText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));	 
    
	    // Display the browse button to select from where to load the maze
	    Button browse = new Button(shell, SWT.PUSH);
	    browse.setText("Browse...");
	    browse.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    browse.addSelectionListener(new SelectionAdapter()
	    {
	    	public void widgetSelected(SelectionEvent event)
	    	{
	    		FileDialog dlg = new FileDialog(shell, SWT.OPEN);
	            dlg.setFilterNames(FILTER_TYPES);
	            dlg.setFilterExtensions(FILTER_SUFFIX);
	            String fn = dlg.open();
	            if (fn != null) {
	            	filePathText.setText(fn);
	            }
	    	}
	    });
	    
	    // Create the OK button and add a handler,
	    // so that pressing it will set the HashMap with the user's input
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter()
	    {
	    	public void widgetSelected(SelectionEvent event)
	    	{
	    		input = new HashMap<String,String>();
	    		input.put("filePath", filePathText.getText());
	    		shell.close();
	    	}
	    });

	    // Create the cancel button and add a handler,
	    // so that pressing it will set the HashMap to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, true, 1, 1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the Load button as default
	    shell.setDefaultButton(ok);
	}
}

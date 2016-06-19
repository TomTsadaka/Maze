package presenter;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Display File Size command class
 */

public class DisplayFileSizeCommand implements Command {

	private Model model;
	private View view;
	
	DisplayFileSizeCommand(Model model, View view)
	{
		this.model=model;
		this.view=view;
	}
	
	@Override
	public void doCommand(String[] args) {
		if(args.length!=2)
		{
			view.displayMessage("Arguments inserted wrong. Example: file_size <filename> \n");
		}
		else
		{
			model.displayFileSize(args[1]);
		}

	}

}

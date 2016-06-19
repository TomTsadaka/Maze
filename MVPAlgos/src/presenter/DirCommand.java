package presenter;

import java.io.File;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Dir command class
 */
public class DirCommand implements Command {

	
	private Model model;
	private View view;
	
	public DirCommand(Model model, View view)
	{
		this.model=model;
		this.view=view;
	}
	
	@Override
	public void doCommand(String[] argsArray) {
		if(!(argsArray.length==2))
		{
			view.displayMessage("Example: dir <path>");
			view.displayMessage("Parameters inserted wrong . Type again");
		}
		else
		{
			model.dir(argsArray[1]);
			view.displayMessage(model.getMessage());
		}
		

	}

}

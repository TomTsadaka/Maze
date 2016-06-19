package presenter;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Load Properties command class
 */

public class LoadPropertiesCommand implements Command {

	private Model model;
	private View view;
	
	public LoadPropertiesCommand(Model model, View view) 
	{
		this.model=model;
		this.view = view;
	}
	
	
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2)
		{
			String filePath = args[1];
			model.loadProperties(filePath);				
		}
		else
		{
			view.displayMessage("Error occured for Load Properties funcionality.\n");
		}
	
	}

}

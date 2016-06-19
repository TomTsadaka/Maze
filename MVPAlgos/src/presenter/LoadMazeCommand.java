package presenter;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Load maze command class
 */

public class LoadMazeCommand implements Command {

	private Model model;
	private View view;
	
	LoadMazeCommand(Model model, View view)
	{
		this. model=model;
		this.view=view;
		
	}
	
	@Override
	public void doCommand(String[] args) {
		if(!(args.length==3))
		{
			view.displayMessage("Arguments inserted wrong. Example load_maze <fileName> <mazeName>\n");
		}
		else
		{
			String fileName=args[1];
			String name=args[2];
			model.loadMaze(fileName, name);
		}

	}

}

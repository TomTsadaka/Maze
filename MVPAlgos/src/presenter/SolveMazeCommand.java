package presenter;

import model.Model;
import view.View;

public class SolveMazeCommand implements Command {

	
	private Model model;
	private View view;
	
	public SolveMazeCommand(Model model, View view) {
		
		this.model = model;
		this.view = view;		
	}

	
	@Override
	public void doCommand(String[] args) 
	{
		if(args.length!=3)
		{
			view.displayMessage("Bad input. Example: Solve <name> <algorithm> \n");
		}
		else
		{
			String name=args[1];
			String algorithm=args[2];
			model.solveMaze(name, algorithm);
		}

	}

}

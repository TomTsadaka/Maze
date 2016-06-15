package presenter;

import model.Model;
import view.View;

public class DisplayMazeSizeCommand implements Command {

	private Model model;
	private View view;
	
	public DisplayMazeSizeCommand(Model model, View view) {		
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2) {
			try {
				model.displayMazeSize(args[1]);
			} catch (Exception e) {
				e.printStackTrace();
				view.displayMessage("Bad command input");
				view.displayMessage("Command sould be: maze_size <name>");
			}
		}else{
			view.displayMessage("argument error: maze_Size <name>");
		}

	}


}

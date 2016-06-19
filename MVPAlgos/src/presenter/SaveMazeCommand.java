package presenter;

import model.Model;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Save maze command class
 */

public class SaveMazeCommand implements Command {

	private Model model;
	
	public SaveMazeCommand(Model model) {
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[1];
		String fileName = args[2];
		
		model.saveMaze(name, fileName);
	}

}

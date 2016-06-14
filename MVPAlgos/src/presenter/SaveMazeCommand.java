package presenter;

import model.Model;

public class SaveMazeCommand implements Command {

	private Model model;
	
	public SaveMazeCommand(Model model) {
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[0];
		String fileName = args[1];
		
		model.saveMaze(name, fileName);
	}

}

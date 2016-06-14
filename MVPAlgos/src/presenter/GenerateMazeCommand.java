package presenter;

import model.Model;

public class GenerateMazeCommand implements Command {
	private Model model;
	public GenerateMazeCommand(Model model) {
		this.model = model;
	}	
	
	@Override
	public void doCommand(String[] args) {
		String name = args[1];
		int x = Integer.parseInt(args[2]);
		int y = Integer.parseInt(args[3]);
		int z = Integer.parseInt(args[4]);
		
		model.generateMaze(name, x,y,z);
	//	System.out.println("maze "+ args[1]+" generated");
	}

}

package presenter;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class DisplayMazeCommand implements Command {

	private Model model;
	private View view;
	
	public DisplayMazeCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[1];
		view.displayMaze(model.getMaze(name));
	}

}

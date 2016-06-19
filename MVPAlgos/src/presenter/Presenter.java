package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * The presenter class
 */

public class Presenter implements Observer {
	
	private Model model;
	private View view;
	private HashMap<String, Command> viewCommands;
	private HashMap<String, Command> modelCommands;
	
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		
		buildCommands();
	}
	
	/**
	 * Build the commands
	 */
	private void buildCommands() {
		viewCommands = new HashMap<String, Command>();
		viewCommands.put("generate_3d_maze", new GenerateMazeCommand(model));
		viewCommands.put("dir", new DirCommand(model, view));
		viewCommands.put("display", new DisplayMazeCommand(model, view));
		viewCommands.put("save_maze", new SaveMazeCommand(model));
		viewCommands.put("load_maze", new LoadMazeCommand(model, view));
		viewCommands.put("display_solution", new DisplaySolutionCommand(model, view));
		viewCommands.put("display_cross_section_by", new DisplayCrossSection(model, view));
		viewCommands.put("exit", new ExitCommand(model, view));
		viewCommands.put("file_size", new DisplayFileSizeCommand(model, view));
		viewCommands.put("maze_size", new DisplayMazeSizeCommand(model,view));
		viewCommands.put("solve_maze", new SolveMazeCommand(model, view));
		viewCommands.put("load_properties", new LoadPropertiesCommand(model, view));
		
		modelCommands = new HashMap<String, Command>();
		modelCommands.put("display_message", new DisplayMessageCommand(model, view));
	}

	/**
	 * Update command
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o == model) {
			String commandName = (String)arg;
			Command command = modelCommands.get(commandName);
			command.doCommand(null);
		}
		else if (o == view) {
			String commandLine = (String)arg;
			String[] arr = commandLine.split(" ");
			String commandName = arr[0];
			
			Command command = viewCommands.get(commandName);
			command.doCommand(arr);
		}
	}


}

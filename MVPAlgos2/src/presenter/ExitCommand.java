package presenter;

import model.Model;
import view.View;

public class ExitCommand implements Command {
	
	private Model model;
	private View view;
	
	public ExitCommand(Model model, View view) {
		this.model=model;
		this.view=view;
		
	}

	@Override
	public void doCommand(String[] argsArray) {
		
		model.exitThreads();
		
	}

}

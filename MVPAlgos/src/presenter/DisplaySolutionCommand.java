package presenter;

import java.util.ArrayList;
import java.util.Arrays;

import algorithms.search.Solution;
import domains.State;
import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Display solution command class
 */

public class DisplaySolutionCommand implements Command {

	private Model model;
	private View view;
	
	public DisplaySolutionCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		if(args.length!=2)
		{
			view.displayMessage("Wrong input. Example: display_solution <name> \n");
		}
		else
		{
			String name = args[1];
			view.displaySolution(model.getSolution(name));
		}
	}

}

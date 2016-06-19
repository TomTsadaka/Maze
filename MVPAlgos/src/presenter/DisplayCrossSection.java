package presenter;

import model.Model;
import view.View;

/**
 * 
 * @author Eyal Gurbanov and Tom Tsadaka 
 * Display Cross Section command class
 */

public class DisplayCrossSection implements Command {

	private Model model;
	private View view;
	
	public DisplayCrossSection(Model model, View view) 
	{
		this.model=model;
		this.view=view;
	}
	
	
	@Override
	public void doCommand(String[] args) {
		if(args.length<5)
			{
				view.displayMessage("arguments inserted wrong");
				view.displayMessage("Example: display_cross_section_by_X/Y/Z <index> for <name>");
			}
			
		String name=args[4];
		int index=Integer.parseInt(args[2]);
		char axis=args[1].charAt(0);
		model.displayCrossSection(name, index, axis);
		
		

	}

}

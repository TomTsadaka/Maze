package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import gui.MazeWindow;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		Properties properties = new Properties("Properties.xml");
		properties.load();
		MyModel model = new MyModel();

		if (properties.getUserInterface().toUpperCase().equals("CLI"))
		{
			MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
			Presenter presenter = new Presenter(model, view);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		}
		else if (properties.getUserInterface().toUpperCase().equals("GUI"))
		{
			MazeWindow gui = new MazeWindow(700, 600);
			Presenter presenter = new Presenter(model, gui);
			gui.addObserver(presenter);
			model.addObserver(presenter);
			gui.run();
		}		
	}

}

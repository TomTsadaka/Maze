package boot;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Observable;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
	
		String xmlPath = "Properties.xml";
		
		
		Properties properties = null;
		try 
		{
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(xmlPath));	//read the XML file
			properties = (Properties)decoder.readObject();	//decoding
			decoder.close();
		} 	
		catch (FileNotFoundException e) 
		{		//if no properties.xml file was found in directory, generating default properties.
			System.out.println("file not found, default properties will be loaded");
			properties = new Properties();
		} 
		catch (IllegalArgumentException e)
		{
			properties = new Properties();
		}
		
			
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyView view=new MyView(in, out);
		
		MyModel model = new MyModel();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);
		
		
		Presenter presenter = new Presenter(model, view);
		presenter.setProperties(properties);
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		view.start();
		
			/*
		MyModel model = new MyModel();		
		MazeWindow view = new MazeWindow();
		
		Presenter presenter = new Presenter(model, view);
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		view.start();
		*/		
	}

}

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;

public class CLI extends Observable{
	
	private BufferedReader in;
	private Writer out;
	//private HashMap<String, Command> commands;
	
	/*public CLI(BufferedReader in, Writer out, HashMap<String, Command> commands)
	{
		this.in=in;
		this.out=out;
		//this.commands=commands;
	}*/
	
	public CLI(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;		
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				 
				try {					
					String line = null;
					do {
						out.write("Enter command: ");
						out.flush();
						line = in.readLine();
						setChanged();
						notifyObservers(line);						
					} while (!(line.equals("exit")));					 
					
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}						
			}
			
		});
		thread.start();
	};

	
}
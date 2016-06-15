package model;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Solution;
import domains.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;

public class MyModel extends Observable implements Model {
	
	//private ArrayList<Thread> threads = new ArrayList<Thread>();
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> mazeSol=new HashMap<String,Solution>();
	private HashMap<Maze3d,Solution> mazesSolutions=new HashMap<Maze3d,Solution>();
	private String message;
	private ExecutorService threadPool=Executors.newCachedThreadPool();
	private static final int threadsNum = 25;
	private Properties properties;
	private XMLDecoder xmlDecoder;
	
	
	public MyModel()
	{	
		this.threadPool = Executors.newFixedThreadPool(threadsNum);
	}
	
	public Properties getProperties() {
		return properties;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public void generateMaze(String name, int x, int y, int z) {
		threadPool.submit(new Callable<Maze3d>() {
			
			@Override
			public Maze3d call() throws Exception {
				
				Maze3d maze = null;
				if(x>30||y>30||z>30)
				{
					message="dimenstions are too high";
					setChanged();
					notifyObservers("display_message");
				}
				
				if(mazes.containsKey(name))
				{
					message="Maze "+name+" already exists";
					setChanged();
					notifyObservers("display_message");
				}
				
				else 
				{					
					MyMaze3dGenerator mg = new MyMaze3dGenerator();
					maze = mg.generate(x,y,z);
					mazes.put(name, maze);
				}
				
				message = "Maze " + name + " is ready\n";
				setChanged();
				notifyObservers("display_message");
				return maze;
			}				
		});
		
	}
	
	public Maze3d getMaze(String name) {
		return mazes.get(name);			
	}

	@Override
	public void saveMaze(String name, String fileName) {
		if (!mazes.containsKey(name)) {
			//controller.displayMessage("Maze " + name + " does not exist\n");
			message = "Maze " + name + " does not exist\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		Maze3d maze = mazes.get(name);
	
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			byte[] bytes = maze.toByteArray();
			out.write(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadMaze(String fileName, String name) {
		InputStream in = null;
		try {
			byte b[] = new byte[3];
			in = new FileInputStream(fileName);
			in.read(b, 0, b.length);
			b = new byte[((int) b[0] * (int) b[1] * (int) b[2]) + 9];
			in.close();
			in = new MyDecompressorInputStream(new FileInputStream(fileName));
			in.read(b);
			mazes.put(name, new Maze3d(b));

			this.message = "Maze " + name + " loaded from " + fileName;
			setChanged();
			notifyObservers("display_message");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (in != null)
				try {
					in.close();
				}
			catch (IOException e) 
			{
					e.printStackTrace();
			}
		}
		

	}
	
	/**
	 * Receives a path to an XML properties file and loads its content.
	 */
	@Override
	public void loadProperties(String path) {
		Properties properties = new Properties(path);
		properties.load();
		if (properties.getPropertiesSet())
		{
			//setChanged();
			//notifyObservers("Properties file successfully loaded\n");
		}
		else
		{
			setChanged();
			notifyObservers("Properties file is corrupted / doesn't exist.");
		}
	}
	
	@Override
	public void dir(String path)
	{
		
		File file=new File(path.toString());
		if(file.isFile()){
				message+=file.toString();
		}
		else{
				for (File f : file.listFiles())
				{
					message+=f.toString();
					message+="\n";

				}
	}
		setChanged();
		notifyObservers(message);
	}

	@Override
	public void displayCrossSection(String name, int index, char axis) {
		if(!(mazes.containsKey(name)))
		{
			message="Maze does not exist";
			setChanged();
			notifyObservers("display_message");
		}
		
		if(index<0)
		{
			{
				message="Index is negative";
				setChanged();
				notifyObservers("display_message");
			}
		}
		
		if(mazes.containsKey(name))
		{
			Maze3d maze=mazes.get(name);
			if(axis=='x')
			{
				if(index<=maze.getX())
				{
					int [][] crossbyX=maze.getCrossSectionByX(index);
					String msg="";
					for(int[] arr:crossbyX)
					{
						msg+=Arrays.toString(arr);
						msg+="\n";
					}
					message=msg;
					setChanged();
					notifyObservers("display_message");
				}
			}
			
			if(axis=='y')
			{
				if(index<=maze.getY())
				{
					int [][] crossbyY=maze.getCrossSectionByY(index);
					String msg="";
					for(int[] arr:crossbyY)
					{
						msg+=Arrays.toString(arr);
						msg+="\n";
					}
					message=msg;
					setChanged();
					notifyObservers("display_message");
				}
			}
			
			if(axis=='z')
			{
				if(index<=maze.getZ())
				{
					int [][] crossbyZ=maze.getCrossSectionByZ(index);
					String msg="";
					for(int[] arr:crossbyZ)
					{
						msg+=Arrays.toString(arr);
						msg+="\n";
					}
					message=msg;
					setChanged();
					notifyObservers("display_message");
				}
			}
		}	
		
	}
	
	public void displayMaze(String name)
	{
		if(!mazes.containsKey(name))
		{
			setChanged();
			notifyObservers("Maze doesn't exist.");
		}
		else
		{
			Maze3d maze=mazes.get(name);
			message=maze.toString();
			setChanged();
			notifyObservers("display_message");
		}
		
	}
	
	public void solveMaze(String name, String algorithm) {
		
		if(!mazes.containsKey(name))
		{
			message="maze "+name+" does not exits \n";
			setChanged();
			notifyObservers("display_message");
		}
		
		if(!(algorithm.toUpperCase().equals("DFS"))&&!(algorithm.toUpperCase().equals("BFS"))&&!(algorithm.toUpperCase().equals("BREADTHFIRSTSEARCH")))
		{
			message="algorithms "+algorithm+ " does not exist";
			setChanged();
			notifyObservers("display_message");
		}
		
		if(mazesSolutions.containsKey(mazes.get(name)))
		{
			message="Solution for maze "+name+" already exists";
			setChanged();
			notifyObservers("display_message");
		}
		
		threadPool.submit(new Callable<Solution>() {
			@Override
			public Solution call() throws Exception {
				Solution sol=null;
				
				MazeAdapter ma=new MazeAdapter(mazes.get(name));
				if (mazes.containsKey(name)) {
					
					if (algorithm.equals("BFS")) {
						sol=new BFS().search(ma);
						mazeSol.put(name, sol);
						mazesSolutions.put(mazes.get(name), sol);
						message=("Solution for maze " + name + " based on algorithem " + algorithm + " is ready");
					}
					else if (algorithm.equals("BreadthFirstSearch")) {
						sol= new BreadthFirstSearch().search(ma);
						mazeSol.put(name, sol);
						mazesSolutions.put(mazes.get(name), sol);
						message=("Solution for maze " + name + " based on algorithem " + algorithm + " is ready");
					}
					else if (algorithm.equals("DFS")) {
						sol= new DFS().search(ma);
						mazeSol.put(name, new DFS().search(ma));
						mazesSolutions.put(mazes.get(name), sol);
						message=("Solution for maze " + name + " based on algorithem " + algorithm + " is ready");
					}
				
				}
				else{
					message=("Maze " + name + " does not exist\n");
				}		
				
				setChanged();
				notifyObservers("display_message");
				return sol;
			}				
		});
	}
	
	public void displaySolution(String name)
	{
		if(!(mazes.containsKey(name)))
		{
			message="Maze not found";
		}
		else
		{
			Solution sol=mazeSol.get(name);
			ArrayList<State> states=sol.getStates();
			
			String solStates="";
			for(State s:states)
			{
				solStates+=s.toString();
			}
			message=solStates;
		}
		
		setChanged();
		notifyObservers("display_message");	
	}
	
	public void saveGZipMaps() {

		FileOutputStream fos = null;
		GZIPOutputStream gos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream("cache_Maps");
			gos = new GZIPOutputStream(fos);
			oos = new ObjectOutputStream(gos);

			oos.writeObject(mazes);
			oos.writeObject(mazeSol);
			oos.writeObject(mazesSolutions);

			oos.flush();
			oos.close();
			gos.close();
			fos.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadGZipMaps() {

		FileInputStream fis = null;
		GZIPInputStream gis = null;
		ObjectInputStream ois = null;

		File f = new File("cache_Maps");
		if (!f.exists())
			return;

		try {

			fis = new FileInputStream("cache_Maps");
			gis = new GZIPInputStream(fis);
			ois = new ObjectInputStream(gis);

			mazes = (HashMap<String, Maze3d>) ois.readObject();
			mazeSol = (HashMap<String, Solution>) ois.readObject();
			mazesSolutions = (HashMap<Maze3d, Solution>) ois.readObject();

			ois.close();
			gis.close();
			fis.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	public void exitThreads()
	{
		threadPool.shutdown();
		
		message="all threads have been removed successfully";
		setChanged();
		notifyObservers("display_message");
	}

	public void displayFileSize(String filename) {
		File f=new File(filename);
		if(f.exists()&&f.isFile()){
			long size=f.length();
			message=("the file: "+filename+" size is: "+size);
		}else{
			message=("file not found");
		}
		
		setChanged();
		notifyObservers("display_message");
	}

	@Override
	public void displayMazeSize(String name) {
		if(mazes.containsKey(name))
		{
		try {
				message=("the maze: '"+name+"' size is: "+mazes.get(name).toByteArray().length);
			} catch (Exception e) {
				message=(e.getMessage());
			}
		}else{
			message=("Maze with name: '"+name+"' not found");
		}
		
		setChanged();
		notifyObservers("display_message");
	}

	public Solution getSolution(String name)
	{
		Solution sol=mazeSol.get(name);
		return sol;
	}

	
	public void setProperties(Properties properties)
	{
		this.properties = properties;
		ExecutorService bufferdpool = threadPool;
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThreads());  //change the number of threads by Properties
		bufferdpool.shutdown();	// initiate orderly shutdown of temporary threadPool

	}
}

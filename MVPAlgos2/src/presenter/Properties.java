package presenter;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Properties implements Serializable {

	/** The properties path */
	private String path;
	
	/** The num of threads. */
	private int numOfThreads;
	
	/** The solve algorithm. */
	private String solveAlgorithm;
	
	/** The generate algorithm. */ 
	private String generateAlgorithm;
	
	/** The user interface. */
	private String userInterface;
	
	/** Did the properties properly loaded */
	private boolean propertiesSet = false;

	/**
	 * Instantiates a new properties.
	 */
	public Properties(String path) {
		this.path=path;
		this.numOfThreads=20;
		this.solveAlgorithm="BFS";
		this.generateAlgorithm="MyMaze3dGenerator";
		this.userInterface="CLI";
	}
	
	/**
	 * Gets the num of threads.
	 *
	 * @return the num of threads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * Sets the num of threads.
	 *
	 * @param numOfThreads the new num of threads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	
	/**
	 * Gets the solve algorithm.
	 *
	 * @return the solve algorithm
	 */
	public String getSolveAlgorithm() {
		return solveAlgorithm;
	}

	/**
	 * Sets the solve algorithm.
	 *
	 * @param solveAlgorithm the new solve algorithm
	 */
	public void setSolveAlgorithm(String solveAlgorithm) {
		this.solveAlgorithm = solveAlgorithm;
	}

	/**
	 * Gets the generate algorithm.
	 *
	 * @return the generate algorithm
	 */
	public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}

	/**
	 * Sets the generate algorithm.
	 *
	 * @param generateAlgorithm the new generate algorithm
	 */
	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}
	
	/**
	 * Gets the user interface.
	 *
	 * @return the user interface
	 */
	public String getUserInterface() {
		return userInterface;
	}

	/**
	 * Sets the user interface.
	 *
	 * @param userInterface the new view interface
	 */
	public void setUserInterface(String userInterface) {
		this.userInterface = userInterface;
	}
	
	public void save(){
		XMLEncoder e;
		try {
			e = new XMLEncoder(new FileOutputStream("Properties.xml"));
			numOfThreads = 20;
			solveAlgorithm="BFS";
			generateAlgorithm="MyMaze3dGenerator";
			userInterface="GUI";
			e.writeObject(numOfThreads);
			e.writeObject(solveAlgorithm);
			e.writeObject(generateAlgorithm);
			e.writeObject(userInterface);
			e.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void load(){
		XMLDecoder d;
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
			try {
				this.numOfThreads = (int)d.readObject();
				this.solveAlgorithm=(String)d.readObject();
				this.generateAlgorithm=(String)d.readObject();
				this.userInterface=(String)d.readObject();
				this.propertiesSet = true;
				d.close();
			} catch (Exception e1) {
				save();
			}
		}
		catch (FileNotFoundException e){
			save();
		}
	}
	
	public boolean getPropertiesSet() {
		return propertiesSet;
	}
}

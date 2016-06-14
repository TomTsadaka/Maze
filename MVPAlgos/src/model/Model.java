package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;


public interface Model {
	void generateMaze(String name, int x, int y, int z);
	void saveMaze(String name, String fileName);
	void loadMaze(String fileName, String name);
	Maze3d getMaze(String name);
	String getMessage();
	Solution getSolution(String name);
	void dir(String path);
	void displayCrossSection(String name, int index, char axis);
	void displayMaze(String name);
	void solveMaze(String name, String algorithm);
	void displaySolution(String name);
	void exitThreads();
	void displayFileSize(String filename);
	void displayMazeSize(String name);
}

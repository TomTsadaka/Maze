package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;


public interface View {
	void displayMessage(String message);
	void displayMaze(Maze3d maze);
	void start();	
	//void displayDir(String dirs);
	void displaySolution(Solution sol);
}

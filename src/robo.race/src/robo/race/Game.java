package robo.race;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import robo.race.entities.Robot;

public class Game {

	public static void main(String[] args) {
		System.out.print("Enter grid file name: ");
		
		Scanner scanner = new Scanner(System.in);
		String gridFileName = scanner.nextLine();
		
		System.out.print("Enter program name: ");
		String programFileName = scanner.nextLine();
		
		scanner.close();
		
		// Parse grid file
		Grid grid;
		try {
			GridParser gridParser = new GridParser();
			grid = gridParser.parse(gridFileName);
		} catch (FileNotFoundException exception) {
			System.out.println("File for grid not found.");
		} catch (GridParserException exception) {
			System.out.println(exception.getMessage());
		}
		
		// Parse programs
		Queue<Program> programs;
		try {
			ProgramParser programParser = new ProgramParser();
			programs = programParser.parse(programFileName);
		} catch (FileNotFoundException exception) {
			System.out.println("File for program not found.");
		} catch (ProgramParserException exception) {
			System.out.println(exception.getMessage());
		}
		
		// Get new robot's from grid and assign to programs.
		for (Program program : programs) {
			Robot robot = grid.addRobot();
			program.setRobot(robot);
		}
		
		// TODO: what if ran out of instructions?
		
		boolean gameFinished = false;
		while (!gameFinished) {
			for (int i = 0; i < 5; i++) {
				// Execute one instruction from each program
				for (Program program : programs) {
					// Execution one instruction
					Queue<RobotInstruction> instructions = program.getInstructions();
					RobotInstruction instruction = instructions.remove();
					
					Robot robot = program.getRobot();
					robot.perform(instruction);
				}
				
				// Move first program to back of queue
				Program firstProgram = programs.remove();
				programs.add(firstProgram);
				
				// Activate board
				grid.activate();
				
				// Print board
				grid.print();
			}
			
			// Check all robot's current flag number for win condition
			for (Program program : programs) {
				Robot robot = program.getRobot();
				if (robot.getNextFlagNumber() == grid.getLastFlagNumber()) {
					gameFinished = true;
					// TODO: get information on robot that won
				}
			}
			
			// Ask user if want to continue (if not won)
			System.out.println("Press ENTER to continue");
			scanner.nextLine();
		}
	}
}
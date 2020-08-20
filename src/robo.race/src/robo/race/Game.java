package robo.race;

import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

import robo.race.entities.Robot;

public class Game {

	public static void main(String[] args) {
		System.out.print("Enter grid file name: ");
		
		try (Scanner scanner = new Scanner(System.in)) {
			String gridFileName = scanner.nextLine();
			
			System.out.print("Enter program name: ");
			String programFileName = scanner.nextLine();
			
			// Parse grid file
			Grid grid;
			try {
				GridParser gridParser = new GridParser();
				grid = gridParser.parse(gridFileName);
			} catch (FileNotFoundException exception) {
				System.out.println("File for grid not found.");
				return;
			} catch (GridParserException exception) {
				System.out.println(exception.getMessage());
				return;
			}
			
			// Parse programs
			Queue<Program> programs;
			try {
				ProgramParser programParser = new ProgramParser();
				programs = programParser.parse(programFileName);
			} catch (FileNotFoundException exception) {
				System.out.println("File for program not found.");
				return;
			} catch (ProgramParserException exception) {
				System.out.println(exception.getMessage());
				return;
			}
			
			// Get new robot's from grid and assign to programs.
			for (Program program : programs) {
				Robot robot = grid.addRobot();
				program.setRobot(robot);
			}
			
			boolean gameFinished = false;
			int round = 1;
			while (!gameFinished) {
				for (int i = 0; i < 5 && !gameFinished; i++) {
					System.out.println("Round " + round + ", action " + (i + 1));
					
					// Execute one instruction from each program
					for (Program program : programs) {
						// Execution one instruction
						Queue<RobotInstruction> instructions = program.getInstructions();
						RobotInstruction instruction = instructions.poll();
						if (instruction == null) {
							System.out.println("Out of instructions!");
							gameFinished = true;
							break;
						} else {
							Robot robot = program.getRobot();
							robot.perform(instruction);
							System.out.println("Robot " + robot.getLetter() + " performed " + instruction.toString());
						}
					}
					
					// Move first program to back of queue
					Program firstProgram = programs.remove();
					programs.add(firstProgram);
					
					// Activate board
					grid.activate();
					
					// Print board
					grid.print();
					
					// Check all robot's current flag number for win condition
					for (Program program : programs) {
						Robot robot = program.getRobot();
						if (robot.getLastFlagNumber() == grid.getLastFlagNumber()) {
							System.out.println("Robot " + robot.getLetter() + " wins!");
							System.out.println("GAME OVER");
							
							gameFinished = true;
						}
					}
				}
				
				if (gameFinished == false) {
					// Ask user if want to continue (if not won)
					System.out.println("Press ENTER to continue");
					scanner.nextLine();
					
					round++;
				}
			}
		}
	}
}
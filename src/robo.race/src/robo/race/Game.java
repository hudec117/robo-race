package robo.race;

public class Game {

	public static void main(String[] args) {
		// Talha: Ask for grid file
		// System.out.print() to print messages to user
		// https://www.baeldung.com/java-console-input-output to read messages from user
		// Talha: Ask for program file
		
		// Talha: Parse grid file
			// Instantiate parser class
			// Use parser class to parse file inputted by user
		// Talha: Parse program file
		
		// Loop until game not ended
			// For 5 times
				// For each program in queue
					// Execute 1 instruction
					// If robot moved
						// Check what entity robot is sitting on
							// If pit, destroy robot
							// If flag and is next flag, increase robot next flag number
				// Dequeue program and enqueue it
				// Activate board
				// Print board
			// Check all robot's current flag number for win condition
			// Ask user if want to continue
	}
}
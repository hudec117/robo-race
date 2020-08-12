package robo.race;

import java.util.Queue;

public class Program {
	String playerName;
	Queue<RobotInstruction> instructions;
	 
	public Program(String playerName, Queue<RobotInstruction> instructions) {
		this.playerName = playerName;
		this.instructions = instructions;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public Queue<RobotInstruction> getInstructions() {
		return instructions;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setInstructions(Queue<RobotInstruction> instructions) {
		this.instructions = instructions;
	}
}

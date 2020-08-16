package robo.race;

import java.util.Queue;
//********************************************************
//  Name: Program
//  Description: Stores a players name and the instructions
//			     for their robot to follow.
//********************************************************
public class Program {
	String playerName;
	Queue<RobotInstruction> instructions;
	 
	public Program(String playerName, Queue<RobotInstruction> instructions) {
		this.playerName = playerName;
		this.instructions = instructions;
	}
	
	//Return player name
	public String getPlayerName() {
		return playerName;
	}
	
	//Return instructions
	public Queue<RobotInstruction> getInstructions() {
		return instructions;
	}
	
	//Set the players name
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	//Set the instructions
	public void setInstructions(Queue<RobotInstruction> instructions) {
		this.instructions = instructions;
  }
}




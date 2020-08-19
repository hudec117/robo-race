package robo.race;

import java.util.Queue;

import robo.race.entities.Robot;
//********************************************************
//  Name: Program
//  Description: Stores a players name and the instructions
//			     for their robot to follow.
//********************************************************
public class Program {
	String playerName;
	Robot robot;
	Queue<RobotInstruction> instructions;
	 
	public Program(String playerName, Queue<RobotInstruction> instructions) {
		this.playerName = playerName;
		this.instructions = instructions;
	}
	
	//Return player name
	public String getPlayerName() {
		return playerName;
	}
	
	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
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




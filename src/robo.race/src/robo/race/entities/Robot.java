package robo.race.entities;

import robo.race.Coordinate;
import robo.race.Grid;
import robo.race.RobotInstruction;

public class Robot extends GridEntity {
	Coordinate startingPosition;
	Grid grid;
	CompassDirection heading = CompassDirection.NORTH;
	int lastFlagNum = 0;
	char letter;
	RobotInstruction previousInstruction;
	
	//Constructor
	public Robot(Grid grid, Coordinate startingPosition) {
		this.startingPosition = startingPosition;
		this.setCurrentPosition(startingPosition);
		this.grid = grid;
	}
	
	//Perform instruction
	public void perform(RobotInstruction instruction) {
		Coordinate pos = this.getCurrentPosition();
		switch(instruction)
		{
		case Forward: //Move Robot forward
			this.previousInstruction = RobotInstruction.Forward;
			switch(heading) //Check which way robot is facing before moving forward
            { 
                case NORTH: 
                	Coordinate newPosN = new Coordinate(pos.getX()-1, pos.getY());
                	grid.moveRobot(this, newPosN);
                    break; 
                case EAST: 
                	Coordinate newPosE = new Coordinate(pos.getX(), pos.getY()+1);
                	grid.moveRobot(this, newPosE);
                    break; 
                case SOUTH: 
                	Coordinate newPosS = new Coordinate(pos.getX()+1, pos.getY());
                	grid.moveRobot(this, newPosS);
                    break;
                case WEST: 
                	Coordinate newPosW = new Coordinate(pos.getX(), pos.getY()-1);
                	grid.moveRobot(this, newPosW);
                    break; 
            } 
			break;
		case Backward: //Move Robot backwards
			this.previousInstruction = RobotInstruction.Backward;
			switch(heading) //Check which way robot is facing before moving backwards
            { 
                case NORTH: 
                	Coordinate newPosN = new Coordinate(pos.getX()+1, pos.getY());
                	grid.moveRobot(this, newPosN);
                    break; 
                case EAST: 
                	Coordinate newPosE = new Coordinate(pos.getX(), pos.getY()-1);
                	grid.moveRobot(this, newPosE);
                    break; 
                case SOUTH: 
                	Coordinate newPosS = new Coordinate(pos.getX()-1, pos.getY());
                	grid.moveRobot(this, newPosS);
                    break;
                case WEST: 
                	Coordinate newPosW = new Coordinate(pos.getX(), pos.getY()+1);
                	grid.moveRobot(this, newPosW);
                    break; 
            } 
			break;
		case RotateLeft: //Rotate robot left
			this.rotate(RotationDirection.AntiClockwise);
			break;
		case RotateRight: //Rotate robot right
			this.rotate(RotationDirection.Clockwise);
			break;
		case UTurn:
			switch(heading) //Rotate opposite way the Robot is currently facing
            { 
                case NORTH: 
                	this.heading = CompassDirection.SOUTH;
                    break; 
                case EAST: 
                	this.heading = CompassDirection.WEST;
                    break; 
                case SOUTH: 
                	this.heading = CompassDirection.NORTH; 
                    break;
                case WEST: 
                	this.heading = CompassDirection.EAST;
                    break; 
            }
			break;
		case Wait:
			//Do nothing
			break;
		}
	}
	
	//Rotate the robot
	public void rotate(RotationDirection direction) {
		if(direction == RotationDirection.Clockwise) {
			switch(heading) //If rotating clockwise
            { 
                case NORTH: 
                	this.heading = CompassDirection.EAST;
                    break; 
                case EAST: 
                	this.heading = CompassDirection.SOUTH;
                    break; 
                case SOUTH: 
                	this.heading = CompassDirection.WEST; 
                    break;
                case WEST: 
                	this.heading = CompassDirection.NORTH;
                    break; 
            } 
		} 
		else if(direction == RotationDirection.AntiClockwise) {
			switch(heading) //If rotating anticlockwise
            { 
                case NORTH: 
                	this.heading = CompassDirection.WEST;
                    break; 
                case EAST: 
                	this.heading = CompassDirection.NORTH;
                    break; 
                case SOUTH: 
                	this.heading = CompassDirection.EAST; 
                    break;
                case WEST: 
                	this.heading = CompassDirection.SOUTH;
                    break; 
            } 
		}
	}
	
	//Destroy the robot
	public void destroy() {
		//Set back to facing north and move back to starting position
		this.heading = CompassDirection.NORTH;
		//Check if starting position is occupied, if so try NESW positions
		if(grid.isRobotInPosition(startingPosition) != 0) {
			Coordinate posN = new Coordinate(startingPosition.getX()-1, startingPosition.getY());
			Coordinate posE = new Coordinate(startingPosition.getX(), startingPosition.getY()+1);
			Coordinate posS = new Coordinate(startingPosition.getX()+1, startingPosition.getY());
			Coordinate posW = new Coordinate(startingPosition.getX(), startingPosition.getY()-1);
			if(grid.checkIfOutOfBounds(posN) == false && grid.isRobotInPosition(posN) == 0) {
				grid.moveRobot(this, posN);
			} else if(grid.checkIfOutOfBounds(posE) == false && grid.isRobotInPosition(posE) == 0) {
				grid.moveRobot(this, posE);
			} else if(grid.checkIfOutOfBounds(posS) == false && grid.isRobotInPosition(posS) == 0) {
				grid.moveRobot(this, posS);
			} else if(grid.checkIfOutOfBounds(posW) == false && grid.isRobotInPosition(posW) == 0) {
				grid.moveRobot(this, posW);
			}
		} else { //If starting position not occupied, move robot there
			grid.moveRobot(this, startingPosition);
		}
		
		System.out.println("Robot " + letter + " destroyed.");
	}
	
	//Returns next flag number
	public int getLastFlagNumber() {
		return this.lastFlagNum;
	}
	
	//Sets the next flag number
	public void setLastFlagNumber(int lastFlagNum) {
		this.lastFlagNum = lastFlagNum;
	}
	
	public Coordinate getStartingPosition() {
		return this.startingPosition;
	}
	
	public CompassDirection getCompassDirection() {
		return heading;
	}
	
	public char getLetter() {
		return this.letter;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public RobotInstruction getPreviousInstruction() {
		return this.previousInstruction;
	}
	
	@Override
	public void act(Robot robot) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void react(Robot robot) {
		throw new UnsupportedOperationException();
		
	}
	
}
package robo.race.entities;

import robo.race.Coordinate;
import robo.race.Grid;
import robo.race.RobotInstruction;

public class Robot extends GridEntity {
	Coordinate startingPosition;
	Grid grid;
	CompassDirection heading = CompassDirection.NORTH;
	int nextFlagNum = 1;
	char letter;
	
	//Constructor
	public Robot(Grid grid, Coordinate startingPosition) {
		this.startingPosition = startingPosition;
		this.setCurrentPosition(startingPosition);
		this.grid  = grid;
	}
	
	//Perform instruction
	public void perform(RobotInstruction instruction) {
		Coordinate pos = this.getCurrentPosition();
		switch(instruction)
		{
		case Forward: //Move Robot forward
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
		grid.moveRobot(this, startingPosition);
		
	}
	
	//Returns next flag number
	public int getNextFlagNumber() {
		return this.nextFlagNum;
	}
	
	//Sets the next flag number
	public void setNextFlagNumber(int nextFlagNum) {
		this.nextFlagNum = nextFlagNum;
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
	
	@Override
	public void act(Robot robot) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void react(Robot robot) {
		throw new UnsupportedOperationException();
		
	}
	
}

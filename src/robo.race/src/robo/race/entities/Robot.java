package robo.race.entities;

import robo.race.Coordinate;
import robo.race.Grid;
import robo.race.RobotInstruction;

public class Robot extends GridEntity {
	Coordinate startingPosition;
	CompassDirection heading = CompassDirection.NORTH;
	int nextFlagNum = 1;
	
	//Constructor
	public Robot(Grid grid, Coordinate startingPosition) {
		this.startingPosition = startingPosition;
	}
	
	//Perform instruction
	public void perform(RobotInstruction instruction) {
		switch(instruction)
		{
		case Forward: //Move Robot forward
			//Grid.moveRobot()
			break;
		case Backward: //Move Robot backwards
			//Grid.moveRobot(robot, newCoordinate)
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
		//Grid.moveRobot(startingPosition);
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
	
	@Override
	public void act(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void react(Robot robot) {
		// TODO Auto-generated method stub
		
	}
	
}

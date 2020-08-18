package robo.race;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import robo.race.entities.CompassDirection;
import robo.race.entities.GridEntity;
import robo.race.entities.Robot;
import robo.race.entities.StartingPosition;

public class Grid {
	GridEntity[][] entities;
	Map<Robot, Coordinate> robots;
	Queue<StartingPosition> startingPositions;
	
	//Constructor
	public Grid (GridEntity[][] entities) {
		this.entities = entities;
		this.robots = new HashMap<Robot, Coordinate>();
		linkEntitiesToGrid(); //Associates each entity with the grid
		this.startingPositions = populateStartingPositions();
	}
	
	private Queue<StartingPosition> populateStartingPositions() {
		Queue<StartingPosition> startingPosQueue = new LinkedList<StartingPosition>();
		for (GridEntity[] row : entities) {
			for (GridEntity col : row) {
				if(col instanceof StartingPosition) {
					startingPosQueue.add((StartingPosition) col);
				}
			}
		}
		return startingPosQueue;
	}
	
	private void linkEntitiesToGrid() {
		for (GridEntity[] row : entities) {
			for (GridEntity col : row) {
				if(col != null) {
					col.setGrid(this);
				}
			}
		}
	}
	
	//Print out the grid
	public void print() {
        String gridString = ""; //Stores the grid
		for (GridEntity[] row : entities) {
            for (GridEntity col : row) { //Loop through each row and add toString of entity
                if(col == null) {
                	gridString += ".";
                } else {
                	gridString += col.toString();
                }
            }
            gridString += "\n";
        }
		System.out.println(gridString);
	}
	
	//Adds robot to the robots map with its coordinate
	public void addRobot(Robot robot) {
		//Add robot to map
		robots.put(robot, robot.getStartingPosition());
		robot.setLetter(startingPositions.poll().getLetter());
		robot.setCurrentPosition(robot.getStartingPosition()); //update robots current position
	}
	
	//Updates robot map with new coordinates
	public void moveRobot(Robot robot, Coordinate newCoordinate) {
		//Check if robot already in new position
		for(Robot r : robots.keySet()) {
			Coordinate oldPosition = robots.get(r);
			//If new coordinates equal to a position where there is already a robot, move original robot
			if(oldPosition.getX() == newCoordinate.getX() && oldPosition.getY() == newCoordinate.getY() && !(r.equals(robot))) {
				//Determine which way robot is facing and move them that way 
				CompassDirection direction = robot.getCompassDirection();
				switch(direction) 
				{ 
	                case NORTH: 
	                	Coordinate coordinateN = new Coordinate(oldPosition.getX(), oldPosition.getY()-1);
	                	moveRobot(r, coordinateN);
	                    break; 
	                case EAST: 
	                	Coordinate coordinateE = new Coordinate(oldPosition.getX()+1, oldPosition.getY());
	                	moveRobot(r, coordinateE);
	                    break; 
	                case SOUTH: 
	                	Coordinate coordinateS = new Coordinate(oldPosition.getX(), oldPosition.getY()+1);
	                	moveRobot(r, coordinateS);
	                    break;
	                case WEST: 
	                	Coordinate coordinateW = new Coordinate(oldPosition.getX()-1, oldPosition.getY());
	                	moveRobot(r, coordinateW);
	                    break; 
	            } 
			}
				
		}
		
		//Check in bounds
		if ((newCoordinate.getX() >= 0 && newCoordinate.getX() < entities.length) &&
		(newCoordinate.getY() >= 0 && newCoordinate.getY() < entities[newCoordinate.getX()].length)) {
			robots.put(robot, newCoordinate); //Update position in map
			robot.setCurrentPosition(newCoordinate);//Update robots own position
		} else {
			robot.destroy(); //Destroy robot if out of bounds
			
		}
		
		
	}
	
	//Get entity by its coordinates
	public GridEntity getEntity(Coordinate coordinate) {
		GridEntity entity = entities[coordinate.getX()][coordinate.getY()];
		if(entity != null) {
			return entity;
		} else {
			return null; //No entity at this position
		}
		 
	}
	
	//Activate entities that robots are on
	public void activate() {
		for (Robot robot : robots.keySet()) {
			Coordinate position = robots.get(robot);
			GridEntity entity = this.getEntity(position);
			if(entity != null) {
				entity.act(robot);
			}
			
		}
	}
	
	public Map<Robot, Coordinate> getRobots(){
		return robots;
	}

}
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
import robo.race.entities.Flag;
import robo.race.entities.Gear;
import robo.race.entities.GridEntity;
import robo.race.entities.Pit;
import robo.race.entities.Robot;
import robo.race.entities.StartingPosition;
//********************************************************
//Name: Grid
//Description: Models a grid with entities
//
//********************************************************
public class Grid {
	GridEntity[][] entities;
	Map<Robot, Coordinate> robots;
	Queue<StartingPosition> startingPositions;
	
	//Constructor
	public Grid (GridEntity[][] entities) {
		this.entities = entities;
		this.robots = new HashMap<Robot, Coordinate>();
		linkEntitiesToGrid(); //Associates each entity with the grid
		this.startingPositions = populateStartingPositions(); //Populate starting positions
	}
	
	//Sorts starting positions from grid and returns an ordered queue
	private Queue<StartingPosition> populateStartingPositions() {
		Queue<StartingPosition> startingPosQueue = new LinkedList<StartingPosition>();
		ArrayList<StartingPosition> spList = new ArrayList<StartingPosition>();
		for (GridEntity[] row : entities) {
			for (GridEntity col : row) {
				if(col instanceof StartingPosition) {	
					spList.add((StartingPosition) col);
				}
			}
		}
		int count = 0;
		while (startingPosQueue.size() != spList.size()) {
			for(StartingPosition sp : spList) {
				char letter = sp.getLetter();
				if (letter == 'A' && count == 0) {
					startingPosQueue.add(sp);
					count++;
				} else if (letter == 'B' && count == 1) {
					startingPosQueue.add(sp);
					count++;
				} else if (letter == 'C' && count == 2) {
					startingPosQueue.add(sp);
					count++;
				} else if (letter == 'D' && count == 3) {
					startingPosQueue.add(sp);
					count++;
				}
			}
		}
		return startingPosQueue;
	}
	
	//Loops through grid and associates each entity with this grid
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
        for (int y = 0; y < entities.length; y++) {
        	for(int x = 0; x < entities[y].length; x++) {
        		//Check if robot at these coordinates
        		Coordinate pos = new Coordinate(y, x);
        		char letter = isRobotInPosition(pos);
        		if(letter != 0) {
        			gridString += letter; //Print letter of the robot instead of Entity
        		}
        		else if(entities[y][x] == null || entities[y][x] instanceof StartingPosition) {
                	gridString += "."; //Print . instead of StartingPositions
                } else {
                	gridString += entities[y][x].toString();
                }
        	}
        	gridString += "\n";
        }
        System.out.println(gridString); //print grid
	}
	
	//Adds robot to the robots map with its coordinate
		public Robot addRobot() {
			//Add robot to map
			StartingPosition pos = this.startingPositions.poll(); //gets a starting pos from queue
			Robot robot = new Robot(this, pos.getCurrentPosition()); //Creates new robot
			robot.setLetter(pos.getLetter());
			robots.put(robot, robot.getStartingPosition()); //Adds robot to map
			entities[robot.getStartingPosition().getX()][robot.getStartingPosition().getY()] = null;
			return robot;
		}
	
	//Updates robot map with new coordinates
	public void moveRobot(Robot robot, Coordinate newCoordinate) {
		//Check in bounds
		if(checkIfOutOfBounds(newCoordinate) == false) {
			robots.put(robot, newCoordinate); //Update position in map
			robot.setCurrentPosition(newCoordinate);
			//Check if on flag or pit
			if(getEntity(robot.getCurrentPosition()) instanceof Pit || getEntity(robot.getCurrentPosition()) instanceof Flag) {
				getEntity(robot.getCurrentPosition()).react(robot);
			}
		} else {
			robot.destroy(); //Destroy robot if out of bounds
		}
		
		//Check if robot already in new position, handle colision
		for(Robot r : robots.keySet()) {
			CompassDirection direction = null;
			Coordinate oldPosition = robots.get(r);
			//If new coordinates equal to a position where there is already a robot, move original robot
			if(oldPosition.equals(newCoordinate) && !(r.equals(robot))) { //If there is a collision
				Coordinate prevPos = robot.getPreviousPosition();
				if(newCoordinate.getX() == prevPos.getX()-1 && newCoordinate.getY() == prevPos.getY()) {
					direction = CompassDirection.NORTH;
				} else if(newCoordinate.getX() == prevPos.getX()+1 && newCoordinate.getY() == prevPos.getY()) {
					direction = CompassDirection.SOUTH;
				} else if(newCoordinate.getX() == prevPos.getX() && newCoordinate.getY() == prevPos.getY()+1) {
					direction = CompassDirection.EAST;
				} else if(newCoordinate.getX() == prevPos.getX() && newCoordinate.getY() == prevPos.getY()-1) {
					direction = CompassDirection.WEST;
				}
			}
			
			if(direction != null) {
				switch(direction) 
				{
				case NORTH:
					Coordinate coordinateN = new Coordinate(r.getCurrentPosition().getX()-1, r.getCurrentPosition().getY());
					if(checkIfOutOfBounds(coordinateN) == false) {
	            		moveRobot(r, coordinateN);
	            	} else {
	            		r.destroy();
	            	}
					break;
				case EAST:
					Coordinate coordinateE = new Coordinate(oldPosition.getX(), oldPosition.getY()+1);
	            	if(checkIfOutOfBounds(coordinateE) == false) {
	                	moveRobot(r, coordinateE);
	            	} else {
	            		r.destroy();
	            	}
	            	break;
				case SOUTH:
					Coordinate coordinateS = new Coordinate(oldPosition.getX()+1, oldPosition.getY());
	            	if(checkIfOutOfBounds(coordinateS) == false) {
	                	moveRobot(r, coordinateS);
	            	} else {
	            		r.destroy();
	            	}
	            	break;
				case WEST:
					Coordinate coordinateW = new Coordinate(oldPosition.getX(), oldPosition.getY()-1);
	            	if(checkIfOutOfBounds(coordinateW) == false) {
	                	moveRobot(r, coordinateW);	
	            	} else {
	            		r.destroy();
	            	}
	            	break;
				}
			}
		}
	}
	
	//Check if position given is within the grid boundaries
	public boolean checkIfOutOfBounds(Coordinate pos) {
		if ((pos.getX() >= 0 && pos.getX() < entities.length) &&
				(pos.getY() >= 0 && pos.getY() < entities[pos.getX()].length)) {
			return false;
		} else {
			return true;
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
        for (int i=0; i < entities.length; i++) {
        	for(int x=0; x < entities[i].length; x++) {
        		Coordinate pos = new Coordinate(i, x);
        		char letter = isRobotInPosition(pos);
        		if(letter != 0) {
        			GridEntity entity = getEntity(pos);
        			 if (entity instanceof Gear) {
        				 for (Robot r : robots.keySet()) {
        					 if (r.getCurrentPosition().equals(pos)) {
        						 entity.act(r);
        					 }
        				 }
        			 }
        		}
        	}
        }
	}
	
	//Check if there is a robot in the position given
	public char isRobotInPosition(Coordinate pos) {
		for(Robot r : robots.keySet()) {
			if(robots.get(r).equals(pos)) {
				return r.getLetter();
			}
		}
		return 0;
	}
	
	//Returns map of robots with their coordinates
	public Map<Robot, Coordinate> getRobots(){
		return robots;
	}
	
	//Add a robot to the map
	public void addToRobotsArray(Robot r, Coordinate pos) {
		this.robots.put(r, pos);
	}
	
	//Returns the final flag number needed to win the game
	public int getLastFlagNumber() {
		ArrayList<Integer> flagNumbers = new ArrayList<Integer>();
		for (GridEntity[] row : entities) {
			for (GridEntity col : row) {
				if(col instanceof Flag) {		
					flagNumbers.add(((Flag) col).getNumber());
				}
			}
		}
		Collections.sort(flagNumbers);
		if (flagNumbers.size() > 0) {
			return flagNumbers.get(flagNumbers.size() - 1);
		} else {
			return 0;
		}
	}
	
	//Checks if there are starting positions available to add a robot
	public boolean canAddRobot() {
		if(robots.size() > 0) {
			return true;
		} else {
			return false;
		}
	}


}
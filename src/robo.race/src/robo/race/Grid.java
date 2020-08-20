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
        for (int i=0; i < entities.length; i++) {
        	for(int x=0; x < entities[i].length; x++) {
        		//Check if robot st these coordinates
        		Coordinate pos = new Coordinate(i, x);
        		char letter = isRobotInPosition(pos);
        		if(letter != 0) {
        			gridString += letter;
        		}
        		else if(entities[i][x] == null || entities[i][x] instanceof StartingPosition) {
                	gridString += ".";
                } else {
                	gridString += entities[i][x].toString();
                }
        	}
        	gridString += "\n";
        }
        System.out.println(gridString);
	}
	
	//Adds robot to the robots map with its coordinate
		public Robot addRobot() {
			//Add robot to map
			StartingPosition pos = this.startingPositions.poll(); //gets a starting pos from queue
			Robot robot = new Robot(this, pos.getCurrentPosition()); //Creates new robot
			robot.setLetter(pos.getLetter());
			robots.put(robot, robot.getStartingPosition()); //Adds robot to map
			entities[robot.getStartingPosition().getY()][robot.getStartingPosition().getX()] = null;
			return robot;
		}
	
	//Updates robot map with new coordinates
	public void moveRobot(Robot robot, Coordinate newCoordinate) {
		//Check in bounds
		if(checkIfOutOfBounds(newCoordinate) == false) {
			robots.put(robot, newCoordinate); //Update position in map
			robot.setCurrentPosition(newCoordinate);//Update robots own position
			//Check if on flag or pit
			if(getEntity(robot.getCurrentPosition()) instanceof Pit || getEntity(robot.getCurrentPosition()) instanceof Flag) {
				getEntity(robot.getCurrentPosition()).react(robot);
			}
		} else {
			robots.remove(robot);
			robot.destroy(); //Destroy robot if out of bounds
		}
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
	                	if(robot.getPreviousInstruction() == RobotInstruction.Forward) {
	                		Coordinate coordinateN = new Coordinate(oldPosition.getX()-1, oldPosition.getY());
	                		if(checkIfOutOfBounds(coordinateN) == false) {
		                		moveRobot(r, coordinateN);
		                		robots.put(r, coordinateN);
		                	} else {
		                		r.destroy();
		                	}
	                	} else if(robot.getPreviousInstruction() == RobotInstruction.Backward) {
	                		Coordinate coordinateN = new Coordinate(oldPosition.getX()+1, oldPosition.getY());
	                		if(checkIfOutOfBounds(coordinateN) == false) {
		                		moveRobot(r, coordinateN);
		                		robots.put(r, coordinateN);
		                	} else {
		                		r.destroy();
		                	}
	                	}
	                    break; 
	                case EAST:
	                	if(robot.getPreviousInstruction() == RobotInstruction.Forward) {
		                	Coordinate coordinateE = new Coordinate(oldPosition.getX(), oldPosition.getY()+1);
		                	if(checkIfOutOfBounds(coordinateE) == false) {
		                		robots.put(r, coordinateE);
			                	moveRobot(r, coordinateE);
		                	} else {
		                		r.destroy();
		                	}
	                	} else if(robot.getPreviousInstruction() == RobotInstruction.Backward) {
		                	Coordinate coordinateE = new Coordinate(oldPosition.getX(), oldPosition.getY()-1);
		                	if(checkIfOutOfBounds(coordinateE) == false) {
		                		robots.put(r, coordinateE);
			                	moveRobot(r, coordinateE);
		                	} else {
		                		r.destroy();
		                	}
	                	}
	                    break; 
	                case SOUTH:
	                	if(robot.getPreviousInstruction() == RobotInstruction.Forward) {
		                	Coordinate coordinateS = new Coordinate(oldPosition.getX()+1, oldPosition.getY());
		                	if(checkIfOutOfBounds(coordinateS) == false) {
		                		robots.put(r, coordinateS);
			                	moveRobot(r, coordinateS);
		                	} else {
		                		r.destroy();
		                	}
	                	} else if (robot.getPreviousInstruction() == RobotInstruction.Backward) {
		                	Coordinate coordinateS = new Coordinate(oldPosition.getX()-1, oldPosition.getY());
		                	if(checkIfOutOfBounds(coordinateS) == false) {
		                		robots.put(r, coordinateS);
			                	moveRobot(r, coordinateS);
		                	} else {
		                		r.destroy();
		                	}
	                	}
	                    break;
	                case WEST: 
	                	if(robot.getPreviousInstruction() == RobotInstruction.Forward) {
		                	Coordinate coordinateW = new Coordinate(oldPosition.getX(), oldPosition.getY()-1);
		                	if(checkIfOutOfBounds(coordinateW) == false) {
		                		robots.put(r, coordinateW);
			                	moveRobot(r, coordinateW);	
		                	} else {
		                		r.destroy();
		                	}
	                	} else if(robot.getPreviousInstruction() == RobotInstruction.Backward) {
		                	Coordinate coordinateW = new Coordinate(oldPosition.getX(), oldPosition.getY()+1);
		                	if(checkIfOutOfBounds(coordinateW) == false) {
		                		robots.put(r, coordinateW);
			                	moveRobot(r, coordinateW);	
		                	} else {
		                		r.destroy();
		                	}
	                	}
	                    break; 
	            } 
			}
				
		}
		
		
		
		
	}
	
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
        					 if (r.getCurrentPosition() == pos) {
        						 entity.act(r);
        					 }
        				 }
        			 }
        		}
        	}
        }
		for (Robot robot : robots.keySet()) {
			Coordinate position = robots.get(robot);
			GridEntity entity = this.getEntity(position);
			if(entity != null) {
				entity.act(robot);
			}
			
		}
	}
	
	public char isRobotInPosition(Coordinate pos) {
		for(Robot r : robots.keySet()) {
			if(robots.get(r).equals(pos)) {
				return r.getLetter();
			}
		}
		return 0;
	}
	
	public Map<Robot, Coordinate> getRobots(){
		return robots;
	}
	
	public void addToRobotsArray(Robot r, Coordinate pos) {
		this.robots.put(r, pos);
	}
	
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
	
	public boolean canAddRobot() {
		if(robots.size() > 0) {
			return true;
		} else {
			return false;
		}
	}


}
package robo.race;
import java.util.HashMap;
import java.util.Map;

import robo.race.entities.CompassDirection;
import robo.race.entities.GridEntity;
import robo.race.entities.Robot;

public class Grid {
	GridEntity[][] entities;
	Map<Robot, Coordinate> robots;
	
	//Constructor
	public Grid (GridEntity[][] entities) {
		this.entities = entities;
		this.robots = new HashMap<Robot, Coordinate>();
	}
	
	//Print out the grid
	public void print() {
		for (GridEntity[] row : entities) {
            String grid = "";
            for (GridEntity col : row) {
                grid += col;
            }
            grid += "\n";
            System.out.println(grid);
        }
	}
	
	//Adds robot to the robots map with its coordinate
	public void addRobot(Robot robot) {
		//Add robot to map
		robots.put(robot, robot.getStartingPosition());
	}
	
	//Updates robot map with new coordinates
	public void moveRobot(Robot robot, Coordinate newCoordinate) {
		//Check if robot already in new position
		for(Robot r : robots.keySet()) {
			Coordinate oldPosition = robots.get(r);
			//If new coordinates equal to a position where there is already a robot, move original robot
			if(oldPosition.getX() == newCoordinate.getX() && oldPosition.getY() == newCoordinate.getY()) {
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
		//Add out of bounds check
		robots.put(robot, newCoordinate); //Update position in map
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
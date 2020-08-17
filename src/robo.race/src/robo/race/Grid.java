package robo.race;
import java.util.Map;

import robo.race.entities.GridEntity;
import robo.race.entities.Robot;

public class Grid {
	GridEntity[][] entities;
	Map<Robot, Coordinate> robots;
	
	//Constructor
	public Grid (GridEntity[][] entities) {
		this.entities = entities;
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

}
package robo.race.entities;

import robo.race.Coordinate;
import robo.race.Grid;

//********************************************************
// Name: 		GridEntity
// Description: An abstract class to represent any entities that will be on a Grid.
// Author: 		Aurel Hudec
//********************************************************
public abstract class GridEntity {
	private Grid grid = null;
	private Coordinate currentPosition;
	
	public GridEntity() { }
	
	public GridEntity(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Method that describes the entity's behaviour when activated
	 * by the board.
	 * @param robot
	 */
	public abstract void act(Robot robot);
	
	
	/**
	 * Method that describes the entity's behaviour when a robot
	 * touches it. (e.g. pit, flag)
	 * @param robot
	 */
	public abstract void react(Robot robot);

	public Grid getGrid() {
		return grid;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Coordinate getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Coordinate currentPosition) {
		this.currentPosition = currentPosition;
	}
}
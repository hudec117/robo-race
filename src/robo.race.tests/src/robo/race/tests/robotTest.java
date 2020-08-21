package robo.race.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import robo.race.Grid;
import robo.race.GridParser;
import robo.race.GridParserException;
import robo.race.RobotInstruction;
import robo.race.entities.CompassDirection;
import robo.race.entities.Robot;

class robotTest {
	@Test
	//Test moving robot backwards and Forwards
	void testRobotMovement() {
		GridParser gp = new GridParser();
		try {
			Grid grid = gp.parse("robotGridTest");
			Robot robot = grid.addRobot();
			assertEquals(robot.getCurrentPosition().getX(), 1);
			assertEquals(robot.getCurrentPosition().getY(), 1);
			robot.perform(RobotInstruction.Forward); //Move Robot forward
			assertEquals(robot.getCurrentPosition().getX(), 0); //Check robot has been moved to the square forward
			assertEquals(robot.getCurrentPosition().getY(), 1);
			robot.perform(RobotInstruction.Backward); //Move Robot backward
			assertEquals(robot.getCurrentPosition().getX(), 1);
			assertEquals(robot.getCurrentPosition().getY(), 1);
			//Moving East
			robot.perform(RobotInstruction.RotateRight);
			robot.perform(RobotInstruction.Forward); //Move Robot forward
			assertEquals(robot.getCurrentPosition().getX(), 1); //Check robot has been moved to the square forward
			assertEquals(robot.getCurrentPosition().getY(), 2);
			robot.perform(RobotInstruction.Backward); //Move Robot backward
			assertEquals(robot.getCurrentPosition().getX(), 1);
			assertEquals(robot.getCurrentPosition().getY(), 1);
			//Moving South
			robot.perform(RobotInstruction.RotateRight);
			robot.perform(RobotInstruction.Forward); //Move Robot forward
			assertEquals(robot.getCurrentPosition().getX(), 2); //Check robot has been moved to the square forward
			assertEquals(robot.getCurrentPosition().getY(), 1);
			robot.perform(RobotInstruction.Backward); //Move Robot backward
			assertEquals(robot.getCurrentPosition().getX(), 1);
			assertEquals(robot.getCurrentPosition().getY(), 1);
			//Moving West
			robot.perform(RobotInstruction.RotateRight);
			robot.perform(RobotInstruction.Forward); //Move Robot forward
			assertEquals(robot.getCurrentPosition().getX(), 1); //Check robot has been moved to the square forward
			assertEquals(robot.getCurrentPosition().getY(), 0);
			robot.perform(RobotInstruction.Backward); //Move Robot backward
			assertEquals(robot.getCurrentPosition().getX(), 1);
			assertEquals(robot.getCurrentPosition().getY(), 1);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GridParserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//Test moving robot backwards and Forwards
	void testRobotUTurn() {
		GridParser gp = new GridParser();
		Grid grid;
		try {
			grid = gp.parse("robotGridTest");
			Robot robot = grid.addRobot();
			assertEquals(robot.getCompassDirection(), CompassDirection.NORTH);
			robot.perform(RobotInstruction.UTurn);
			assertEquals(robot.getCompassDirection(), CompassDirection.SOUTH);
			robot.perform(RobotInstruction.UTurn);
			assertEquals(robot.getCompassDirection(), CompassDirection.NORTH);
			robot.perform(RobotInstruction.RotateRight);
			robot.perform(RobotInstruction.UTurn);
			assertEquals(robot.getCompassDirection(), CompassDirection.WEST);
			robot.perform(RobotInstruction.UTurn);
			assertEquals(robot.getCompassDirection(), CompassDirection.EAST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GridParserException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	//Test destroying robot
	void testRobotDestroy() {
		GridParser gp = new GridParser();
		Grid grid;
		try {
			grid = gp.parse("robotGridTest");
			Robot robot = grid.addRobot();
			
			robot.perform(RobotInstruction.Forward);
			assertEquals(robot.getCurrentPosition().getX(), 0);
			
			// Destroy robot and assert it moved back to starting position.
			robot.destroy();
			assertEquals(robot.getCurrentPosition().getX(), 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GridParserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//Test destroying robot
	void testRobotDestroyCollision() {
		GridParser gp = new GridParser();
		Grid grid;
		try {
			grid = gp.parse("collisionBoardTest");
			Robot robotA = grid.addRobot();
			Robot robotB = grid.addRobot();
			
			// Move A and B forward (B is now sitting in A's starting position)
			robotA.perform(RobotInstruction.Forward);
			robotB.perform(RobotInstruction.Forward);
			
			robotA.perform(RobotInstruction.Forward);
			
			// Moves the robot outside of the board, destroying it.
			robotA.perform(RobotInstruction.Forward);
			
			// Assert robot has spawned North of starting position
			assertEquals(robotA.getStartingPosition().getX() - 1, robotA.getCurrentPosition().getX());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GridParserException e) {
			e.printStackTrace();
		}
	}
}

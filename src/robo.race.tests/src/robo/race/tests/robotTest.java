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
			grid.addRobot();
			Robot robot = (Robot) grid.getRobots().keySet().toArray()[0];
			//Moving North
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GridParserException e) {
			// TODO Auto-generated catch block
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
			grid.addRobot();
			Robot robot = (Robot) grid.getRobots().keySet().toArray()[0];
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GridParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

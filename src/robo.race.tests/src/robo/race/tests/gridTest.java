package robo.race.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import robo.race.Grid;
import robo.race.GridParser;
import robo.race.GridParserException;
import robo.race.RobotInstruction;
import robo.race.entities.CompassDirection;
import robo.race.entities.Robot;

class gridTest {

	@Test
	void gridScenarioTest() {
		//Test Pit, Flag and Gear
		try {
			GridParser gp = new GridParser();
			Grid grid = gp.parse("gridBoardTest");
			grid.addRobot();
			grid.addRobot();
			Robot robotB = (Robot) grid.getRobots().keySet().toArray()[0];
			Robot robotA = (Robot) grid.getRobots().keySet().toArray()[1];
			robotA.perform(RobotInstruction.Forward); //Test Pit
			assertEquals(robotA.getCurrentPosition().getX(), 2);
			assertEquals(robotA.getCurrentPosition().getY(), 1);
			
			robotB.perform(RobotInstruction.Forward); //Test Gear
			assertEquals(robotB.getCompassDirection(), CompassDirection.NORTH);
			grid.print();
			grid.activate();
			assertEquals(robotB.getCompassDirection(), CompassDirection.EAST);
			grid.print();
			assertEquals(robotB.getLastFlagNumber(), 0);
			robotB.perform(RobotInstruction.Forward); //Test Flag
			assertEquals(robotB.getLastFlagNumber(), 1);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GridParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	


}

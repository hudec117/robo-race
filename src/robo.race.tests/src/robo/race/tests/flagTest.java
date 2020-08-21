package robo.race.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import robo.race.entities.Flag;
import robo.race.entities.Robot;

class flagTest {

	@Test
	void flag_act_throwsUnsupportedOperationException() {
		// Arrange
		Robot robot = new Robot(null, null);
		
		// Act
		try {
			new Flag(1).act(robot);
			assertTrue(false);
		} catch (UnsupportedOperationException e) {
			// Assert
			assertTrue(true);
		}
	}
	
	@Test
	void flag_react_robotLastFlagIs0_setsLastFlagTo1() {
		// Arrange
		Robot robot = new Robot(null, null);
		
		// Act
		new Flag(1).react(robot);
		
		// Assert
		assertEquals(robot.getLastFlagNumber(), 1);
	}
	
	@Test
	void flag_react_robotLastFlagIs2_flagIs5_doesNotSetRobotFlagTo5() {
		// Arrange
		Robot robot = new Robot(null, null);
		robot.setLastFlagNumber(2);
		
		// Act
		new Flag(5).react(robot);
		
		// Assert
		assertEquals(robot.getLastFlagNumber(), 2);
	}
}
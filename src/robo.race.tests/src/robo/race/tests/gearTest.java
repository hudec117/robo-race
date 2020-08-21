package robo.race.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import robo.race.entities.CompassDirection;
import robo.race.entities.Flag;
import robo.race.entities.Gear;
import robo.race.entities.Robot;
import robo.race.entities.RotationDirection;

class gearTest {

	@Test
	void react_throwsUnsupportedOperationException() {
		// Arrange
		Robot robot = new Robot(null, null);
		
		// Act
		try {
			new Gear(RotationDirection.Clockwise).react(robot);
			assertTrue(false);
		} catch (UnsupportedOperationException e) {
			// Assert
			assertTrue(true);
		}
	}
	
	@Test
	void act_clockwiseRotation_rotatesRobotToEast() {
		// Arrange
		Robot robot = new Robot(null, null);
		
		// Act
		new Gear(RotationDirection.Clockwise).act(robot);
		
		// Assert
		assertEquals(robot.getCompassDirection(), CompassDirection.EAST);
	}
	
	@Test
	void act_antiClockwiseRotation_rotatesRobotToEast() {
		// Arrange
		Robot robot = new Robot(null, null);
		
		// Act
		new Gear(RotationDirection.AntiClockwise).act(robot);
		
		// Assert
		assertEquals(robot.getCompassDirection(), CompassDirection.WEST);
	}
}
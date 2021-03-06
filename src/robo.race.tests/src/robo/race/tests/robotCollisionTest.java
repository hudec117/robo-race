package robo.race.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import robo.race.Grid;
import robo.race.GridParser;
import robo.race.GridParserException;
import robo.race.RobotInstruction;
import robo.race.entities.Robot;

class robotCollisionTest {

	@Test
	void gridTestCollision() {
		try {
			GridParser gp = new GridParser();
			Grid grid = gp.parse("collisionBoardTest");
			Robot robotA = grid.addRobot();
			Robot robotB = grid.addRobot();
			System.out.println(robotA.getCurrentPosition().getX());
			assertEquals(robotA.getCurrentPosition().getX(), 1);
			robotB.perform(RobotInstruction.Forward);
			assertEquals(robotA.getCurrentPosition().getX(), 0); //Check robot A has been pushed
			robotB.perform(RobotInstruction.Forward);
			assertEquals(robotA.getCurrentPosition().getX(), robotA.getStartingPosition().getX()); //Check robot A was destroyed and spawned back
			assertEquals(robotA.getCurrentPosition().getY(), robotA.getStartingPosition().getY()); //Check robot A was destroyed and spawned back
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GridParserException e) {
			e.printStackTrace();
		}
	}
}
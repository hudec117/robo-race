package robo.race.entities;

public class Pit extends GridEntity {
	void act() {
		 throw new UnsupportedOperationException("Invalid operation");
	}
	
	void react() {
		Robot.destroy();
	}
}

package robo.race.entities;

public class Pit extends GridEntity {
	void act() {
		Robot.destroy();
	}
	
	void react() {
		Robot.destroy();
	}
}

package robo.race.entities;

public class Pit extends GridEntity {
	@Override
	public void act(Robot robot) {
		 throw new UnsupportedOperationException("Invalid operation");
	}
	
	@Override
	public void react(Robot robot) {
		robot.destroy();
	}
	
	@Override
	public String toString() {
		return "X";
	}	
	
}

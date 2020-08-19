package robo.race.entities;

public class Pit extends GridEntity {
	
	public void react(Robot robot) {
		robot.destroy();
	}

	@Override
	public void act(Robot robot) {
		 throw new UnsupportedOperationException();
		
	}
	
	@Override
	public String toString() {
		return "x";
		
	}

}

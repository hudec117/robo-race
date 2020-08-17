package robo.race.entities;

public class Pit extends GridEntity {
	void act() {
		 throw new UnsupportedOperationException("Invalid operation");
	}
	
	public void react(Robot robot) {
		robot.destroy();
	}

	@Override
	public void act(Robot robot) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "X";
		
	}

}

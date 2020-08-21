package robo.race.entities;

//********************************************************
// Name: 		Pit
// Description: A immediately reactable grid entity that destroys robots.
// Author: 		Talha Muhammad
//********************************************************
public class Pit extends GridEntity {
	
	@Override
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

package robo.race.entities;

//********************************************************
// Name: 		Flag
// Description: A Grid entity that represents a flag. Is able to react to robot's entering it.
// Author: 		Aurel Hudec
//********************************************************
public class Flag extends GridEntity {
	private int number;
	
	public Flag(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public void act(Robot robot) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void react(Robot robot) {
		// If the robot's last flag number is equal to our flag number
		// set the robot's last flag to ours.
		if (robot.getLastFlagNumber() + 1 == number) {
			robot.setLastFlagNumber(number);
		}
	}
	
	@Override
	public String toString() {
		return Integer.toString(number);
	}	
}
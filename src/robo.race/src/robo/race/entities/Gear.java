package robo.race.entities;

//********************************************************
// Name: 		Gear
// Description: A Grid entity that represents a Gear. Gears are able to activate on board activation and rotate the robot on them.
// Author: 		Aurel Hudec/Talha Muhammad
//********************************************************
public class Gear extends GridEntity {
	private RotationDirection rotationDirection;
	
	public Gear(RotationDirection direction) {
		this.rotationDirection = direction;
	}

	public RotationDirection getRotationDirection() {
		return rotationDirection;
	}

	@Override
	public void act(Robot robot) {
		robot.rotate(rotationDirection);
	}

	@Override
	public void react(Robot robot) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		if(rotationDirection == RotationDirection.Clockwise) {
			return "+";
		}
		else{
			return "-";
		}
	}
}

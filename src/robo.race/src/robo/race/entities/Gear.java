package robo.race.entities;

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

package robo.race.entities;

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
		if (robot.getNextFlagNumber() == number) {
			robot.setNextFlagNumber(number + 1);
		}
	}
}
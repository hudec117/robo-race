package robo.race.entities;

//********************************************************
// Name: 		StartingPosition
// Description: Represents a starting location on the Grid, this is removed from the Grid when robot's are added.
// Author: 		Aurel Hudec
//********************************************************
public class StartingPosition extends GridEntity{
	private char letter;
	
	public StartingPosition(char letter) {
		this.letter = letter;
	}

	public char getLetter() {
		return letter;
	}

	@Override
	public void act(Robot robot) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public void react(Robot robot) {
		throw new UnsupportedOperationException("Invalid operation");
	}

}

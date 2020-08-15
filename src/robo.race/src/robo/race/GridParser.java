package robo.race;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import robo.race.entities.Flag;
import robo.race.entities.Gear;
import robo.race.entities.GridEntity;
import robo.race.entities.Pit;
import robo.race.entities.RotationDirection;
import robo.race.entities.StartingPosition;

public class GridParser {
	public Grid parse(String filePath) throws FileNotFoundException, GridParserException {
		
		File file = new File(filePath);
		Scanner sc = new Scanner(file);
		
		ArrayList<GridEntity[]> allEntities = new ArrayList<GridEntity[]>();
		
		try {
			int lineCount = 0;
			int previousRowWidth = 0;
			while (sc.hasNextLine()) {
				if (lineCount > 0) {
					String line = sc.nextLine();
					char[] rawEntities = line.toCharArray();
					
					if (lineCount > 1 && previousRowWidth != rawEntities.length) {
						throw new GridParserException("Row width inconsistent.");
					}
					
					previousRowWidth = rawEntities.length;
					
					GridEntity[] entities = new GridEntity[rawEntities.length];
					
					for (int i = 0; i < rawEntities.length;) {
						char rawEntity = rawEntities[i];
						GridEntity entityToAdd = null;
						
						if (rawEntity == 'x') {
							// Pit
							entityToAdd = new Pit();
						} else if (rawEntity == '+' || rawEntity == '-') {
							// Gear
							RotationDirection direction = rawEntity == '+' ? RotationDirection.Clockwise : RotationDirection.AntiClockwise;
							entityToAdd = new Gear(direction);
						} else if (Character.isDigit(rawEntity)) {
							// Flag
							int flagNumber = rawEntity - '0';
							if (flagNumber < 1 || flagNumber > 4) {
								throw new GridParserException("Board contains more than 4 flags.");
							}
							entityToAdd = new Flag(flagNumber);
						} else if (Character.isUpperCase(rawEntity)) {
							// Robot
							if (rawEntity > 'D') {
								throw new GridParserException("Board cannot have more than 4 players.");
							}
							entityToAdd = new StartingPosition(rawEntity);
						}
						
						entities[i] = entityToAdd;
					}
					
					allEntities.add(entities);
				}
				
				lineCount++;
			}
		} finally {
			sc.close();
		}
		
		Grid grid = new Grid((GridEntity[][])allEntities.toArray());
		
		return grid;
	}
}

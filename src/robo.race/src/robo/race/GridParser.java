package robo.race;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import robo.race.entities.Flag;
import robo.race.entities.Gear;
import robo.race.entities.GridEntity;
import robo.race.entities.Pit;
import robo.race.entities.RotationDirection;

public class GridParser {
	public Grid parse(String filePath) throws FileNotFoundException {
		
		File file = new File(filePath);
		Scanner sc = new Scanner(file);
		
		ArrayList<ArrayList<GridEntity>> grid = new ArrayList<ArrayList<GridEntity>>();

		int lineCount = 0;
		while (sc.hasNextLine()) {
			if (lineCount > 0) {
				String line = sc.nextLine();
				char[] rawEntities = line.toCharArray();
				ArrayList<GridEntity> entities = new ArrayList<GridEntity>();
				
				for (char rawEntity : rawEntities) {
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
							// TODO: handle
						}
						entityToAdd = new Flag(flagNumber);
					} else if (Character.isUpperCase(rawEntity)) {
						// Robot
//						entityToAdd = new Robot();
					}
					
					entities.add(entityToAdd);
				}
				
				grid.add(entities);
			}
		}
		
		return grid;
	}
}

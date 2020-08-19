package robo.race;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
//********************************************************
//  Name: ProgramParser
//  Description: Takes an instructions file and outputs Programs
//               for the robots to follow
//********************************************************
public class ProgramParser {
	
	public List<Program> parse(String filePath) throws FileNotFoundException, ProgramParserException {
		//Used to store names and instructions from file
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		
		//Read file
		File file = new File(filePath); 
		Scanner sc = new Scanner(file); 
		int lineCount = 0;
	    while (sc.hasNextLine()) {
	      String line = sc.nextLine();
	      lineCount++;
	      //If lineCount = 2, get names (line1 is "format1", line2 contains names)
	      if (lineCount == 2) {
	    	  String[] lineSplitted = line.split(" ");
		      for(String name : lineSplitted) {
		    	  names.add(name);
		      }
	      } else if (lineCount > 2){ //Else get instructions
	    	  String[] lineSplitted = line.split(" ");
		      for(int i = 0; i < names.size(); i++) {
		    	  if(i < instructions.size()) {
		    		  if(lineSplitted[i].length() != 5) {
			    		  throw new ProgramParserException("Instructions must be setup in blocks of 5.");
			    	  }
		    		  String updatedInstruction = instructions.get(i) + lineSplitted[i];
		    		  instructions.set(i, updatedInstruction);
		    	  } else {
		    		  instructions.add(lineSplitted[i]);
		    	  }	
		      }
		  } 
	    }
	    
	    //Store all instructions for all players
	    ArrayList<Queue<RobotInstruction>> robotInstructions = new ArrayList<Queue<RobotInstruction>>();
	    //Loop through text instructions and convert them to RobotInstruction enums
	    for (String instruction : instructions){
	    	//Add enums to a queue
	    	Queue<RobotInstruction> instructionQueue = new LinkedList<RobotInstruction>();
	    	for (int i = 0; i < instruction.length(); i++){
	    	    char c = instruction.charAt(i); 
	    	    if(i > 0) {
	    	    	char prevC = instruction.charAt(i-1);
		    	    if (c == prevC) {
		    	    	throw new ProgramParserException("Robot cannot perform the same move twice in a row.");
		    	    }
	    	    }
	    	    
	    	    switch(c) 
	            { 
	                case 'F': 
	                	instructionQueue.add(RobotInstruction.Forward); 
	                    break; 
	                case 'B': 
	                	instructionQueue.add(RobotInstruction.Backward); 
	                    break; 
	                case 'L': 
	                	instructionQueue.add(RobotInstruction.RotateLeft);  
	                    break;
	                case 'R': 
	                	instructionQueue.add(RobotInstruction.RotateRight); 
	                    break; 
	                case 'U': 
	                	instructionQueue.add(RobotInstruction.UTurn); 
	                    break; 
	                case 'W': 
	                	instructionQueue.add(RobotInstruction.Wait); 
	                    break;
	                default:
	                    //File doesn't match correct format, return null
	                	return null;
	            } 
	    	    
	    	}
	    	robotInstructions.add(instructionQueue);
	    }
	    
	    //Create a program using player name and instructions
	    List<Program> programs = new ArrayList<Program>();
	    for(int i = 0; i < names.size(); i++) {
	    	Program program = new Program(names.get(i), robotInstructions.get(i));
	    	programs.add(program);
	    }
	    
	    //Return array of all programs
	    return new LinkedList<Program>(programs);
	}

}

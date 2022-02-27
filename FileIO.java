// This class will handle the file Input and Output that the program needs
import java.io.File;						// The Java File Class
import java.io.FileNotFoundException;		// Handling Error exceptions in the code
import java.util.*;							// Taking the Scanner Class in order to manip text files

public class FileIO {
	// Variables
	String filename = "example.txt";		// The name of the text file
	int numberOfProcesses = 0;				// The number of processes in the file
	int UID[];								// An array of processes
	int adjInformation[][];					// The Adjacency information
	
	// Constructors
	public FileIO(String fileName) {
		filename = fileName;				// Setting up the Filename
		parseFileInformation();				// parse The File Information
	}
	
	// Functions
	boolean checkIfFileExists() {
		// This function checks to see whether or not the file exists and returns the result as a bool
		File fileToCheck = new File(filename);
		return fileToCheck.exists();
	}
	
	boolean checkIfFileExists(String fn) {
		// This function checks to see whether or not the file exists and returns the result as a bool
		File fileToCheck = new File(fn);
		return fileToCheck.exists();
	}
	
	boolean checkIfFileExists(File f) {
		// This function checks to see whether or not the file exists and returns the result as a bool
		return f.exists();
	}
	
	String returnRawData() {
		// A function to see the plain text contents of the file
		String s = "";						// The String to return
		File newFile = new File(filename);	// The path to the file
		
		try {
			Scanner reader = new Scanner(newFile);
			if(checkIfFileExists(newFile)) {
				// The file does exist and we can begin reading from it
				while(reader.hasNextLine()) {
					// Read in the next line
					s += reader.nextLine();
				}
				reader.close();
			} else
				s = "Warning: File could not be loaded!";
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// return the string of the file
		return s;
	}

	int[] breakStringIntoArray (String s){
		// A way to read in a string and parse the information so that it can be applied to an array
		int []parsedArray = {};				// An array that holds the integers
		String[] splitString = s.split("\\s+");		// Split by the string
		for(int elem = 0; elem < splitString.length; elem++) {
			// Go to each position of the array and copy over the string data, turning it into a ints
			parsedArray[elem] = Integer.parseInt(splitString[elem]);
		}
		return parsedArray;
	}
	
	int[] breakStringIntoArray (String s, int numProcesses, int[] UIDs){
		// A way to read in a string and parse the information so that it can be applied to an array 
		// in a bitmap format
		int []parsedArray = new int[numProcesses];	// An array that holds the integers
		String[] splitString = s.split("\\s+");		// Split by the string
		
		// Set the new array to all zeros
		for(int elem = 0; elem < parsedArray.length; elem++)
			parsedArray[elem] = 0;					// For the bit map
		
		for(int elemInNeighbors = 0; elemInNeighbors < splitString.length; elemInNeighbors++) {
			for(int elemInUID = 0; elemInUID < UID.length; elemInUID++) {
			// Go to each position of the smaller string and set the value to 1 if the neighbors are true
				if(UID[elemInUID] == Integer.parseInt(splitString[elemInNeighbors])) {
					parsedArray[elemInUID] = 1;		// Because the node has that neighbor, we set this to 1
				} 
				// We allow everything else to maintain their old values
			}
		}
		return parsedArray;
	}
	
	boolean parseFileInformation() {
		// This function breaks the file information into the different categories to be read later
		File newFile = new File(filename);	// The path to the file
		int fileLine = 0;					// The counter for line we are currently on
		String data;						// data from the file
		
		try {
			Scanner reader = new Scanner(newFile);
			if(checkIfFileExists(newFile)) {
				// The file does exist and we can begin reading from it
				while(reader.hasNextLine()) {
					// Read in the next line
					data = reader.nextLine();	// Read in the line contents
					
					switch(fileLine) {
						case 0: // This is used to determine the number of processes there are in the program
							numberOfProcesses = Integer.parseInt(data);
							break;	
						case 1: // This is used to determine the UIDs of the processes 
							UID = breakStringIntoArray(data);
							break;
						default: // for any other line, this would be used for the adjacency information 
							adjInformation[fileLine - 2] = breakStringIntoArray(data, numberOfProcesses, UID);
							break;
					}
					fileLine++;			// Increment after we have read a line in the file
				}
				reader.close();
			} else 
				return false;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		return true;
		
	}
}

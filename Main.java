import java.util.*;
public class Main {
	   public  static  void main(String args[]) throws InterruptedException {
	       //int[] process_id = {12, 18, 58, 56, 55, 54};
	        /*for(int i=0;i<process_id.length;i++){
	            Process p=new Process(process_id[i]);
	            p.start();
	        }*/
		   createProcesses();
	    }
	    private static void createProcesses() throws InterruptedException{
	    	
	    	FileIO f=new FileIO("src/inputdata.txt");
	    	//Creating n processes and linking it to its neighbors
	    	Process[] processObjects = new Process[f.numberOfProcesses];
	    	for(int i=0; i < f.numberOfProcesses; i++) {
	    		Process p_obj = new Process(f.UID[i]);
	    		processObjects[i] = p_obj;
	    	}
	    	for(int i=0; i < f.numberOfProcesses; i++) {
	    		int count_of_neighbors = 0;
	    		for(int j=0; j < f.numberOfProcesses; j++) {
	    			if(f.adjInformation[i][j] == 1) {
	    				count_of_neighbors += 1;
	    			}
	    		}
	    		processObjects[i].neighbors = new Process[count_of_neighbors];
	    	}
	    	
	    	for(int i=0; i < f.numberOfProcesses; i++) {
	    		int index = 0;
	    		for(int j=0; j < f.adjInformation[i].length; j++) {
	    			if(f.adjInformation[i][j] == 1) {
	    				processObjects[i].neighbors[index] = processObjects[j];
	    				index += 1;
	    			}
	    		}
	    	}
	    	for(int i=0; i < processObjects.length; i++) {
	    		System.out.println("List of neighbors of process: "+processObjects[i].uid);
	    		for(int j=0; j < processObjects[i].neighbors.length; j++) {
	    			System.out.println(""+processObjects[i].neighbors[j].uid);
	    		}
	    	}
	    	
	    	for(int i=0; i < f.numberOfProcesses; i++) {
	    		processObjects[i].start();
	    	}
	    	
	    	System.out.println("In main.java");
	    	for(int i=0; i < f.numberOfProcesses; i++) {
	    		processObjects[i].join();
	    	}
	    	}
	        /*
	            //master thread waiting for all threads to complete
	            for (int i = 0; i < process_id.length; i++)
	                try{
	                    Process p=new Process(process_id[i]);
	                    p.join();
	                    System.out.println("Master thread: Thread "+i+" terminated");
	                }
	                catch (InterruptedException e){
	                    System.out.println("Got interrupted while waiting for :" + i);
	                    continue;
	                }*/
	        }



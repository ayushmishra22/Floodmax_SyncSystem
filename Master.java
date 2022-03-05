import java.util.*;
public class Master extends Thread{
	int master_round;
	Process[] child_processes;
	
	public Master(Process[] child_p,int numberOfProcesses) {
		this.child_processes = child_p;
		master_round = 1;
    	for(int i=0; i < numberOfProcesses; i++) {
    		child_processes[i].start();
    	}
	}
	
	                                         
	public void run() {
		while(true) {
			int signal = 1;
			for(int i=0; i<child_processes.length; i++) {
				if(child_processes[i].current_round != master_round) {
					signal = 0;
				}
			}
			if(signal==1) {
				master_round+=1;
				System.out.println("----------------------------------Round "+master_round+"------------------------------------");
				for(int i=0; i<child_processes.length; i++) {
					child_processes[i].sync_round = master_round;
				}
				System.out.println("Start of round: "+ master_round);
			}
		}
	}

}

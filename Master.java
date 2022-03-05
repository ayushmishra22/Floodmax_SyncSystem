import java.util.*;
public class Master extends Thread{
	int master_round = 0;
	Process[] child_processes;
	
	public Master(Process[] child_p) {
		this.child_processes = child_p;
	}
	
	                                         
	public void run() {
		while(true) {
			int signal = 1;
			for(int i=0; i<child_processes.length; i++) {
				if(child_processes[i].sync_round != master_round) {
					signal = 0;
				}
			}
			if(signal==1) {
				master_round+=1;
				System.out.println("Start of round: "+ master_round);
			}
		}
	}

}

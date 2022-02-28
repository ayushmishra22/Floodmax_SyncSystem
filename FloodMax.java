import java.util.*;

/*public class FloodMax {
    public  static  void main(String args[]) {
        int[] process_id = {12, 18, 58, 56, 55, 54};
        for(int i=0;i<process_id.length;i++){
            Process p=new Process(process_id[i]);
            p.start();
        }
        FloodMax floodMax=new FloodMax();
        floodMax.createThreads(process_id);
    }
        private void createThreads(int[] process_id){
            FileIO f=new FileIO("src/inputdata.txt");
            Neighbour new_neighbour;
            ArrayList<Neighbour> neigh_list;
            int[][] x=f.adjInformation;
            for (int i = 0; i < process_id.length; i++)
            {
                neigh_list = new ArrayList<Neighbour>();
                //Create list of each node's neighbours
                for(int j=0; j<process_id.length; j++)
                    if(x[i][j] == 1)
                    {
                        new_neighbour = new Neighbour();
                        neigh_list.add(new_neighbour);
                    }
                Process p = new Process(process_id[i]);
                    for(int k=0;k<process_id.length;k++){
                        p.start();
                    }
            }
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
                }
        }
 */       

public class Floodmax {
	int round; 			//To keep track of round number.
	public void floodmax(Process p) {
		if(round == 1) {			//If it is the first round, then each process must send its id to all its neighbors
			for(int i = 0; i<=p.neighbours.length; i++) {
				Message msg = new Message(p, p.neighbours[i], "max_uid", ""+p.max_uid);
				msg.receiver.message_buffer.offer(msg);
			}
		}	
		
		while(p.message_buffer.size() != 0) { 			//Each process must execute every message and send corresponding messages to its neighbors.
			Message received_msg = p.message_buffer.poll();
			if(received_msg.type  == "max_uid") {
				if(p.max_uid >= Integer.parseInt(received_msg.message)) { 		//If max id in message is lesser than max_id encountered by process then send reject message.
					Message msg = new Message(p,received_msg.sender , "reject", "");
					msg.receiver.message_buffer.offer(msg);	
				}
				if(p.max_uid < Integer.parseInt(received_msg.message)) {		//If max id in message is greater than max_id encountered by process then update max_id and send max_id to all other neighbors
					p.max_uid = Integer.parseInt(received_msg.message);
					p.parent = received_msg.sender;
					//send the max id to others
					for(int i = 0; i < p.neighbours.length; i++) {
						if(p.neighbours[i] != received_msg.sender) {
							Message msg_to_send = new Message(p, p.neighbours[i], "max_uid", ""+p.max_uid);
							msg_to_send.receiver.message_buffer.offer(msg_to_send);
						}
					}
				}
			}
		}
		
	}
}

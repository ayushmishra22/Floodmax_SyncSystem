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
	//Step 1. Send id to neighbors
	//Step 2. Receive messages from all neighbors
	//Step 3. Select max id and store it in Process.max_id variable
	//Step 4. Send the max id to all other neighbors
	
	public void begin_floodmax(Process p) {
		int count_neighbours = p.neighbours.length;
		for(int i = 0; i<=count_neighbours; i++) {
			Message msg = new Message(p, p.neighbours[i], "max_uid", ""+p.max_uid);
			if(p.neighbours[i].msg_rear != p.neighbours[i].message_buffer.length -1) {
				p.neighbours[i].message_buffer[p.msg_rear+1] = msg;	
				p.neighbours[i].msg_rear += 1;
			}
		}
		
		while(p.msg_front != p.msg_rear) {
			Message received_msg = p.message_buffer[p.msg_front];
			if(received_msg.type  == "max_uid") {
				if(p.max_uid >= Integer.parseInt(received_msg.message)) {
					Message msg = new Message(p,received_msg.sender , "reject", "");
					msg.receiver.message_buffer[msg.receiver.msg_rear+1] = msg;	
					msg.receiver.msg_rear += 1;
				if(p.max_uid < Integer.parseInt(p.message_buffer[p.msg_front].message)) {
					p.max_uid = Integer.parseInt(p.message_buffer[p.msg_front].message);
					//send the max id to others
					for(int i = 0; i < p.neighbours.length; i++) {
						if(p.neighbours[i] != received_msg.sender) {
							Message msg_to_send = new Message(p, p.neighbours[i], "max_uid", ""+p.max_uid);
						}
					}
					Message new_msg = new Message(p,p.message_buffer[p.msg_front].sender , "reject", "");
					msg.receiver.message_buffer[msg.receiver.msg_rear+1] = new_msg;	
				}
			}
		}
		
	}
}

    }


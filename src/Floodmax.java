
public class Floodmax {
	int round = 1; 			//To keep track of round number.
	public void floodmax(Process p) {
		if(round == 1) {			//If it is the first round, then each process must send its id to all its neighbors
			System.out.println("Round 1-Process: "+p.uid);
			for(int i = 0; i<p.neighbors.length; i++) {
				Message msg = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
				msg.receiver.message_buffer.add(msg);
			}
		}	
		
		while(p.message_buffer.size() != 0) { 			//Each process must execute every message and send corresponding messages to its neighbors.
			System.out.println("Checking messages- Process: "+p.uid);
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
					for(int i = 0; i < p.neighbors.length; i++) {
						if(p.neighbors[i] != received_msg.sender) {
							Message msg_to_send = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
							msg_to_send.receiver.message_buffer.offer(msg_to_send);
						}
					}
				}
			}
		}
	round += 1;
	System.out.println("Process: "+p.uid+" Ready for Round: "+round);
	}
}

import java.util.*;
public class Floodmax {			
	
	public boolean programFinished = false;
	
	public void floodmax(Process p) {
		if(p.current_round == 0) {			//If it is the first round, then each process must send its id to all its neighbors
			for(int i = 0; i<p.neighbors.length; i++) {
				Message msg = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
				msg.receiver.message_buffer.add(msg);
			}
			check_messages(p);
			p.current_round +=1;
		}
		while(true) {
			if(p.current_round < p.sync_round) {
				send_messages(p);
				check_messages(p);
				check_ifdone(p);
				p.current_round += 1;
			}
			else {
				continue;
			}
		}
	}
	
	private void check_ifdone(Process p) {
		int f = 1;
		for(int j=0; j < p.neighbors.length; j++) {
			if(p.convergecast[j] == 0) {
				f = 0;
				break;
			}
	}
	if(f==1 && !programFinished) {
		check_ifLeader(p);
		try{
		Message done_msg = new Message(p,p.parent , "I am Done", "");
		done_msg.receiver.message_buffer.add(done_msg);
		} catch(NullPointerException e){}
	}
	}
	private void check_ifLeader(Process p) {
		int flag = 1;
		for(int i =0; i < p.neighbors.length; i++) {
			if(p.convergecast[i] != 2) {
				flag = 0;
			}
		}
		if(flag==1) {
			System.out.println("Elected Leader: "+p.uid);
			Master.found_leader = 1;
			Master.leader_uid = p.uid;
		}
	}
	
	private void send_messages(Process p) {
		while(p.msg_to_be_sent.size() != 0) {
			Message m = p.msg_to_be_sent.remove();
			try {
			m.receiver.message_buffer.add(m);
			}
			catch(Exception e) {
				System.out.println("Null exception caught in line 73");
			}
		}
	}
		private void check_messages(Process p){
			while(p.message_buffer.size() != 0) { 			//Each process must execute every message and send corresponding messages to its neighbors.
				//System.out.println("Checking messages- Process: "+p.uid);
				Message received_msg = p.message_buffer.remove();
				if(received_msg.type  == "max_uid") {
					if(p.max_uid >= Integer.parseInt(received_msg.message)) { 		//If max id in message is lesser than max_id encountered by process then send reject message.
						Message msg_max = new Message(p,received_msg.sender , "reject", "");
						msg_max.receiver.message_buffer.add(msg_max);
						/*try {
						p.msg_to_be_sent.add(msg_max);
						}
						catch(Exception e) {
							System.out.println("Null exception caught in line 84");
						}*/
					}
					if(p.max_uid < Integer.parseInt(received_msg.message)) {		//If max id in message is greater than max_id encountered by process then update max_id and send max_id to all other neighbors
						p.max_uid = Integer.parseInt(received_msg.message);
						p.parent = received_msg.sender;
						//send the max id to others
						for(int i = 0; i < p.neighbors.length; i++) {
							if(p.neighbors[i] != received_msg.sender) {
								Message msg_to_send = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
								msg_to_send.receiver.message_buffer.add(msg_to_send);
							}
						}
						Message accept_msg = new Message(p,received_msg.sender , "accept", "");
						accept_msg.receiver.message_buffer.add(accept_msg);
						for(int i=0; i < p.neighbors.length; i++) {
							p.convergecast[i] = 0;
						}
					}
				}
				if(received_msg.type == "accept") {
					//p.parent
				}
				if(received_msg.type == "I am Done") {
					for(int j=0; j < p.neighbors.length; j++) {
						if(received_msg.sender == p.neighbors[j]) {
							p.convergecast[j] = 2;
						}
					}
				}
				if(received_msg.type == "reject") {
					for(int j=0; j < p.neighbors.length; j++) {
						if(received_msg.sender == p.neighbors[j]) {
							p.convergecast[j] = 1;
						}
					}
				}
				
			}
		}

	}

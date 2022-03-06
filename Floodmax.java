import java.util.*;
public class Floodmax {			
	public void floodmax(Process p) {
		if(p.current_round == 0) {			//If it is the first round, then each process must send its id to all its neighbors
			System.out.println("Round 1-Process: "+p.uid);
			for(int i = 0; i<p.neighbors.length; i++) {
				Message msg = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
				msg.receiver.message_buffer.add(msg);
			}
			check_messages(p);
			System.out.println("Process: "+p.uid+" Ready for Round: 2");
			p.current_round +=1;
		}
		System.out.println("Current round = "+p.current_round);
		System.out.println("Sync round = "+p.sync_round);
		while(true) {
			if(p.current_round < p.sync_round) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				send_messages(p);
				check_messages(p);
				check_ifdone(p);
				System.out.println("Process: "+p.uid+" Ready for Round: "+(p.current_round+2));
				//for(int j=0; j < p.neighbors.length; j++) {
				//		System.out.print("Process: "+p.uid+"    "+p.neighbors[j]);
				//	}
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
	if(f==1) {
		check_ifLeader(p);
		Message done_msg = new Message(p,p.parent , "I am Done", "");
		done_msg.receiver.message_buffer.add(done_msg);
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
			System.out.println("I am the leader - Process uid: "+p.uid);
			Master.found_leader = 1;
		}
	}
	
	private void send_messages(Process p) {
		while(p.msg_to_be_sent.size() != 0) {
			Message m = p.msg_to_be_sent.remove();
			if(Objects.isNull(m)) {
				System.out.println("MESSAGE is null here------------------------------------------------------------------------------------------------");
			}
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
				if(Objects.isNull(received_msg)) {
					System.out.println("MESSAGE is null here------------------------------------------------------------------------------------------------");
				}
				if(received_msg.type  == "max_uid") {
					if(p.max_uid >= Integer.parseInt(received_msg.message)) { 		//If max id in message is lesser than max_id encountered by process then send reject message.
						Message msg_max = new Message(p,received_msg.sender , "reject", "");
						if(Objects.isNull(msg_max)) {
							System.out.println("MESSAGE is null here------------------------------------------------------------------------------------------------");
						}
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
								if(Objects.isNull(msg_to_send)) {
									System.out.println("MESSAGE is null here------------------------------------------------------------------------------------------------");
								}
								msg_to_send.receiver.message_buffer.add(msg_to_send);
								/*try {
									p.msg_to_be_sent.add(msg_to_send);
									}
									catch(Exception e) {
										System.out.println("Null exception caught in line 99");
									}*/
							}
						}
						Message accept_msg = new Message(p,received_msg.sender , "accept", "");
						if(Objects.isNull(accept_msg)) {
							System.out.println("MESSAGE is null here------------------------------------------------------------------------------------------------");
						}
						accept_msg.receiver.message_buffer.add(accept_msg);
						/*try {
							p.msg_to_be_sent.add(accept_msg);
							}
							catch(Exception e) {
								System.out.println("Null exception caught in line 109");
							}*/
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

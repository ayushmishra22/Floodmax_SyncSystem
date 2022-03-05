
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
				for(int j=0; j < p.neighbors.length; j++) {
						System.out.print("Process: "+p.uid+"    "+p.neighbors[j]);
					}
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
		done_msg.receiver.message_buffer.offer(done_msg);
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
			//send terminate message
		}
	}
	
	private void send_messages(Process p) {
		while(p.msg_to_be_sent.size() != 0) {
			Message m = p.msg_to_be_sent.poll();
			m.receiver.message_buffer.offer(m);
		}
	}
		private void check_messages(Process p){
			while(p.message_buffer.size() != 0) { 			//Each process must execute every message and send corresponding messages to its neighbors.
				//System.out.println("Checking messages- Process: "+p.uid);
				Message received_msg = p.message_buffer.poll();
				if(received_msg.type  == "max_uid") {
					if(p.max_uid >= Integer.parseInt(received_msg.message)) { 		//If max id in message is lesser than max_id encountered by process then send reject message.
						Message msg = new Message(p,received_msg.sender , "reject", "");
						//msg.receiver.message_buffer.offer(msg);
						p.msg_to_be_sent.offer(msg);
					}
					if(p.max_uid < Integer.parseInt(received_msg.message)) {		//If max id in message is greater than max_id encountered by process then update max_id and send max_id to all other neighbors
						p.max_uid = Integer.parseInt(received_msg.message);
						p.parent = received_msg.sender;
						//send the max id to others
						for(int i = 0; i < p.neighbors.length; i++) {
							if(p.neighbors[i] != received_msg.sender) {
								Message msg_to_send = new Message(p, p.neighbors[i], "max_uid", ""+p.max_uid);
								//msg_to_send.receiver.message_buffer.offer(msg_to_send);
								p.msg_to_be_sent.offer(msg_to_send);
							}
						}
						Message accept_msg = new Message(p,received_msg.sender , "accept", "");
						//accept_msg.receiver.message_buffer.offer(accept_msg);
						p.msg_to_be_sent.offer(accept_msg);
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

public class Message { 			//Use this class to send and receive messages
	
	int sender;
	int receiver;
	String type;	//type can be "max_uid", "reject", "I am done", "Terminate" 
	String message;
	
	public Message(int sender_id, int receiver_id, String message, String type_of_message) {
		this.sender = sender_id;
		this.receiver = receiver_id;
		this.type = type_of_message;
		this.message = message;
	}
	
	public int getSenderID() {
		return this.sender;
	}
	
	public void setSenderID(int uid) {
		this.sender = uid;
	}
	
	public int getReceiverID() {
		return this.receiver;
	}
	
	public void setReceiverID(int uid) {
		this.receiver = uid;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String msg) {
		this.message = msg;
	}
	
}

public class Message { 			//Use this class to send and receive messages
	
	Process sender;
	Process receiver;
	String type;	//type can be "max_uid", "reject", "I am done", "Terminate" 
	String message;
	
	public Message(Process sender, Process receiver, String type_of_message, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.type = type_of_message;
		this.message = message;
		String str = ""+sender.uid+"|"+receiver.uid+"|"+type_of_message+"|"+message;
		String s = "";
		System.out.println(s);
	}
	
	public Process getSender() {
		return this.sender;
	}
	
	public void setSender(Process sender) {
		this.sender = sender;
	}
	
	public Process getReceiver() {
		return this.receiver;
	}
	
	public void setReceiver(Process receiver) {
		this.receiver = receiver;
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

//import javax.naming.ldap.PagedResultsControl;
import java.util.*;

public class Process extends Thread {
    int uid;
    Process[] neighbors;
    int[] convergecast;
    int max_uid;
    Process parent;
    Process[] child;
    Queue<Message> message_buffer;
    Queue<Message> msg_to_be_sent;
    int sync_round;//used for synchronizing the processes
    int current_round;
    boolean termination;

    //constructor
    public Process(int id){
        uid=id;
        parent=null;
        max_uid=id;
        termination=Boolean.FALSE;
        sync_round = 0;
        current_round = 0;
        message_buffer = new LinkedList<Message>();
        msg_to_be_sent = new LinkedList<Message>();
    }
    public void run(){
        System.out.println("Hey I am process "+uid);
        Floodmax fm = new Floodmax();
        fm.floodmax(this);
        // FloodMax.floodmax() to be called here
        /*synchronized(sync_round)
        {
            try{
                sync_round.wait();
            }
            catch(InterruptedException ip)
            {
                System.out.println("process "+ process_id+ " Interrupted while waiting");
            }
            catch(IllegalMonitorStateException e)
            {
                System.out.println("Exception in process"+process_id+ " : "+ e.toString());
            }
        }*/
    }
}
import javax.naming.ldap.PagedResultsControl;
import java.util.*;

public class Process extends Thread {
    int uid;
    //ArrayList<Neigbour> neighbours;
    //ArrayList<Message> msg;
    int temp_leader;
    int parent;
    int round;
    Integer sync_round;//used for synchronizing the processes
    boolean termination;

    //constructor
     public Process(int id){
        uid=id;
    }
    public Process(int id,ArrayList<Neighbour> neigh_list){
        uid=id;
        parent=-1;
        round=0;
        temp_leader=id;
        termination=Boolean.FALSE;
    }

    public void run(){
        System.out.println("Hey I am process "+uid);
        //message generation,sending/receiving code goes here
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


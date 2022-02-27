import java.util.*;

public class FloodMax {
    public  static  void main(String args[]) {
        int[] process_id = {12, 18, 58, 56, 55, 54};
        /*for(int i=0;i<process_id.length;i++){
            Process p=new Process(process_id[i]);
            p.start();
        }*/
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
    }


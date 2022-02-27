import java.util.Scanner;

public class FloodMax {
    public  static  void main(String args[]){
        int[] process_id={12,18,58,56,55,54};
        Scanner s=new Scanner(System.in);
        for(int i=0;i<process_id.length;i++){
            Process p=new Process(process_id[i]);
            p.start();
        }
    }
}

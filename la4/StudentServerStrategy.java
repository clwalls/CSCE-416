import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
    List<String> file;
    boolean[] acks;
    int cwnd;
    int ssthresh;

    public StudentServerStrategy(){
        reset();
    }

    public void setFile(List<String> file){
        this.file = file;
        acks = new boolean[file.size()];
        
    }

    public void reset(){
        cwnd = 1;
        ssthresh = 20;
    }

     /*
    * Congestion Control: 
    *       Slow Start: if msg == ACK && cwnd <= ssthresh, cwnd = 2*cwnd
    *       Congestion Avoidance: if msg == ACK && cwnd > ssthresh, 
    *             additive increase every RTT: cwnd++ 
    *
    *            TCP RENO: multiplative decrease, after packet loss: cwnd = cwnd /2, ssthresh = cwnd 
    */

    public List<Message> sendRcv(List<Message> clientMsgs){
        for(Message m: clientMsgs){
            // Mark ACKS as a boolean
            acks[m.num] =true;
            System.out.println(m.num+","+m.msg);
        }
        System.out.println("--");

        /*  This is for printing out the contents of ack
        for (int i = 0; i < acks.length;i++){
            System.out.print(i + ":" + acks[i] + " ");
        }
        System.out.println();
        */

        List<Message> msgs = new ArrayList<Message>();
            for (int i = 0; i < acks.length;i++){
                if ((acks[i] != true) && (msgs.size() < cwnd)){
                    msgs.add(new Message(i,file.get(i)));
                }
            }

        // Slow Start and Congestion Avoidance
        if (cwnd <= ssthresh){
            cwnd = 2*cwnd;
        } else if (cwnd > ssthresh){
                cwnd++;
        }

        return msgs;
    }
    
}
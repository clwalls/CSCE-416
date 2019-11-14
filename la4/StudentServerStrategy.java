import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
    List<String> file;
    boolean[] acks;

    //(Congestion window) cwnd++ whenever RTT incereases, cuts in half when packet loss occurs
    // Note: online examples have the sender doing congestion control and not the server
    int cwnd;
    int ssthresh;

    public StudentServerStrategy(){
        reset();
    }

    public void setFile(List<String> file){
        this.file = file;
    }

    public void reset(){
        cwnd = 1;
        ssthresh = 20;
    }

     /*
    * TODO: Increment cwnd everytime RTT increases. Program starts in cwnd
    * 
    * Congestion Control: 
    *       Slow Start: if msg == ACK && cwnd <= ssthresh, cwnd = 2*cwnd
    *       Congestion Avoidance: if msg == ACK && cwnd > ssthresh, 
    *             additive increase every RTT: cwnd++ 
    *
    *            TCP RENO: multiplative decrease, after packet loss: cwnd = cwnd /2, ssthresh = cwnd 
    *            TCP TAHOE: set cwnd to 1
    */

    public List<Message> sendRcv(List<Message> clientMsgs){
        for(Message m: clientMsgs){
            acks[m.num-1] =true;
            System.out.println(m.num+","+m.msg);
        }

        int firstUnACKed = 0;
        
        List<Message> msgs = new ArrayList<Message>();

        while( firstUnACKed < acks.length && acks[firstUnACKed]) ++firstUnACKed;

        if(firstUnACKed < acks.length) {
            msgs.add(new Message(firstUnACKed,file.get(firstUnACKed)));   
        }
        return msgs;
    }
    
}
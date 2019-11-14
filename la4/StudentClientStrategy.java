import java.util.*;

public class StudentClientStrategy implements ClientStrategy{
    ArrayList<String> file;
    //(Congestion window) cwnd++ whenever RTT incereases, cuts in half when packet loss occurs
    // Note: online examples have the sender doing congestion control and not the server
    int cwnd;

    public StudentClientStrategy(){
        reset();
        cwnd = 1;
    }

    public void reset(){
        file = new ArrayList<String>();
    }

    public List<String> getFile(){
        return file;
    }

    //Call this function to cut cwindow in half, is called when packet loss is detected
    public void halfCwnd(){
        cwnd = cwnd/2;
    }



    /*
    * TODO: Increment cwnd everytime RTT increases. Program starts in cwnd
    * 
    * Congestion Avoidance: 
    *       Slow Start: if msg == ACK && cwnd <= ssthresh, cwnd = 2*cwnd
    *       Congestion Avoidance: if msg == ACK && cwnd > ssthresh, 
                additive increase every RTT: cwnd++ 
                multiplative decrease, after packet loss: cwnd = cwnd /2, ssthresh = cwnd 
    */

    /* 
    * What this method does: Builds a "file" that is the size of the server's message and then returns an array of ACK messages 
    * Format: ACK #, "ACK"
    */
    public List<Message> sendRcv(List<Message> serverMsgs){
        // For each message on the list, print the SEQ/ACK number and Message
        for(Message m : serverMsgs){
            // This line builds up the file array to the size of the server message's ACK/SEQ number size.
            while(file.size() < m.num+1) file.add(null);
            
            //File == serverMsgs (copy)
            file.set(m.num,m.msg);
            System.out.println(m.num+","+m.msg);
        }

        //Finds first non-null message, 
        int nextNeeded = 0;
        while(nextNeeded <file.size() && file.get(nextNeeded)!=null)
            ++nextNeeded;

        
        // ACK the message, add to list, return list of ACKs
        // TODO: Send ${cwnd} amount of ACKs instead of one
        Message m=new Message(nextNeeded,"ACK");
        List<Message> ack = new ArrayList<Message>();
        ack.add(m);
        return ack;

    }

}
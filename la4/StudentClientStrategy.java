import java.util.*;

public class StudentClientStrategy implements ClientStrategy{
    ArrayList<String> file;
    //(Congestion window) cwnd++ whenever RTT incereases, cuts in half when packet loss occurs
    // Note: online examples have the sender doing congestion control and not the server
    int cwnd;

    public StudentClientStrategy(){
        reset();
        cwnd = 0;
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


    
    public List<Message> sendRcv(List<Message> serverMsgs){
        // For each message on the list, print the SEQ/ACK number and Message
        for(Message m : serverMsgs){
            // This line builds up the file array to the size of the message's ACK/SEQ number size.
            while(file.size() < m.num+1) file.add(null);
            //At the designated ACK/SEQ num, add the string message
            file.set(m.num,m.msg);
            //PRINT message to console
            System.out.println(m.num+","+m.msg);
        }

        //grab the first non null message
        int nextNeeded = 0;
        while(nextNeeded <file.size() && file.get(nextNeeded)!=null)
            ++nextNeeded;

        Message m=new Message(nextNeeded,"ACK");

        List<Message> ack = new ArrayList<Message>();
        ack.add(m);
        return ack;

    }

}
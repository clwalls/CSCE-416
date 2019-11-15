import java.util.*;

public class StudentClientStrategy implements ClientStrategy{
    ArrayList<String> file;
    public StudentClientStrategy(){
        reset();
    }

    public void reset(){
        file = new ArrayList<String>();
    }

    public List<String> getFile(){
        return file;
    }

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
        System.out.println("--");
        
        //Finds first non-null message, 
        int nextNeeded = 0;
        while(nextNeeded <file.size() && file.get(nextNeeded)!=null)
            ++nextNeeded;

        
        // ACK the message, add to list, return list of ACKs
        Message m=new Message(nextNeeded,"ACK");
        List<Message> ack = new ArrayList<Message>();
        ack.add(m);
        return ack;

    }

}
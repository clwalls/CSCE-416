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

        List<Message> ack = new ArrayList<Message>();
        for(Message m : serverMsgs){
            //This is what is used to check the file length in the tester
            while(file.size() < m.num+1) file.add(null);
            file.set(m.num,m.msg);

            //Skip the middle man, just acknowledge everything
            Message message=new Message(m.num,"ACK");
            ack.add(message);
            System.out.println(message.num+","+message.msg);
        }
        System.out.println("--");

        /* This prints out all the messages in ack
        for (Message m: ack){
            System.out.print(m.num + ":" + m.msg + " ");
        }
        System.out.println();
        */

        return ack;

    }

}
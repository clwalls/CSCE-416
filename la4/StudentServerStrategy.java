import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
    List<String> file;
    boolean[] acks;

    public StudentServerStrategy(){
        reset();
    }

    public void setFile(List<String> file){
        this.file = file;
    }

    public void reset(){


    }

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
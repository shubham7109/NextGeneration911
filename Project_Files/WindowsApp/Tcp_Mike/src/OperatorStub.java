import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class OperatorStub {

    public static void main(String[] args) throws IOException, InterruptedException {

        int portNumber = 6789;
        String host = "10.25.69.139";
        //String host = "localhost";

        Client me = new Client(portNumber, host, "observer", "test");
        me.sendMessage("Im in");
        ArrayList<String> messages;
        int length = 0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now;

        while(true){
            messages = me.getMessages();
            for(int i = length; i < messages.size(); i++){
                System.out.println(messages.get(i));
            }
            length = messages.size();
            if(Math.random() > 0.5) {
                length = messages.size();
                now = LocalDateTime.now();
                me.sendMessage(dtf.format(now));
            }
            sleep(1000);
        }

//            me.closeConnection();
//
//            sleep(1000);
//
//            // I'm trying to add a new client but it throws an exception
//            // I need this, cause when I open the 911 call view, I create a new client
//
//            Client new_CLient = new Client(portNumber, host, "2", "1");
//            new_CLient.sendMessage("Hello from 2nd");
//            new_CLient.sendMessage("2nd Message");
//
//            messages = new_CLient.getMessages();
//
//            while(messages.size() == 0) {
//                try {
//                    sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            for (String line:
//                    new_CLient.getMessages()) {
//                System.out.println(line);
//            }
//        sleep(1000);
//        new_CLient.closeConnection();

    }
}

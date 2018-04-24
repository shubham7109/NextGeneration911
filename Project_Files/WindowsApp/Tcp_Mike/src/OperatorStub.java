import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class OperatorStub {

    public static void main(String[] args) throws IOException, InterruptedException {

        int portNumber = 6789;
        String host = "10.25.69.139";
        //String host = "localhost";

        Client me = new Client(portNumber, host, "1", "1");
        while (true) {
            me.sendMessage(String.valueOf(System.currentTimeMillis()));
            for (String line :
                    me.getMessages()) {
                System.out.println(line);
            }
            sleep(1000);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        }
//        me.sendMessage("Hello");
//        me.sendMessage("2nd Message");
//
//        ArrayList<String> messages = me.getMessages();
//
//        while(messages.size() == 0) {
//            try {
//                sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        while(true){
//            for (String line:
//                    me.getMessages()) {
//                System.out.println(line);
//            }
//            sleep(1000);
//            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
//        }

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

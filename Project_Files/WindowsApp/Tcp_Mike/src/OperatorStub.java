import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class OperatorStub {

    public static void main(String[] args) throws IOException, InterruptedException {

        int portNumber = 6789;
        String host = "10.25.69.139";

        Client me = new Client(portNumber, host, "1", "1");
        me.sendMessage("Hello");
        me.sendMessage("2nd Message");

        ArrayList<String> messages = me.getMessages();

        while(messages.size() == 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            for (String line:
                    me.getMessages()) {
                System.out.println(line);
            }
            sleep(2000);

            me.closeConnection();

            // I'm trying to add a new client but it throws an exception
            // I need this, cause when I open the 911 call view, I create a new client

            Client new_CLient = new Client(portNumber, host, "2", "1");
            new_CLient.sendMessage("Hello");
            new_CLient.sendMessage("2nd Message");

            messages = new_CLient.getMessages();

            while(messages.size() == 0) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (String line:
                    new_CLient.getMessages()) {
                System.out.println(line);
            }
        sleep(2000);
        new_CLient.closeConnection();


    }
}

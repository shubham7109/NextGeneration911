import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class OperatorStub {

    public static void main(String[] args) {

        int portNumber = 2222;
        String host = "localhost";

        Client me = new Client(portNumber, host, "Bob", "123");
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
        messages = me.getMessages();
        System.out.println(messages.size());
        System.out.println(messages);

    }
}

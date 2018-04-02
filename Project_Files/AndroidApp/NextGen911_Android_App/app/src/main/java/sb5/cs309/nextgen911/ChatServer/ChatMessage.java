package sb5.cs309.nextgen911.ChatServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by mike on 3/24/18.
 */

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public JSONObject getJSON(){
        JSONObject message = new JSONObject();

        try {
            message.put("name", getMessageUser());
            message.put("time", getMessageTime());
            message.put("text", getMessageText());
        }catch(JSONException e){
            e.printStackTrace();
        }
        return message;
    }
}
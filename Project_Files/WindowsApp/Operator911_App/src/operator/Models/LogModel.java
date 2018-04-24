package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Model class to model LogModel
 * @author Shubham Sharma
 */
public class LogModel {

    private String date;
    private String time;
    private String callLength;
    private String operatorName;
    private String phoneNumber;
    private String messages;
    private String operatorID;

    /**
     * Constructor set the instance variables
     * @param jsonObject Used to parse and set variables from given keys
     * @throws JSONException
     */
    public LogModel(JSONObject jsonObject) {
        try {
            date = jsonObject.getString("date");
            time = jsonObject.getString("time");
            callLength  = jsonObject.getString("callLength");
            operatorName  = jsonObject.getString("operatorName");
            phoneNumber = jsonObject.getString("phoneNumber");

            operatorID = jsonObject.getString("operatorId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            messages = ((jsonObject.getString("messages") == null) ? "No messages": jsonObject.getString("messages"));
        } catch (JSONException e) {
            messages = "No messages available";
        }

    }

    public String getMessages() {
        return messages;
    }

    public String getOperatorID() {
        return operatorID;
    }

    /**
     *
     * @return Returns the date of the given log
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @return Returns the time of the given log
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @return Returns the length of the call of the given log
     */
    public String getCallLength() {
        return callLength;
    }

    /**
     *
     * @return Returns the operator name of the given log
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     *
     * @return Returns the caller's number of the given log
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}

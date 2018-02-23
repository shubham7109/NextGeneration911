package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class LogModel {

    private String date;
    private String time;
    private String callLength;
    private String operatorName;
    private String phoneNumber;

    public LogModel(JSONObject jsonObject) throws JSONException {
        date = jsonObject.getString("date");
        time = jsonObject.getString("time");
        callLength  = jsonObject.getString("callLength");
        operatorName  = jsonObject.getString("operatorName");
        phoneNumber = jsonObject.getString("phoneNumber");

    }
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCallLength() {
        return callLength;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

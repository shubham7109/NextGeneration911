package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class OperatorModel {

    private String id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private int status;

    public OperatorModel(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        userName = jsonObject.getString("userName");
        password = jsonObject.getString("password");
        firstName = jsonObject.getString("firstName");
        lastName = jsonObject.getString("lastName");
        location = jsonObject.getString("location");
        status = jsonObject.getInt("status");


    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    public int getStatus() {
        return status;
    }





}

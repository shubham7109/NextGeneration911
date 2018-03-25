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
    private int accesibility;
    private int status;
    private String ipAddress;
    private String image;

    public OperatorModel(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        userName = jsonObject.getString("userName");
        password = jsonObject.getString("password");
        firstName = jsonObject.getString("firstName");
        lastName = jsonObject.getString("lastName");
        location = jsonObject.getString("location");
        status = jsonObject.getInt("status");
        accesibility = jsonObject.getInt("accesibility");
        ipAddress = jsonObject.getString("ipAddress");
        image = jsonObject.getString("image");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getImage() {
        return image;
    }

    public int getAccesibility(){
        return accesibility;
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

    public String getStatus() {

        if(status == 0)
            return "Available";
        else if (status == 1)
            return "Unavailable";
        else if (status == 2)
            return "On-Call";
        else
            return "Offline";

    }





}

package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class to model OperatorModel
 * @author Shubham Sharma
 */
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

    /**
     * Constructor set the instance variables
     * @param jsonObject Used to parse and set variables from given keys
     * @throws JSONException
     */
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

    /**
     *
     * @return Returns the IP address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *
     * @return Returns the image URL
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @return Returns the accessibility level
     */
    public int getAccesibility(){
        return accesibility;
    }

    /**
     *
     * @return Returns the ID of the operator
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Returns the username of the operator
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return Returns the password of the operator
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return Returns the first name of the operator
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return Returns the last name of the operator
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return Returns the location of the operator
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return Returns the current status of the operator
     */
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

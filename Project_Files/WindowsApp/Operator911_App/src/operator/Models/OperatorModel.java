package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private int totalCalls=0;
    private int averageCallLength=0;
    private int quickestCallLength=99999;
    private int lastCallLength=0;

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

    public void setOperatorReports(ArrayList<LogModel> logModels){
        calculateTotalCalls(logModels);
        calculateAverageCallLength(logModels);
        calculateQuickestCallLength(logModels);
        calculateLastCallLength(logModels);
    }

    private void calculateLastCallLength(ArrayList<LogModel> logModels){
        for (LogModel logModel : logModels) {
            if (logModel.getOperatorName().equals(firstName + " " + lastName)) {
                lastCallLength = Integer.valueOf(logModel.getCallLength());
                return;
            }
        }
    }

    private void calculateQuickestCallLength(ArrayList<LogModel> logModels){
        for (LogModel logModel : logModels) {
            if (logModel.getOperatorName().equals(firstName + " " + lastName)) {
                if(quickestCallLength > Integer.valueOf(logModel.getCallLength())){
                    quickestCallLength = Integer.valueOf(logModel.getCallLength());
                }
            }
        }
    }

    private void calculateTotalCalls(ArrayList<LogModel> logModels){
        for (LogModel logModel : logModels) {
            if (logModel.getOperatorName().equals(firstName + " " + lastName)) {
                totalCalls++;
            }
        }
    }

    private void calculateAverageCallLength(ArrayList<LogModel> logModels){
        int counter =0;
        for(LogModel logModel : logModels){
            if (logModel.getOperatorName().equals(firstName + " " + lastName)) {
                averageCallLength+= Integer.valueOf(logModel.getCallLength());
                counter++;
            }
        }
        if(counter == 0)
            averageCallLength = 0 ;
        else
            averageCallLength /= counter;
    }

    /**
     * @return Returns total number of calls received
     */
    public int getTotalCalls() {
        return totalCalls;
    }

    /**
     * @return Returns the average length of a call
     */
    public int getAverageCallLength() {
        return averageCallLength;
    }

    /**
     * @return Returns the quickest call length
     */
    public int getQuickestCallLength() {
        return quickestCallLength;
    }


    /**
     * @return Returns the latest call length
     */
    public int getLastCallLength() {
        return lastCallLength;
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

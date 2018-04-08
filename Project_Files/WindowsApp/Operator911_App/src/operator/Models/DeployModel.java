package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Model class to model DeployModel
 * @author Shubham Sharma
 */
public class DeployModel {

    private String id;
    private String type;
    private String latitude;
    private String longitude;

    /**
     * Constructor set the instance variables
     * @param jsonObject Used to parse and set variables from given keys
     * @throws JSONException
     */
    public DeployModel(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        type = jsonObject.getString("type");
        latitude = jsonObject.getString("latitude");
        longitude =jsonObject.getString("longitude");
    }

    /**
     * Calculates the distance between the latitude and longitude of the current deploy model
     * with the caller's location
     * @param lat1 latitude of the caller
     * @param lon1 longitude of the caller
     * @param unit Unit of measurement
     * @return Distance in given unit
     */
    public double distance(double lat1, double lon1, String unit) {
        double lat2 = Double.parseDouble(latitude);
        double lon2 = Double.parseDouble(longitude);
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /**
     * @return Returns the ID of the DeployModel
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Returns the type of the DeployModel
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return Returns the latitude of the DeployModel
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @return Returns the longitude of the DeployModel
     */
    public String getLongitude() {
        return longitude;
    }
}

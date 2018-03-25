package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class DeployModel {

    private String id;
    private String type;
    private String latitude;
    private String longitude;

    public DeployModel(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        type = jsonObject.getString("type");
        latitude = jsonObject.getString("latitude");
        longitude =jsonObject.getString("longitude");
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}

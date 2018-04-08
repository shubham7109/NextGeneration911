package sb5.cs309.nextgen911;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Various REST api calls, implemented using Volley
 */

public class Networking {
    /**
     * Make a post request to persons server to add/update a person. Overwrites existing persons
     *
     * @param personalInfo JSON containing personal Info
     */
    public static void postPersonalInfo(JSONObject personalInfo) {
        JsonObjectRequest req = new JsonObjectRequest("http://proj-309-sb-5.cs.iastate.edu:8080/persons/", personalInfo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        VolleyController.getInstance().getRequestQueue().add(req);
    }

    /**
     * Make a JSON GET request from persons server
     *
     * @param ID       ID of request person
     * @param listener Implements onResponse for a JSONObject Listener
     */
    public static void getPersonalInfo(final String ID, final Response.Listener<JSONObject> listener) {
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, "http://proj-309-sb-5.cs.iastate.edu:8080/persons/" + ID, null, listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleyController.getInstance().getRequestQueue().add(req);
    }

    /**
     * Make a String GET request to get the IP of the next available operator
     *
     * @param listener Implements onResponse for a JSONObject Listener
     */
    public static void getOperatorIP(Response.Listener<String> listener) {
        StringRequest req = new StringRequest
                (Request.Method.GET, "http://proj-309-sb-5.cs.iastate.edu:8080/makecall/", listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleyController.getInstance().getRequestQueue().add(req);
    }
}

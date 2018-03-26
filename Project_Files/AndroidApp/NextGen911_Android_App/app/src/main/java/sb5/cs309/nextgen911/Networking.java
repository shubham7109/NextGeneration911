package sb5.cs309.nextgen911;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 3/24/18.
 */

public class Networking {


    public static void postPersonalInfo(JSONObject personalInfo, RequestQueue mQueue) {
        JsonObjectRequest req = new JsonObjectRequest(PersonalInfoActivity.context.getResources().getString(R.string.personsURL), personalInfo,
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

        mQueue.add(req);
    }

    public static void getPersonalInfo(final String ID, final AppController.VolleyResponseListener listener, RequestQueue mQueue) {
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, "http://proj-309-sb-5.cs.iastate.edu:8080/persons/" + ID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(req);
    }

}

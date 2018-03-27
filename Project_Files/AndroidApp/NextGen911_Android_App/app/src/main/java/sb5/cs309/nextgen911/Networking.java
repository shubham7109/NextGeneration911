package sb5.cs309.nextgen911;

import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 3/24/18.
 */

public class Networking {


    public static void postPersonalInfo(JSONObject personalInfo) {
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

        AppController.getInstance().getRequestQueue().add(req);
    }

    public static void getPersonalInfo(final String ID, final Response.Listener<JSONObject> listener) {
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, "http://proj-309-sb-5.cs.iastate.edu:8080/persons/" + ID, null, listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().getRequestQueue().add(req);
    }

    public static void getOperatorIP(Response.Listener<String> listener) {
        StringRequest req = new StringRequest
                (Request.Method.GET, "http://proj-309-sb-5.cs.iastate.edu:8080/makecall/", listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().getRequestQueue().add(req);
    }
}

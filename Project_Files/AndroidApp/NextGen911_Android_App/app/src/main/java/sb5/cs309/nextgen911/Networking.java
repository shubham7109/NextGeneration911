package sb5.cs309.nextgen911;

import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Networking {
    private static final String url = "http://proj-309-sb-5.cs.iastate.edu:8080/persons/";



    // Attempt to post json to server
    public static void post(final JSONObject personalInfo) {


        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(url);

                    StringEntity se = new StringEntity(personalInfo.toString());
                    se.setContentType(new BasicHeader("Content-Type", "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    if (response != null) {
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }

    public static JSONObject get(String phoneNumber){
        // TODO
        return null;
    }



}

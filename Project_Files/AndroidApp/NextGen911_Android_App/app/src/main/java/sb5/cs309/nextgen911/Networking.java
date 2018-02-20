package sb5.cs309.nextgen911;

import android.os.Looper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import java.io.InputStream;

public class Networking {

    private static final String base_url = "http://proj-309-sb-5.cs.iastate.edu:8080/persons/";



    // Attempt to post json to server
    public static void post(final JSONObject personalInfo) {


        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(base_url);

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



    public static JSONObject get(final String ID){
        JSONObject response = null;

        return  response;
    }








}

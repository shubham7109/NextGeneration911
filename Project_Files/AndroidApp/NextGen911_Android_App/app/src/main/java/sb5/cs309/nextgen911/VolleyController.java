package sb5.cs309.nextgen911;


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 *
 * Implements queues and control logic for volley
 */

public class VolleyController extends Application {
    private static VolleyController mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyController getInstance() {
        return mInstance;
    }

    /**
     * Creates a singleton instance on app launch
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Creates a volley request queue if not instantiated
     *
     * @return an instance of the singleton Volley Request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add a volley request to the queue
     *
     * @param req Request to be added
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(""); // Empty tag
        getRequestQueue().add(req);
    }
}
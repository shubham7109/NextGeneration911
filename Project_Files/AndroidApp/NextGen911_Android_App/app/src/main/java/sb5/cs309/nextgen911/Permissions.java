package sb5.cs309.nextgen911;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Contains logic to asks for permissions
 */

public class Permissions {
    /**
     * Requests every permission used in the app. Only ungranted permissions are request if call is repeated
     * @param activity Calling activity
     * @param requestCode Code to identify this App (911)
     */
    public static void requestPermissions(Activity activity, int requestCode) {
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(android.Manifest.permission.CALL_PHONE);
        permissionList.add(android.Manifest.permission.READ_SMS);
        permissionList.add(Manifest.permission.USE_SIP);
        permissionList.add(Manifest.permission.USE_FINGERPRINT);
        String[] perms = permissionList.toArray(new String[0]);

        ActivityCompat.requestPermissions(activity, perms, requestCode);
    }
}

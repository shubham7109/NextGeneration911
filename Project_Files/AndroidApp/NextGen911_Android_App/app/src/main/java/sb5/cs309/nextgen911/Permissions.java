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
 * Created by mikeonys on 2/16/18.
 */

public class Permissions {
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


    // Currently Unused but useful if permissions need to be checked later.
    public static void checkPermissions(Context context, Activity activity) {
        CharSequence text;
        int duration = Toast.LENGTH_LONG;

        boolean locationPerm = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean sipPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.USE_SIP) == PackageManager.PERMISSION_GRANTED;
        boolean smsPerm = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;

    }

}

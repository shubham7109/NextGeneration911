package mikeonys.mikeexperiment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {
    private static final int requestCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void requestPermissions(View view) {
        ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA}, requestCode);
    }

    public void checkPermissions(View view){
        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_LONG;

        boolean locationPerm = ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean cameraPerm = ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (locationPerm && cameraPerm){
            text = "All Permissions Granted";
        }
        else if(locationPerm && !cameraPerm){
            text = "Location Access Granted, but Camera Access Denied";
        }
        else if(!locationPerm && cameraPerm){
            text = "Location Access Denied, but Camera Access Granted";
        }
        else{
            text = "No Permissions Granted";
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

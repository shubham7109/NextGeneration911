package mikeonys.mikeexperiment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "mikeonys.mikeexperiment.MESSAGE"; //Needed to send intent messages


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMessage(View view) {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
    }

    public void openScore(View view){
        Intent intent = new Intent(this,ScoreActivity.class);
        startActivity(intent);
    }

    public void openLocation(View view){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void openPermission(View view){
        Intent intent = new Intent(this, PermissionActivity.class);
        startActivity(intent);
    }



}

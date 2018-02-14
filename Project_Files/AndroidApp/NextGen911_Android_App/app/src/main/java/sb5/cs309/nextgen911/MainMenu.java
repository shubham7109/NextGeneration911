package sb5.cs309.nextgen911;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


public class MainMenu extends AppCompatActivity {


    private static SharedPreferences sharedPreferences;
    public static final String mypreference = "911UserPrefs";
    public static final String regKey = "Registered";
    private static final int requestCode = 911;
    BottomNavigationView bottomNavigationView;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MainMenu.context = getApplicationContext();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        int permission = sharedPreferences.getInt(regKey, 0);
        if(permission != 0){
            findViewById(R.id.reg_button).setVisibility(View.INVISIBLE);
        }

        requestPermissions();

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                // Do nothing this is the current view
                                break;

                            case R.id.navigation_personal_info:
                                intent = new Intent(getAppContext(), PersonalInfoActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.navigation_text:
                                intent = new Intent(getAppContext(), Text911Activity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                break;
                        }


                        return true;
                    }
                });
    }


    public void requestPermissions() {
        ActivityCompat.requestPermissions(MainMenu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS}, requestCode);
    }


    // Currently Unused but useful if permissions need to be checked later.
    public void checkPermissions(){
        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_LONG;

        boolean locationPerm = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean callPerm = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        boolean smsPerm = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;

    }

    public static Context getAppContext() {
        return MainMenu.context;
    }

    public void onRegisterClick(View view){
        Intent intent;

        intent = new Intent(getAppContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    public static void register_device(String regCode){
        sharedPreferences.edit().putInt(regKey,Integer.parseInt(regCode)).apply();
    }
}
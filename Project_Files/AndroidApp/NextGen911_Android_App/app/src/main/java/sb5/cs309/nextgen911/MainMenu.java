package sb5.cs309.nextgen911;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


public class MainMenu extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    public static final String mypreference = "911UserPrefs";
    public static final String regKey = "Registered";
    private static final int requestCode = 911;
    BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        /* Check for Critical Permissions */
        requestPermissions();

        /* Handle User Registration Should not block, access to 911
           currently just a dummy registration
         */
        if (!sharedPreferences.contains(regKey)) {
            Button button = (Button) findViewById(R.id.reg_status);
            button.setText(getResources().getString(R.string.reg_warning));

            // TODO Handle Registration steps
        } else {
            Button button = (Button) findViewById(R.id.reg_status);
            button.setVisibility(View.INVISIBLE);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_personal_info:
                    fragment = new PersonalInfoFragment();
                    //loadFragment(fragment);
                    return true;

                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    //loadFragment(fragment);
                    return true;

                case R.id.navigation_text:
                    fragment = new TextFragment();
                    //loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
}
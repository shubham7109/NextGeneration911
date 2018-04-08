package sb5.cs309.nextgen911;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Controller for first screen. Contains permission requests, and general navigation
 */

public class MainMenu extends AppCompatActivity {


    private static final String mypreference = "911UserPrefs";
    private static final String regKey = "Registered";
    private static final String phonekey = "phNum";
    private static final String idKey = "ID";
    private static final int requestCode = 911;
    public static SharedPreferences sharedPreferences;
    private static int clickCount;
    private static Context context;
    private BottomNavigationView bottomNavigationView;

    /**
     * Allows for the retreival of context outside of current intent.
     *
     * @return Context of mainMenu
     */
    public static Context getAppContext() {
        return MainMenu.context;
    }

    /**
     * Sets up naviagtion view, and makes calls to set-up permissions. Contains controller to ensure call button reuqires long press or lots of quick presses
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MainMenu.context = getApplicationContext();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        int registered = sharedPreferences.getInt(regKey, 0);
        findViewById(R.id.reg_button).setVisibility(View.INVISIBLE);


        Permissions.requestPermissions(MainMenu.this, requestCode);

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
                                intent = new Intent(getAppContext(), FingerPrintActivity.class);
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


        clickCount = 0;
        Button call911 = findViewById(R.id.call_911_button);
        call911.isClickable();

        call911.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCount < 3) {
                    Toast.makeText(getBaseContext(), (3 - clickCount) + " more taps to call 911", Toast.LENGTH_SHORT).show();
                    clickCount++;
                } else {
                    Intent intent = new Intent(getAppContext(), CallActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            }
        });

        call911.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getAppContext(), CallActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
        });

    }

    /**
     * Restarts button click counter if focus is lost and regained
     */
    protected void onResume() {
        super.onResume();
        clickCount = 0;

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
}
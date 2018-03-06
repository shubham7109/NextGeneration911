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


public class MainMenu extends AppCompatActivity {


    public static final String mypreference = "911UserPrefs";
    public static final String regKey = "Registered";
    public static final String phonekey = "phNum";
    public static final String idKey = "ID";
    private static final int requestCode = 911;
    public static SharedPreferences sharedPreferences;
    public static int clickCount;
    static Context context;
    BottomNavigationView bottomNavigationView;

    public static Context getAppContext() {
        return MainMenu.context;
    }

    public static void register_device(String regCode) {
        sharedPreferences.edit().putInt(regKey, Integer.parseInt(regCode)).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MainMenu.context = getApplicationContext();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        int registered = sharedPreferences.getInt(regKey, 0);
        if (registered != 0) {
            findViewById(R.id.reg_button).setVisibility(View.INVISIBLE);
        }

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
        Button call911 =findViewById(R.id.call_911_button);
        call911.isClickable();

        call911.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickCount < 3){
                    Toast.makeText(getBaseContext(), (3-clickCount) +" more taps to call 911", Toast.LENGTH_SHORT).show();
                    clickCount++;
                }else{
                    Toast.makeText(getBaseContext(), "911 Called", Toast.LENGTH_LONG).show();
                }

            }
        });

        call911.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getBaseContext(), "911 Called", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    public void onRegisterClick(View view) {
        Intent intent;

        intent = new Intent(getAppContext(), RegistrationActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
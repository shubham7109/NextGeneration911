package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PersonalInfoActivity extends AppCompatActivity {

    private TextView mTextMessage;
    static Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(getAppContext(), MainMenu.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;

                case R.id.navigation_personal_info:
                    // Do nothing this is the current view
                    break;
                case R.id.navigation_text:
                    intent = new Intent(getAppContext(), Text911Activity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        PersonalInfoActivity.context = getApplicationContext();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_personal_info);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static Context getAppContext() {
        return PersonalInfoActivity.context;
    }

}

package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sb5.cs309.nextgen911.ChatServer.ChatMessage;
import sb5.cs309.nextgen911.ChatServer.Client;

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.sharedPreferences;

public class Text911Activity extends AppCompatActivity {

    private EditText inputBox;
    static Context context;
    private ListView list_of_messages;
    ArrayList<String> messageList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

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
                    intent = new Intent(getAppContext(), FingerPrintActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.navigation_text:
                    // Do nothing this is the current view
                    break;
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();

        inputBox = findViewById(R.id.input);
        list_of_messages = findViewById(R.id.list_of_messages);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_text);
        adapter = new ArrayAdapter<String>(getAppContext(),R.layout.activity_text911,messageList);
        list_of_messages.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = sharedPreferences.getString(idKey, "");
                message += inputBox.getText().toString();
                inputBox.setText("");

                messageList.add(message);
                adapter.notifyDataSetChanged();
            }
        });



    }
    
    public static Context getAppContext() {
        return Text911Activity.context;
    }
}



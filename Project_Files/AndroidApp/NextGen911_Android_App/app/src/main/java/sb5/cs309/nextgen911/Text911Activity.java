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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Text911Activity extends AppCompatActivity {

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

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_text);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                input.setText("");
            }
        });
    }

    protected displayChatMessages(){
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }


    public static Context getAppContext() {
        return Text911Activity.context;
    }
}

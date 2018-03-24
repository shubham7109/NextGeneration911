package sb5.cs309.nextgen911;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

import sb5.cs309.nextgen911.ChatServer.Client;

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.sharedPreferences;

public class Text911Activity extends AppCompatActivity {

    private EditText inputBox;
    static Context context;
    private ListView list_of_messages;
    ArrayList<String> messageList;
    ArrayAdapter<String> adapter;
    Client clientConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();

        messageList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Text911Activity.this,
                android.R.layout.simple_list_item_1,messageList);

        inputBox = findViewById(R.id.input);
        list_of_messages = findViewById(R.id.list_of_messages);
        list_of_messages.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        clientConnection = createClient();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = sharedPreferences.getString(idKey, "");
                message += ": ";
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


    private Client createClient(){
        Consumer<Serializable> onRecieveCallback = new myConsumer();
        Client c = new Client("10.26.17.136", 5555, onRecieveCallback);
        return c;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public class myConsumer implements Consumer<Serializable>{

        @Override
        public void accept(Serializable serializable) {
            messageList.add(serializable.toString());
            adapter.notifyDataSetChanged();
        }
    }
}



package mikeonys.mikeexperiment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "mikeonys.mikeexperiment.MESSAGE"; //Needed to send intent messages
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String scoreKey = "scoreKey";
    int hiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(scoreKey)) {
            hiScore = sharedPreferences.getInt(scoreKey, 0);
        }
        else
            hiScore = 0;

        TextView textView = findViewById(R.id.textView2);
        textView.setText("Hi-Score: " + hiScore);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void sendScore(View view){
        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        EditText editText = (EditText) findViewById(R.id.editText2);
        try {
            int score = Integer.parseInt(editText.getText().toString());
            text = score + ": Entered";

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            submitScore(score);

        }catch (NumberFormatException e){
            text = "Invalid Input: Enter only integers";

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        TextView textView = findViewById(R.id.textView2);
        textView.setText("Hi-Score: " + hiScore);
    }

    public void submitScore(int score){
        if(score > hiScore){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(scoreKey, score);
            editor.apply();
            hiScore = score;
        }

    }

    public void resetScore(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(scoreKey);
        editor.apply();
        hiScore = 0;
        TextView textView = findViewById(R.id.textView2);
        textView.setText("Hi-Score: " + hiScore);
    }
}

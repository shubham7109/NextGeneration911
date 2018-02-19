package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PersonalInfoActivity extends AppCompatActivity {

    private RequestQueue queue;
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
        RequestQueue queue = Volley.newRequestQueue(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_personal_info);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static Context getAppContext() {
        return PersonalInfoActivity.context;
    }

    public void submit_info(View view){
        if(check_DOB() && check_ZIP() && check_phoneNumber()){
            JSONObject personalInfo = new JSONObject();

            try {
                personalInfo.put("id", getID());
                personalInfo.put("phoneNumber", getPhoneNumber());
                personalInfo.put("gender", getGender());
                personalInfo.put("firstName", getFirstName());
                personalInfo.put("middleName", getMiddleName());
                personalInfo.put("lastName", getLastName());
                personalInfo.put("homeAddress", getAddress());
                personalInfo.put("city", getCity());
                personalInfo.put("state", getState());
                personalInfo.put("zipcode", getZip());
                personalInfo.put("dateOfBirth", getDOB());
                personalInfo.put("licencePlateNumber", getLicencePlate());
                personalInfo.put("vehicle", getVehicle());
                personalInfo.put("bloodType", getBloodType());
                personalInfo.put("heightCentimeters", getHeight());
                personalInfo.put("weightKilograms", getWeight());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            post(personalInfo);

        }
    }

    // Ensures that date of birth confirms to MM/DD/YYYY standards displays error on failure
    public boolean check_DOB(){
        EditText dob_box = findViewById(R.id.dob_editText);
        String date = dob_box.getText().toString();

        if(date.equals("")){
            return true;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);


        try {
            cal.setTime(sdf.parse(date));
            // Handle wierd calendar bound issues
            if(cal.get(Calendar.MONTH)>11 || cal.get(Calendar.DAY_OF_MONTH) > 31)
                throw new ParseException("",0); //Exception values not used

            return true;

        } catch (ParseException e) {
            dob_box.setError("Please use MM/DD/YYYY date format");
            return false;
        }

    }

    // Ensures 5 digit zip code is entered displays error on failure
    public boolean check_ZIP(){
        EditText zip_box = findViewById(R.id.zipCode_editText);

        if(zip_box.getText().toString().length()==0) {
            return true;
        }


        if(zip_box.getText().toString().length()!=5){
            zip_box.setError("ZIP Code must be 5 digits long");
            return false;
        }
        return true;
    }

    // Ensures 10 digit phone number is entered displays error on failure
    public boolean check_phoneNumber(){
        EditText phone_number_box = findViewById(R.id.phoneNumber_editText);
        String phoneNumber = phone_number_box.getText().toString();
        phoneNumber = phoneNumber.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters

        if(phoneNumber.length() != 10 ){
            phone_number_box.setError("Phone Numbers must be 10 digits long");
            return false;
        }

        return true;

    }

    // Returns last 4 digits of phone number as ID
    // TODO create better ID method
    public int getID(){
        EditText phone_number_box = findViewById(R.id.phoneNumber_editText);
        String phoneNumber = phone_number_box.getText().toString();
        phoneNumber = phoneNumber.replaceAll("[^\\d.]", "");
        return Integer.parseInt(phoneNumber.substring(6));
    }

    public String getFirstName(){
        EditText name = findViewById(R.id.firstName_editText);
        return name.getText().toString();
    }

    public String getMiddleName(){
        EditText name = findViewById(R.id.middleName_editText);
        return name.getText().toString();
    }

    public String getLastName(){
        EditText name = findViewById(R.id.lastName_editText);
        return name.getText().toString();
    }

    public String getDOB(){
        EditText dob = findViewById(R.id.dob_editText);
        return dob.getText().toString();
    }

    public String getPhoneNumber(){
        EditText phone_number_box = findViewById(R.id.phoneNumber_editText);
        String phoneNumber = phone_number_box.getText().toString();
        return phoneNumber.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters
    }

    // Return //0 for female, 1 for male, -1 on no selection
    public int getGender(){
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);

        if(male.isChecked())
            return 1;
        if(female.isChecked())
            return 0;
        else
            return -1;
    }

    public String getAddress(){
        EditText address = findViewById(R.id.home_editText);
        return address.getText().toString();
    }

    public String getCity(){
        EditText city = findViewById(R.id.city_editText);
        return city.getText().toString();
    }

    public String getState(){
        EditText state = findViewById(R.id.state_editText);
        return state.getText().toString();
    }

    public int getZip(){
        EditText zip = findViewById(R.id.zipCode_editText);
        return Integer.parseInt(zip.getText().toString());
    }

    public String getLicencePlate(){
        EditText plate = findViewById(R.id.licencePlateNumber_editText);
        return plate.getText().toString();
    }

    public String getVehicle(){
        EditText vehicle = findViewById(R.id.vehicle_editText);
        return vehicle.getText().toString();
    }

    public int getHeight(){
        EditText height = findViewById(R.id.heightCentimeters_editText);
        return Integer.parseInt(height.getText().toString());
    }

    public int getWeight(){
        EditText weight = findViewById(R.id.weightKilograms_editText);
        return Integer.parseInt(weight.getText().toString());
    }

    public String getBloodType(){
        RadioButton a = findViewById(R.id.bloodA);
        RadioButton b = findViewById(R.id.bloodB);
        RadioButton ab = findViewById(R.id.bloodAB);
        RadioButton o = findViewById(R.id.bloodO);
        CheckBox rh = findViewById(R.id.rh_checkBox);

        if(a.isChecked()){
            if(rh.isChecked())
                return "A+";
            else
                return "A-";
        }
        if(b.isChecked()) {
            if (rh.isChecked())
                return "B+";
            else
                return "B-";
        }
        if(ab.isChecked()) {
            if (rh.isChecked())
                return "AB+";
            else
                return "AB-";
        }
        if(o.isChecked()) {
            if (rh.isChecked())
                return "O+";
            else
                return "O-";
        }
        return "";
    }


    // Attempt to post info to server
    public void post(final JSONObject personalInfo){
        String url = "localhost:8080/persons/" + getID();


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, personalInfo, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        VolleyQueue.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
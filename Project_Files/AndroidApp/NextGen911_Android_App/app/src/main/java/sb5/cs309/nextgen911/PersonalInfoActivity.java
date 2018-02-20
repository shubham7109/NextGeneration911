package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PersonalInfoActivity extends AppCompatActivity {

    static Context context;
    static String id = "";

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
        prepopulate();

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
            Toast.makeText(context, "Submitted", Toast.LENGTH_LONG).show();

            Networking.post(personalInfo);
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

        if(phoneNumber.length() != 10){
            phone_number_box.setError("Mandatory Field: Phone Numbers must be 10 digits long");
            return false;
        }

        return true;

    }

    // Returns phone number as ID
    public String getID(){
        return getPhoneNumber().substring(6);
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
        phoneNumber = phoneNumber.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters

        return phoneNumber;
    }

    // Return //0 for female, 1 for male, -1 on no selection
    public String getGender(){
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);

        if(male.isChecked())
            return "MALE";
        if(female.isChecked())
            return "FEMALE";
        else
            return "";
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

    public String getZip(){
        EditText zip = findViewById(R.id.zipCode_editText);

        if(zip.getText().toString().equals(""))
            return "0";

        return zip.getText().toString();
    }

    public String getLicencePlate(){
        EditText plate = findViewById(R.id.licencePlateNumber_editText);
        return plate.getText().toString();
    }

    public String getVehicle(){
        EditText vehicle = findViewById(R.id.vehicle_editText);
        return vehicle.getText().toString();
    }

    public String getHeight(){
        EditText height = findViewById(R.id.heightCentimeters_editText);
        if(height.getText().toString().equals(""))
            return "0";

        return height.getText().toString();
    }

    public String getWeight(){
        EditText weight = findViewById(R.id.weightKilograms_editText);
        if(weight.getText().toString().equals(""))
            return "0";

        return weight.getText().toString();
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


    //TODO JSON get is not working
    public void prepopulate() {
        String gender, firstName, middleName, lastName, homeAddress, city, state,
                dateOfBirth, licencePlateNumber, vehicle, bloodType, zipcode, heightCentimeters, weightKilograms;

        if(id.equals(""))
            return;

        //Make get request
        JSONObject personalInfo = Networking.get(id);

        // No data returned
        if(personalInfo == null){
            return;
        }

        // Load all JSON values
        try {
            gender = personalInfo.getString("gender");
            firstName = personalInfo.getString("firstName");
            middleName = personalInfo.getString("middleName");
            lastName = personalInfo.getString("lastName");
            homeAddress = personalInfo.getString("homeAddress");
            city = personalInfo.getString("city");
            state = personalInfo.getString("state");
            zipcode = personalInfo.getString("zipcode");
            dateOfBirth = personalInfo.getString("dateOfBirth");
            licencePlateNumber = personalInfo.getString("licencePlateNumber");
            vehicle = personalInfo.getString("vehicle");
            bloodType = personalInfo.getString("bloodType");
            heightCentimeters = personalInfo.getString("heightCentimeters");
            weightKilograms = personalInfo.getString("weightKilograms");
        } catch (JSONException e) {
            throw new RuntimeException(e); // Non-recoverable
        }


        // Change hints for all fields
        setGender(gender);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setHomeAddress(homeAddress);
        setCity(city);
        setState(state);
        setZIP(zipcode);
        setDOB(dateOfBirth);
        setLicence(licencePlateNumber);
        setVehicle(vehicle);
        setBloodType(bloodType);
        setHeight(heightCentimeters);
        setWeight(weightKilograms);
    }

    public void setFirstName(String firstName) {
        EditText text = findViewById(R.id.firstName_editText);
        text.setText(firstName);
    }

    public void setMiddleName(String middleName) {
        EditText text = findViewById(R.id.middleName_editText);
        text.setText(middleName);
    }

    public void setLastName(String lastName) {
        EditText text = findViewById(R.id.lastName_editText);
        text.setText(lastName);
    }

    public void setHomeAddress(String homeAddress) {
        EditText text = findViewById(R.id.home_editText);
        text.setText(homeAddress);
    }

    public void setCity(String city) {
        EditText text = findViewById(R.id.city_editText);
        text.setText(city);
    }

    public void setState(String state) {
        EditText text = findViewById(R.id.state_editText);
        text.setText(state);
    }

    public void setZIP(String ZIP) {
        EditText text = findViewById(R.id.zipCode_editText);
        text.setText(ZIP);
    }

    public void setDOB(String DOB) {
        EditText text = findViewById(R.id.dob_editText);
        text.setText(DOB);
    }

    public void setLicence(String licence) {
        EditText text = findViewById(R.id.licencePlateNumber_editText);
        text.setText(licence);
    }

    public void setVehicle(String vehicle) {
        EditText text = findViewById(R.id.vehicle_editText);
        text.setText(vehicle);
    }

    public void setBloodType(String bloodType){
        CheckBox rh = findViewById(R.id.rh_checkBox);
        if(bloodType.contains("+"))
            rh.toggle();
        bloodType = bloodType.replace("+","");
        if(bloodType.equals("A")){
            RadioButton a = findViewById(R.id.bloodA);
            a.toggle();
        }
        if(bloodType.equals("B")){
            RadioButton b = findViewById(R.id.bloodB);
            b.toggle();
        }
        if(bloodType.equals("AB")){
            RadioButton ab = findViewById(R.id.bloodAB);
            ab.toggle();
        }
        if(bloodType.equals("O")){
            RadioButton o = findViewById(R.id.bloodO);
            o.toggle();
        }

    }

    public void setGender(String gender){
        if(gender.equals("MALE")){
            RadioButton button = findViewById(R.id.male);
            button.toggle();
        }
        if(gender.equals("FEMALE")){
            RadioButton button = findViewById(R.id.female);
            button.toggle();
        }
        if(gender.equals("")){
            RadioButton button = findViewById(R.id.none);
            button.toggle();
        }

    }

    public void setHeight(String height){
        EditText text = findViewById(R.id.heightCentimeters_editText);
        text.setText(height);
    }

    public void setWeight(String weight){
        EditText text = findViewById(R.id.weightKilograms_editText);
        text.setText(weight);
    }
}

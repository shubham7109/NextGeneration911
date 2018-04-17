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

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.sharedPreferences;

/**
 * Controls personal info. entry
 */

public class PersonalInfoActivity extends AppCompatActivity {

    static Context context;
    BottomNavigationView navigation;

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

    public static Context getAppContext() {
        return PersonalInfoActivity.context;
    }

    /**
     * On launch sets up the navigation bar and attempts to pre-load personal data if device already registered
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        PersonalInfoActivity.context = getApplicationContext();
        loadJson();

        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_personal_info);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * On submit button push, create a JSON with all entered values, mark this device as registered and make a POST request,
     */
    public void submit_info(View view) {
        if (check_DOB() && check_ZIP() && check_phoneNumber()) {
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

                LocationServices.LocationTuple l = LocationServices.getLocation(getAppContext());

                personalInfo.put("latitude", l.lat); // Null Value
                personalInfo.put("longitude", l.lng); // Null Value

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(context, "Submitted", Toast.LENGTH_LONG).show();
            sharedPreferences.edit().putString(idKey, getID()).apply();

            Networking.postPersonalInfo(personalInfo);
        }
    }

    // Ensures that date of birth confirms to MM/DD/YYYY standards displays error on failure
    private boolean check_DOB() {
        EditText dob_box = findViewById(R.id.dob_editText);
        String date = dob_box.getText().toString();

        if (date.equals("")) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);


        try {
            cal.setTime(sdf.parse(date));
            // Handle wierd calendar bound issues
            if (cal.get(Calendar.MONTH) > 11 || cal.get(Calendar.DAY_OF_MONTH) > 31)
                throw new ParseException("", 0); //Exception values not used

            return true;

        } catch (ParseException e) {
            dob_box.setError("Please use MM/DD/YYYY date format");
            return false;
        }

    }

    // Ensures 5 digit zip code is entered displays error on failure
    private boolean check_ZIP() {
        EditText zip_box = findViewById(R.id.zipCode_editText);

        if (zip_box.getText().toString().length() == 0) {
            return true;
        }


        if (zip_box.getText().toString().length() != 5) {
            zip_box.setError("ZIP Code must be 5 digits long");
            return false;
        }
        return true;
    }

    // Ensures 10 digit phone number is entered displays error on failure
    private boolean check_phoneNumber() {
        EditText phone_number_box = findViewById(R.id.phoneNumber_editText);
        String phoneNumber = phone_number_box.getText().toString();
        phoneNumber = phoneNumber.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters

        if (phoneNumber.length() != 10) {
            phone_number_box.setError("Mandatory Field: Phone Numbers must be 10 digits long");
            return false;
        }

        return true;

    }

    // Returns phone number as ID
    private String getID() {
        return getPhoneNumber().substring(6);
    }

    private String getFirstName() {
        EditText name = findViewById(R.id.firstName_editText);
        return name.getText().toString();
    }

    private void setFirstName(String firstName) {
        EditText text = findViewById(R.id.firstName_editText);
        text.setText(firstName);
    }

    private String getMiddleName() {
        EditText name = findViewById(R.id.middleName_editText);
        return name.getText().toString();
    }

    private void setMiddleName(String middleName) {
        EditText text = findViewById(R.id.middleName_editText);
        text.setText(middleName);
    }

    private String getLastName() {
        EditText name = findViewById(R.id.lastName_editText);
        return name.getText().toString();
    }

    private void setLastName(String lastName) {
        EditText text = findViewById(R.id.lastName_editText);
        text.setText(lastName);
    }

    private String getDOB() {
        EditText dob = findViewById(R.id.dob_editText);
        return dob.getText().toString();
    }

    private void setDOB(String DOB) {
        EditText text = findViewById(R.id.dob_editText);
        text.setText(DOB);
    }

    private String getPhoneNumber() {
        EditText phone_number_box = findViewById(R.id.phoneNumber_editText);
        String phoneNumber = phone_number_box.getText().toString();
        phoneNumber = phoneNumber.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters

        return phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        EditText text = findViewById(R.id.phoneNumber_editText);
        text.setText(phoneNumber);
    }

    // Return //0 for female, 1 for male, -1 on no selection
    private String getGender() {
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);

        if (male.isChecked())
            return "MALE";
        if (female.isChecked())
            return "FEMALE";
        else
            return "";
    }

    private void setGender(String gender) {
        if (gender.equals("MALE")) {
            RadioButton button = findViewById(R.id.male);
            button.toggle();
        }
        if (gender.equals("FEMALE")) {
            RadioButton button = findViewById(R.id.female);
            button.toggle();
        }
        if (gender.equals("")) {
            RadioButton button = findViewById(R.id.none);
            button.toggle();
        }

    }

    private String getAddress() {
        EditText address = findViewById(R.id.home_editText);
        return address.getText().toString();
    }

    private String getCity() {
        EditText city = findViewById(R.id.city_editText);
        return city.getText().toString();
    }

    private void setCity(String city) {
        EditText text = findViewById(R.id.city_editText);
        text.setText(city);
    }

    private String getState() {
        EditText state = findViewById(R.id.state_editText);
        return state.getText().toString();
    }

    private void setState(String state) {
        EditText text = findViewById(R.id.state_editText);
        text.setText(state);
    }

    private String getZip() {
        EditText zip = findViewById(R.id.zipCode_editText);
        if (zip.getText().toString().equals(""))
            return "0";

        return zip.getText().toString();
    }

    private String getLicencePlate() {
        EditText plate = findViewById(R.id.licencePlateNumber_editText);
        return plate.getText().toString();
    }

    private String getVehicle() {
        EditText vehicle = findViewById(R.id.vehicle_editText);
        return vehicle.getText().toString();
    }

    private void setVehicle(String vehicle) {
        EditText text = findViewById(R.id.vehicle_editText);
        text.setText(vehicle);
    }

    private String getHeight() {
        EditText height = findViewById(R.id.heightCentimeters_editText);
        if (height.getText().toString().equals(""))
            return "0";

        return height.getText().toString();
    }

    private void setHeight(String height) {
        EditText text = findViewById(R.id.heightCentimeters_editText);
        if (!height.equals("0"))
            text.setText(height);
    }

    private String getWeight() {
        EditText weight = findViewById(R.id.weightKilograms_editText);
        if (weight.getText().toString().equals(""))
            return "0";

        return weight.getText().toString();
    }

    private void setWeight(String weight) {
        EditText text = findViewById(R.id.weightKilograms_editText);
        if (!weight.equals("0"))
            text.setText(weight);
    }

    private String getBloodType() {
        RadioButton a = findViewById(R.id.bloodA);
        RadioButton b = findViewById(R.id.bloodB);
        RadioButton ab = findViewById(R.id.bloodAB);
        RadioButton o = findViewById(R.id.bloodO);
        CheckBox rh = findViewById(R.id.rh_checkBox);

        if (a.isChecked()) {
            if (rh.isChecked())
                return "A+";
            else
                return "A-";
        }
        if (b.isChecked()) {
            if (rh.isChecked())
                return "B+";
            else
                return "B-";
        }
        if (ab.isChecked()) {
            if (rh.isChecked())
                return "AB+";
            else
                return "AB-";
        }
        if (o.isChecked()) {
            if (rh.isChecked())
                return "O+";
            else
                return "O-";
        }
        return "";
    }

    private void setBloodType(String bloodType) {
        CheckBox rh = findViewById(R.id.rh_checkBox);
        if (bloodType.contains("+"))
            rh.toggle();
        bloodType = bloodType.replace("+", "");
        if (bloodType.equals("A")) {
            RadioButton a = findViewById(R.id.bloodA);
            a.toggle();
        }
        if (bloodType.equals("B")) {
            RadioButton b = findViewById(R.id.bloodB);
            b.toggle();
        }
        if (bloodType.equals("AB")) {
            RadioButton ab = findViewById(R.id.bloodAB);
            ab.toggle();
        }
        if (bloodType.equals("O")) {
            RadioButton o = findViewById(R.id.bloodO);
            o.toggle();
        }

    }

    private void loadJson() {
        String id = sharedPreferences.getString(idKey, "");

        if (id.equals(""))
            return;

        //Make get request
        com.android.volley.Response.Listener<JSONObject> listener = new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                populateJSONValues(response);
            }
        };
        Networking.getPersonalInfo(id, listener);
    }

    /**
     * Populate data entry fields with existing JSON data.
     *
     * @param personalInfo JSON of personal information to populate data fields
     */
    public void populateJSONValues(JSONObject personalInfo) {
        String gender, firstName, middleName, lastName, homeAddress, city, state,
                dateOfBirth, licencePlateNumber, vehicle, bloodType, zipcode, heightCentimeters, weightKilograms, phoneNumber;

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
            phoneNumber = personalInfo.getString("phoneNumber");
        } catch (JSONException e) {
            throw new RuntimeException(e); // Non-recoverable
        }


        // Change hints for all fields
        setGender(gender);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
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

    private void setHomeAddress(String homeAddress) {
        EditText text = findViewById(R.id.home_editText);
        text.setText(homeAddress);
    }

    private void setZIP(String ZIP) {
        EditText text = findViewById(R.id.zipCode_editText);
        if (!ZIP.equals("0"))
            text.setText(ZIP);
    }

    private void setLicence(String licence) {
        EditText text = findViewById(R.id.licencePlateNumber_editText);
        text.setText(licence);
    }

    /**
     * Correct navigation bar is correct on resume
     */
    protected void onResume() {
        super.onResume();
        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_personal_info);
    }
}

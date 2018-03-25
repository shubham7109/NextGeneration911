package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonModel {

    private String id;
    private String phoneNumber;
    private String gender;
    private String firstName;
    private String middleName;
    private String lastName;
    private String homeAddress;
    private String city;
    private String state;
    private String zipcode;
    private String dateOfBirth;
    private String licencePlateNumber;
    private String vehicle;
    private String bloodType;
    private String heightCentimeters;
    private String weightKilograms;
    private String latitude;
    private String longitude;

    public PersonModel(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        phoneNumber = jsonObject.getString("phoneNumber");
        gender = jsonObject.getString("gender");
        firstName = jsonObject.getString("firstName");
        middleName = jsonObject.getString("middleName");
        lastName = jsonObject.getString("lastName");
        homeAddress = jsonObject.getString("homeAddress");
        city = jsonObject.getString("city");
        state = jsonObject.getString("state");
        zipcode = jsonObject.getString("zipcode");
        dateOfBirth = jsonObject.getString("dateOfBirth");
        licencePlateNumber = jsonObject.getString("licencePlateNumber");
        vehicle = jsonObject.getString("vehicle");
        bloodType = jsonObject.getString("bloodType");
        heightCentimeters = jsonObject.getString("heightCentimeters");
        weightKilograms = jsonObject.getString("weightKilograms");
        latitude = jsonObject.getString("latitude");
        longitude = jsonObject.getString("longitude");
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getHeightCentimeters() {
        return heightCentimeters;
    }

    public String getWeightKilograms() {
        return weightKilograms;
    }


}

package operator.Models;

import org.json.JSONObject;

public class PersonModel {

    private int id;
    private int phoneNumber;
    private int gender; //0 for female, 1 for male
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
    private int heightCentimeters;
    private int weightKilograms;

    public PersonModel(JSONObject jsonObject){
        id = jsonObject.getInt("id");
        phoneNumber = jsonObject.getInt("phoneNumber");
        gender = jsonObject.getInt("gender");
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
        heightCentimeters = jsonObject.getInt("heightCentimeters");
        weightKilograms = jsonObject.getInt("weightKilograms");
    }

    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getGender() {
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

    public int getHeightCentimeters() {
        return heightCentimeters;
    }

    public int getWeightKilograms() {
        return weightKilograms;
    }


}

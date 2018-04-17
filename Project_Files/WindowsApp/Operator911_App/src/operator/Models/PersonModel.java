package operator.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class to model PersonModel
 * @author Shubham Sharma
 */
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

    /**
     * Constructor set the instance variables
     * @param jsonObject Used to parse and set variables from given keys
     * @throws JSONException
     */
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

    /**
     *
     * @return Returns the latitude of the person
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @return Returns the longitude of the person
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @return Returns the ID of the person
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Returns the phone number of the person
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return Returns the gender of the person
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @return Returns the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return Returns the middle name of the person
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     *
     * @return Returns the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return Returns the home address of the person
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     *
     * @return Returns the city of the person
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @return Returns the state of the person
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @return Returns the zip code of the person
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *
     * @return Returns the date of birth of the person
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @return Returns the plate number of the person
     */
    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    /**
     *
     * @return Returns the vehicle of the person
     */
    public String getVehicle() {
        return vehicle;
    }

    /**
     *
     * @return Returns the blood type of the person
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     *
     * @return Returns the height of the person
     */
    public String getHeightCentimeters() {
        return heightCentimeters;
    }

    /**
     *
     * @return Returns the weight of the person
     */
    public String getWeightKilograms() {
        return weightKilograms;
    }
}

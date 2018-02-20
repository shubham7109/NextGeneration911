package person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
	
	@Id
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
	
	public Person() {
		super();
	}
	
	public Person(String id) {
		super();
		this.id = id;
		this.phoneNumber = "0";
		this.gender = "";
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.homeAddress = "";
		this.city = "";
		this.state = "";
		this.zipcode = "";
		this.dateOfBirth = "";
		this.licencePlateNumber = "";
		this.vehicle = "";
		this.bloodType = "";
		this.heightCentimeters = "";
		this.weightKilograms = "";
	}
	
	

	public Person(String id, String phoneNumber, String gender, String firstName, String middleName, String lastName,
			String homeAddress, String city, String state, String zipcode, String dateOfBirth,
			String licencePlateNumber, String vehicle, String bloodType, String heightCentimeters,
			String weightKilograms) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.homeAddress = homeAddress;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.dateOfBirth = dateOfBirth;
		this.licencePlateNumber = licencePlateNumber;
		this.vehicle = vehicle;
		this.bloodType = bloodType;
		this.heightCentimeters = heightCentimeters;
		this.weightKilograms = weightKilograms;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getDateOfBioperatorsrth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLicencePlateNumber() {
		return licencePlateNumber;
	}

	public void setLicencePlateNumber(String licencePlateNumber) {
		this.licencePlateNumber = licencePlateNumber;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getHeightCentimeters() {
		return heightCentimeters;
	}

	public void setHeightCentimeters(String heightCentimeters) {
		this.heightCentimeters = heightCentimeters;
	}

	public String getWeightKilograms() {
		return weightKilograms;
	}

	public void setWeightKilograms(String weightKilograms) {
		this.weightKilograms = weightKilograms;
	}

	
	
	
	
}

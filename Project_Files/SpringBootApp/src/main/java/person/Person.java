package person;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import address.*;
import callLog.*;
import car.*;
import contact.*;
import emergencyContact.*;
import medical.*;
import operator.*;
import textLog.*;

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
	
	private String dateOfBirth;
	private String licencePlateNumber;
	private String vehicle;
	private String bloodType;
	private String heightCentimeters;
	private String weightKilograms;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="address_FK")
	private Address address;
	

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

	public String getDateOfBirth() {
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
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
	
	
}

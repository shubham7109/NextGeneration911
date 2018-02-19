package person;

public class Person {
   
   private int id;
   private String phoneNumber;
   private String gender;
   private String firstName;
   private String middleName;
   private String lastName;
   private String homeAddress;
   private String city;
   private String state;
   private int zipcode;
   private String dateOfBirth;
   private String licencePlateNumber;
   private String vehicle;
   private String bloodType;
   private int heightCentimeters;
   private int weightKilograms;
   
   public Person(int id, String phoneNumber, String gender, String firstName,
              String middleName, String lastName,
         String homeAddress, String city,
              String state, int zipcode, String dateOfBirth,
         String licencePlateNumber, String vehicle, String bloodType,
              int heightCentimeters, int weightKilograms) {
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

   public Person() {
      super();
   }
   
   public Person(int id) {
      super();
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
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

   public int getZipcode() {
      return zipcode;
   }

   public void setZipcode(int zipcode) {
      this.zipcode = zipcode;
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

   public int getHeightCentimeters() {
      return heightCentimeters;
   }

   public void setHeightCentimeters(int heightCentimeters) {
      this.heightCentimeters = heightCentimeters;
   }

   public int getWeightKilograms() {
      return weightKilograms;
   }

   public void setWeightKilograms(int weightKilograms) {
      this.weightKilograms = weightKilograms;
   }
   
   
   
   
   
   
   
}
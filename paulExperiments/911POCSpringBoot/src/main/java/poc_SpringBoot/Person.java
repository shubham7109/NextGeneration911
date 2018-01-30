package poc_SpringBoot;

public class Person {
	
	private String id;
	private String name;
	private String DLnum; //Driver's License Number
	private String medicalHistory;
	private String dob; //date of birth TODO: consider date format in stead of string
	private String bloodType;
	private int age;
	private int gender;
	
	
	public Person() {
		
	}
	
	
	public Person(String id, String name, String dLnum, String medicalHistory, String dob, String bloodType, int age,
			int gender) {
		super();
		this.id = id;
		this.name = name;
		DLnum = dLnum;
		this.medicalHistory = medicalHistory;
		this.dob = dob;
		this.bloodType = bloodType;
		this.age = age;
		this.gender = gender;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDLnum() {
		return DLnum;
	}

	public void setDLnum(String dLnum) {
		DLnum = dLnum;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	
	
}

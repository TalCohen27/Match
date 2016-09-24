package utilsAndData;

import java.sql.Date;

public class User {
	public static final String DEFAULT_PHOTO = "profile.png";
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String aboutMe = null;
	private String sex;
	private Date birthday = null;
	private String education;
	private String area;
	private String language;
	private String maritalStatus;
	private int children;
	private String eyeColor;
	private String hairType;
	private String hairColor;
	private String origin;
	private int height;
	private int weight;
	private String earningLevel;
	private String religion;
	private String smoking;
	private String physique;
	private String intrestedIn;
	private String photo = null;
	private int partnerMaxHeight;
	private int partnerMinHeight;
	private int partnerMaxAge;
	private int partnerMinAge;
	private String partner_education;
	private String partner_area;
	private String partner_language;
	private String partner_maritalStatus;
	private String partner_eyeColor;
	private String partner_hairType;
	private String partner_hairColor;
	private String partner_origin;
	private String partner_earningLevel;
	private String partner_religion;
	private String partner_smoking;
	private String partner_physique;
	
	private int privacy_level;


	public User(Long globalUserId) { // //
		this.setId(globalUserId);
	}

	public User() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public java.sql.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date date) {
		this.birthday = date;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getHairType() {
		return hairType;
	}

	public void setHairType(String hairType) {
		this.hairType = hairType;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getEarningLevel() {
		return earningLevel;
	}

	public void setEarningLevel(String earningLevel) {
		this.earningLevel = earningLevel;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}

	public String getPhysique() {
		return physique;
	}

	public void setPhysique(String physique) {
		this.physique = physique;
	}

	public String getIntrestedIn() {
		return intrestedIn;
	}

	public void setIntrestedIn(String intrestedIn) {
		this.intrestedIn = intrestedIn;
	}

	public Long getId() { // //
		return id;
	}

	public void setId(Long globalUserId) { // //
		this.id = globalUserId;
	}

	public String getPhoto() {
		if (photo == null)
			return DEFAULT_PHOTO;
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public void setPartnerMaxAge(int partnerMaxAge) {
		this.partnerMaxAge = partnerMaxAge;

	}

	public int getPartnerMaxAge() {
		return partnerMaxAge;

	}

	public int getPartnerMinAge() {
		return partnerMinAge;

	}

	public void setPartnerMinAge(int partnerMinAge) {
		this.partnerMinAge = partnerMinAge;
		;

	}

	public void setPartnerMinHeight(int partnerMinHeight) {
		this.partnerMinHeight = partnerMinHeight;

	}

	public int getPartnerMinHeight() {
		return partnerMinHeight;

	}

	public int getPartnerMaxHeight() {
		return partnerMaxHeight;

	}

	public void setPartnerMaxHeight(int partnerMaxHeight) {
		this.partnerMaxHeight = partnerMaxHeight;

	}

	public String getPartner_religion() {
		return partner_religion;
	}

	public void setPartner_religion(String partner_religion) {
		this.partner_religion = partner_religion;
	}

	public String getPartner_earningLevel() {
		return partner_earningLevel;
	}

	public void setPartner_earningLevel(String partner_earningLevel) {
		this.partner_earningLevel = partner_earningLevel;
	}

	public String getPartner_physique() {
		return partner_physique;
	}

	public void setPartner_physique(String partner_physique) {
		this.partner_physique = partner_physique;
	}

	public String getPartner_smoking() {
		return partner_smoking;
	}

	public void setPartner_smoking(String partner_smoking) {
		this.partner_smoking = partner_smoking;
	}

	public String getPartner_origin() {
		return partner_origin;
	}

	public void setPartner_origin(String partner_origin) {
		this.partner_origin = partner_origin;
	}

	public String getPartner_hairColor() {
		return partner_hairColor;
	}

	public void setPartner_hairColor(String partner_hairColor) {
		this.partner_hairColor = partner_hairColor;
	}

	public String getPartner_hairType() {
		return partner_hairType;
	}

	public void setPartner_hairType(String partner_hairType) {
		this.partner_hairType = partner_hairType;
	}

	public String getPartner_eyeColor() {
		return partner_eyeColor;
	}

	public void setPartner_eyeColor(String partner_eyeColor) {
		this.partner_eyeColor = partner_eyeColor;
	}

	public String getPartner_maritalStatus() {
		return partner_maritalStatus;
	}

	public void setPartner_maritalStatus(String partner_maritalStatus) {
		this.partner_maritalStatus = partner_maritalStatus;
	}

	public String getPartner_language() {
		return partner_language;
	}

	public void setPartner_language(String partner_language) {
		this.partner_language = partner_language;
	}

	public String getPartner_area() {
		return partner_area;
	}

	public void setPartner_area(String partner_area) {
		this.partner_area = partner_area;
	}

	public String getPartner_education() {
		return partner_education;
	}

	public void setPartner_education(String partner_education) {
		this.partner_education = partner_education;
	}

	public void setPrivacyLevel(int privacyLevel) {
		this.privacy_level = privacyLevel;
		
	}
	
	public int getPrivacyLevel() {
		return this.privacy_level;
		
	}

}
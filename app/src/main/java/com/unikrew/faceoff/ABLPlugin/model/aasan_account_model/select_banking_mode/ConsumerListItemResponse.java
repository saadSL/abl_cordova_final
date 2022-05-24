package com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode;

import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.ConsumerKycDetail;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseAttachment;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponseStepperSections;

import java.io.Serializable;
import java.util.List;

public class ConsumerListItemResponse implements Serializable {
	private Object customerTitle;
	private Object kinMobile;
	private Object kinRelation;
	private List<AddressesItemResponse> addresses;
	private Object occupation;
	private Object employerAddress;
	private Object relationshipWithMinorId;
	private Object employerPostalCode;

	public GetConsumerAccountDetailsResponseStepperSections getStepperSections() {
		return stepperSections;
	}

	public void setStepperSections(GetConsumerAccountDetailsResponseStepperSections stepperSections) {
		this.stepperSections = stepperSections;
	}

	private Object portedMobileNetwork;

	public void setCustomerTitle(Object customerTitle) {
		this.customerTitle = customerTitle;
	}

	public void setKinMobile(Object kinMobile) {
		this.kinMobile = kinMobile;
	}

	public void setKinRelation(Object kinRelation) {
		this.kinRelation = kinRelation;
	}

	public void setAddresses(List<AddressesItemResponse> addresses) {
		this.addresses = addresses;
	}

	public void setOccupation(Object occupation) {
		this.occupation = occupation;
	}

	public void setEmployerAddress(Object employerAddress) {
		this.employerAddress = employerAddress;
	}

	public void setRelationshipWithMinorId(Object relationshipWithMinorId) {
		this.relationshipWithMinorId = relationshipWithMinorId;
	}

	public void setEmployerPostalCode(Object employerPostalCode) {
		this.employerPostalCode = employerPostalCode;
	}

	public void setPortedMobileNetwork(Object portedMobileNetwork) {
		this.portedMobileNetwork = portedMobileNetwork;
	}

	public void setTaxResidentInd(Object taxResidentInd) {
		this.taxResidentInd = taxResidentInd;
	}

	public void setIdTypeId(Object idTypeId) {
		this.idTypeId = idTypeId;
	}

	public void setPlaceOfIssue(Object placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public void setMobileNetworkId(Object mobileNetworkId) {
		this.mobileNetworkId = mobileNetworkId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setEducationId(Object educationId) {
		this.educationId = educationId;
	}

	public void setOccupationId(Integer occupationId) {
		this.occupationId = occupationId;
	}

	public void setCustomerNonResidentInd(Object customerNonResidentInd) {
		this.customerNonResidentInd = customerNonResidentInd;
	}

	public void setAdcFlagId(Object adcFlagId) {
		this.adcFlagId = adcFlagId;
	}

	public void setSuggestPlaceOfBirth(List<String> suggestPlaceOfBirth) {
		this.suggestPlaceOfBirth = suggestPlaceOfBirth;
	}

	public void setEmployerPhone(Object employerPhone) {
		this.employerPhone = employerPhone;
	}

	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}

	public void setProfession(Object profession) {
		this.profession = profession;
	}

	public void setKinCnic(Object kinCnic) {
		this.kinCnic = kinCnic;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}

	public void setCountryOfResidenceId(Object countryOfResidenceId) {
		this.countryOfResidenceId = countryOfResidenceId;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setMaritalStatusId(Object maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public void setNatureOfAccountId(Object natureOfAccountId) {
		this.natureOfAccountId = natureOfAccountId;
	}

	public void setEmployedSince(Object employedSince) {
		this.employedSince = employedSince;
	}

	public void setDesignation(Object designation) {
		this.designation = designation;
	}

	public void setCustomerTitleId(Object customerTitleId) {
		this.customerTitleId = customerTitleId;
	}

	public void setAttachments(List<GetConsumerAccountDetailsResponseAttachment> attachments) {
		this.attachments = attachments;
	}

	public void setGender(Object gender) {
		this.gender = gender;
	}

	public void setExistingAccountInd(boolean existingAccountInd) {
		this.existingAccountInd = existingAccountInd;
	}

	public void setCountryOfBirthPlaceId(Object countryOfBirthPlaceId) {
		this.countryOfBirthPlaceId = countryOfBirthPlaceId;
	}

	public void setCustomerNtn(String customerNtn) {
		this.customerNtn = customerNtn;
	}

	public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
		this.rdaCustomerProfileId = rdaCustomerProfileId;
	}

	public void setNatureOfBusiness(Object natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public void setCustomerCity(Object customerCity) {
		this.customerCity = customerCity;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setExpiryDate(Object expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public void setKinAddress(Object kinAddress) {
		this.kinAddress = kinAddress;
	}

	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
	}

	public void setSuggestMotherNames(List<String> suggestMotherNames) {
		this.suggestMotherNames = suggestMotherNames;
	}

	public void setAccountInformation(AccountInformationResponse accountInformation) {
		this.accountInformation = accountInformation;
	}

	public ConsumerKycDetail getConsumerKycDetail() {
		return consumerKycDetail;
	}

	public void setConsumerKycDetail(ConsumerKycDetail consumerKycDetail) {
		this.consumerKycDetail = consumerKycDetail;
	}

	public void setReferrerCp(Object referrerCp) {
		this.referrerCp = referrerCp;
	}

	public void setPrimary(boolean primary) {
		isPrimary = primary;
	}

	public void setExistingCustomerInd(boolean existingCustomerInd) {
		this.existingCustomerInd = existingCustomerInd;
	}

	public void setFatherHusbandName(String fatherHusbandName) {
		this.fatherHusbandName = fatherHusbandName;
	}

	public void setNationalityTypeId(int nationalityTypeId) {
		this.nationalityTypeId = nationalityTypeId;
	}

	public void setKinName(Object kinName) {
		this.kinName = kinName;
	}

	public void setGenderId(Object genderId) {
		this.genderId = genderId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public void setMobileNoCountryCodeId(Object mobileNoCountryCodeId) {
		this.mobileNoCountryCodeId = mobileNoCountryCodeId;
	}

	public void setEmploymentTypeId(Object employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public void setNearestLandmark(String nearestLandmark) {
		this.nearestLandmark = nearestLandmark;
	}

	public void setEmployerEmail(Object employerEmail) {
		this.employerEmail = employerEmail;
	}

	public void setReferredBy(Object referredBy) {
		this.referredBy = referredBy;
	}

	public void setNameOfOrganization(Object nameOfOrganization) {
		this.nameOfOrganization = nameOfOrganization;
	}

	private Object taxResidentInd;
	private Object idTypeId;
	private Object placeOfIssue;
	private Object mobileNetworkId;
	private String  emailAddress;
	private Object educationId;
	private Integer occupationId = null;
	private Object customerNonResidentInd;
	private Object adcFlagId;
	private List<String> suggestPlaceOfBirth;
	private Object employerPhone;
	private Integer professionId = null;
	private Object profession;
	private Object kinCnic;
	private String customerBranch;
	private String motherMaidenName;
	private Object countryOfResidenceId;
	private String accessToken;
	private Object maritalStatusId;
	private int statusId;
	private Object natureOfAccountId;
	private Object employedSince;
	private Object designation;
	private Object customerTitleId;
	private List<GetConsumerAccountDetailsResponseAttachment> attachments;
	private Object gender;
	private boolean existingAccountInd;
	private Object countryOfBirthPlaceId;
	private GetConsumerAccountDetailsResponseStepperSections stepperSections;
	private String customerNtn;
	private int rdaCustomerProfileId;
	private Object natureOfBusiness;
	private Object customerCity;
	private String idNumber;
	private Object expiryDate;
	private int customerTypeId;
	private Object kinAddress;
	private String cityOfBirth;
	private List<String> suggestMotherNames;
	private AccountInformationResponse accountInformation;
	public ConsumerKycDetail consumerKycDetail;
	private Object referrerCp;
	private boolean isPrimary;
	private boolean existingCustomerInd;
	private String fatherHusbandName;
	private int nationalityTypeId;
	private Object kinName;
	private Object genderId;
	private String fullName;
	private String dateOfBirth;
	private String mobileNo;
	private String dateOfIssue;
	private Object mobileNoCountryCodeId;
	private Object employmentTypeId;
	private String nearestLandmark;
	private String customerAddress;

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	private Object employerEmail;
	private Object referredBy;
	private Object nameOfOrganization;

	public Object getCustomerTitle(){
		return customerTitle;
	}

	public Object getKinMobile(){
		return kinMobile;
	}

	public Object getKinRelation(){
		return kinRelation;
	}

	public List<AddressesItemResponse> getAddresses(){
		return addresses;
	}

	public Object getOccupation(){
		return occupation;
	}

	public Object getEmployerAddress(){
		return employerAddress;
	}

	public Object getRelationshipWithMinorId(){
		return relationshipWithMinorId;
	}

	public Object getEmployerPostalCode(){
		return employerPostalCode;
	}

	public Object getPortedMobileNetwork(){
		return portedMobileNetwork;
	}

	public Object getTaxResidentInd(){
		return taxResidentInd;
	}

	public Object getIdTypeId(){
		return idTypeId;
	}

	public Object getPlaceOfIssue(){
		return placeOfIssue;
	}

	public Object getMobileNetworkId(){
		return mobileNetworkId;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public Object getEducationId(){
		return educationId;
	}

	public Integer getOccupationId(){
		return occupationId;
	}

	public Object getCustomerNonResidentInd(){
		return customerNonResidentInd;
	}

	public Object getAdcFlagId(){
		return adcFlagId;
	}

	public List<String> getSuggestPlaceOfBirth(){
		return suggestPlaceOfBirth;
	}

	public Object getEmployerPhone(){
		return employerPhone;
	}

	public Integer getProfessionId(){
		return professionId;
	}

	public Object getProfession(){
		return profession;
	}

	public Object getKinCnic(){
		return kinCnic;
	}

	public String getCustomerBranch(){
		return customerBranch;
	}

	public String getMotherMaidenName(){
		return motherMaidenName;
	}

	public Object getCountryOfResidenceId(){
		return countryOfResidenceId;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public Object getMaritalStatusId(){
		return maritalStatusId;
	}

	public int getStatusId(){
		return statusId;
	}

	public Object getNatureOfAccountId(){
		return natureOfAccountId;
	}

	public Object getEmployedSince(){
		return employedSince;
	}

	public Object getDesignation(){
		return designation;
	}

	public Object getCustomerTitleId(){
		return customerTitleId;
	}

	public List<GetConsumerAccountDetailsResponseAttachment> getAttachments(){
		return attachments;
	}

	public Object getGender(){
		return gender;
	}

	public boolean isExistingAccountInd(){
		return existingAccountInd;
	}

	public Object getCountryOfBirthPlaceId(){
		return countryOfBirthPlaceId;
	}



	public String getCustomerNtn(){
		return customerNtn;
	}

	public int getRdaCustomerProfileId(){
		return rdaCustomerProfileId;
	}

	public Object getNatureOfBusiness(){
		return natureOfBusiness;
	}

	public Object getCustomerCity(){
		return customerCity;
	}

	public String getIdNumber(){
		return idNumber;
	}

	public Object getExpiryDate(){
		return expiryDate;
	}

	public int getCustomerTypeId(){
		return customerTypeId;
	}

	public Object getKinAddress(){
		return kinAddress;
	}

	public String getCityOfBirth(){
		return cityOfBirth;
	}

	public List<String> getSuggestMotherNames(){
		return suggestMotherNames;
	}

	public AccountInformationResponse getAccountInformation(){
		return accountInformation;
	}

	public Object getReferrerCp(){
		return referrerCp;
	}

	public boolean isPrimary(){
		return isPrimary;
	}

	public boolean isExistingCustomerInd(){
		return existingCustomerInd;
	}

	public String getFatherHusbandName(){
		return fatherHusbandName;
	}

	public int getNationalityTypeId(){
		return nationalityTypeId;
	}

	public Object getKinName(){
		return kinName;
	}

	public Object getGenderId(){
		return genderId;
	}

	public String getFullName(){
		return fullName;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public String getDateOfIssue(){
		return dateOfIssue;
	}

	public Object getMobileNoCountryCodeId(){
		return mobileNoCountryCodeId;
	}

	public Object getEmploymentTypeId(){
		return employmentTypeId;
	}

	public String getNearestLandmark(){
		return nearestLandmark;
	}

	public Object getEmployerEmail(){
		return employerEmail;
	}

	public Object getReferredBy(){
		return referredBy;
	}

	public Object getNameOfOrganization(){
		return nameOfOrganization;
	}

}
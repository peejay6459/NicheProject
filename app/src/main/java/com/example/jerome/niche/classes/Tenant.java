package com.example.jerome.niche.classes;

/**
 * Created by Jerome on 23/10/2016.
 */

public class Tenant extends NicheUser {
    private String passportNumber;
    private String idNumber;
    private String previousCountry;
    private String relativeName;
    private String relativeRelationship;
    private String relativeAddress;
    private String phoneOrMobileNumber;

    public Tenant(int id, String username, String password, String email, String userType, String name, String gender, String dob, String telephoneNumber, String mobileNumber, String address, String country, char accountStatus) {
        super(id, username, password, email, userType, name, gender, dob, telephoneNumber, mobileNumber, address, country, accountStatus);
    }

    public Tenant(int id, String username, String password, String email, String userType, String name, String gender, String dob, String telephoneNumber, String mobileNumber, String address, String country, char accountStatus, String passportNumber, String idNumber, String previousCountry, String relativeName, String relativeRelationship, String relativeAddress, String phoneOrMobileNumber) {
        super(id, username, password, email, userType, name, gender, dob, telephoneNumber, mobileNumber, address, country, accountStatus);
        this.passportNumber = passportNumber;
        this.idNumber = idNumber;
        this.previousCountry = previousCountry;
        this.relativeName = relativeName;
        this.relativeRelationship = relativeRelationship;
        this.relativeAddress = relativeAddress;
        this.phoneOrMobileNumber = phoneOrMobileNumber;
    }

    public Tenant(String name, String gender, String dob, String telephoneNumber, String mobileNumber, String address, String country, String passportNumber, String idNumber, String previousCountry, String relativeName, String relativeRelationship, String relativeAddress, String phoneOrMobileNumber) {
        super(name, gender, dob, telephoneNumber, mobileNumber, address, country);
        this.passportNumber = passportNumber;
        this.idNumber = idNumber;
        this.previousCountry = previousCountry;
        this.relativeName = relativeName;
        this.relativeRelationship = relativeRelationship;
        this.relativeAddress = relativeAddress;
        this.phoneOrMobileNumber = phoneOrMobileNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPreviousCountry() {
        return previousCountry;
    }

    public void setPreviousCountry(String previousCountry) {
        this.previousCountry = previousCountry;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public String getRelativeRelationship() {
        return relativeRelationship;
    }

    public void setRelativeRelationship(String relativeRelationship) {
        this.relativeRelationship = relativeRelationship;
    }

    public String getRelativeAddress() {
        return relativeAddress;
    }

    public void setRelativeAddress(String relativeAddress) {
        this.relativeAddress = relativeAddress;
    }

    public String getPhoneOrMobileNumber() {
        return phoneOrMobileNumber;
    }

    public void setPhoneOrMobileNumber(String phoneOrMobileNumber) {
        this.phoneOrMobileNumber = phoneOrMobileNumber;
    }
}

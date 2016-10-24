package com.example.jerome.niche.classes;

/**
 * Created by Jerome on 23/10/2016.
 */

public class NicheUser {
    private int id;
    private String username;
    private String password;
    private String email;
    private String userType;
    private String name;
    private String gender;
    private String dob;
    private String telephoneNumber;
    private String mobileNumber;
    private String address;
    private String country;
    //private char accountStatus;

    public NicheUser(int id, String username, String password, String email, String userType, String name, String gender, String dob, String telephoneNumber, String mobileNumber, String address, String country, char accountStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.telephoneNumber = telephoneNumber;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.country = country;
        //this.accountStatus = accountStatus;
    }

    public NicheUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public NicheUser(String name, String gender, String dob, String telephoneNumber, String mobileNumber, String address, String country) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.telephoneNumber = telephoneNumber;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.country = country;
        //this.accountStatus = accountStatus;
    }


    public NicheUser(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /*public char getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(char accountStatus) {
        this.accountStatus = accountStatus;
    }
    */

}

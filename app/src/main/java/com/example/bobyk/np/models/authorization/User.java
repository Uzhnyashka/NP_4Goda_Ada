package com.example.bobyk.np.models.authorization;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/5/17.
 */


@IgnoreExtraProperties
public class User {

    private String firstName;
    private String surname;
    private String middleName;
    private String phoneNumber;
    private String photoUrl;
    private String email;
    private String role;


    public User() {

    }

    public User(String email, String firstName, String surname, String middleName, String phoneNumber,
                String photoUrl, String role) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.photoUrl = photoUrl;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

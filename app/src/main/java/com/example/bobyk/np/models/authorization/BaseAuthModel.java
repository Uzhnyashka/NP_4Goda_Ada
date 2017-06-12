package com.example.bobyk.np.models.authorization;

import com.example.bobyk.np.models.main.Point;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

@IgnoreExtraProperties
public class BaseAuthModel {

    private String email;
    private String firstName;
    private String surname;
    private String middleName;
    private String role;
    private String phoneNumber;
    private String photoUrl;
    private List<Point> points;
    private Long signInTime;
    private Long singOutTime;

    public BaseAuthModel() {

    }

    public BaseAuthModel(String firstName, String surname, String middleName,
                  String email, String role, String phoneNumber,
                  String photoUrl) {
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.middleName = middleName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
    }

//    public BaseAuthModel(String firstName, String surname, String middleName,
//                         String email, String role, String phoneNumber,
//                         String photoUrl, Double latitude, Double longitude) {
//        this.email = email;
//        this.firstName = firstName;
//        this.surname = surname;
//        this.middleName = middleName;
//        this.role = role;
//        this.phoneNumber = phoneNumber;
//        this.photoUrl = photoUrl;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setSignInTime(Long signInTime) {
        this.signInTime = signInTime;
    }

    public void setSingOutTime(Long singOutTime) {
        this.singOutTime = singOutTime;
    }

    public Long getSignInTime() {
        return signInTime;
    }

    public Long getSingOutTime() {
        return singOutTime;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
}

package com.example.bobyk.np.models.main;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/9/17.
 */

@IgnoreExtraProperties
public class Delivery {

    private String driverId;
    private String id;
    private String status;
    private Double price;
    private String senderEmail;
    private String senderLocation;
    private String recipientEmail;
    private String recipientLocation;
    private String sendDate;
    private String recipientDate;
    private String senderName;
    private String recipientName;
    private String senderId;
    private String recipientId;
    private String senderPhone;
    private String recipientPhone;
    private Double weight;

    public Delivery() {

    }

    public Delivery(String id, String status, String driverId, String senderEmail, String senderLocation,
                    String recipientEmail, String recipientLocation, Double price, Double weight,
                    String sendDate, String recipientDate, String senderId, String recipientId) {
        this.id = id;
        this.status = status;
        this.driverId = driverId;
        this.senderEmail = senderEmail;
        this.senderLocation = senderLocation;
        this.recipientEmail = recipientEmail;
        this.recipientLocation = recipientLocation;
        this.price = price;
        this.weight = weight;
        this.sendDate = sendDate;
        this.recipientDate = recipientDate;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRecipientLocation() {
        return recipientLocation;
    }

    public void setRecipientLocation(String recipientLocation) {
        this.recipientLocation = recipientLocation;
    }

    public String getSenderLocation() {
        return senderLocation;
    }

    public void setSenderLocation(String senderLocation) {
        this.senderLocation = senderLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRecipientDate() {
        return recipientDate;
    }

    public void setRecipientDate(String recipientDate) {
        this.recipientDate = recipientDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }
}



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
    private Long price;
    private String senderId;
    private String senderLocation;
    private String recipientId;
    private String recipientLocation;
    private String sendDate;
    private String recipientDate;
    private Long weight;

    public Delivery() {

    }

    public Delivery(String id, String status, String driverId, String senderId, String senderLocation,
                    String recipientId, String recipientLocation, Long price, Long weight,
                    String sendDate, String recipientDate) {
        this.id = id;
        this.status = status;
        this.driverId = driverId;
        this.senderId = senderId;
        this.senderLocation = senderLocation;
        this.recipientId = recipientId;
        this.recipientLocation = recipientLocation;
        this.price = price;
        this.weight = weight;
        this.sendDate = sendDate;
        this.recipientDate = recipientDate;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public Long getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientLocation() {
        return recipientLocation;
    }

    public void setRecipientLocation(String recipientLocation) {
        this.recipientLocation = recipientLocation;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
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
}

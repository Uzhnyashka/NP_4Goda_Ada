package com.example.bobyk.np.models.main;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/18/17.
 */

@IgnoreExtraProperties
public class Message {

    private String deliveryId;
    private String recipientId;
    private String status;
    private Long date;
    private String senderFullName;
    private String senderLocation;
    private String recipientLocation;
    private String recipientFullName;
    private String id;

    public Message() {

    }

    public Message(String deliveryId, String recipientId, String status, Long date) {
        this.deliveryId = deliveryId;
        this.recipientId = recipientId;
        this.status = status;
        this.date = date;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getRecipientLocation() {
        return recipientLocation;
    }

    public void setRecipientLocation(String recipientLocation) {
        this.recipientLocation = recipientLocation;
    }

    public String getRecipientFullName() {
        return recipientFullName;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setRecipientFullName(String recipientFullName) {
        this.recipientFullName = recipientFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public String getSenderLocation() {
        return senderLocation;
    }

    public void setSenderLocation(String senderLocation) {
        this.senderLocation = senderLocation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.example.bobyk.np.models.main;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/7/17.
 */

@IgnoreExtraProperties
public class Notification {

    private String recipientName;
    private String senderName;
    private String recipientLocation;
    private String senderLocation;
    private String date;

    public Notification() {

    }

    public Notification(String recipientName, String recipientLocation, String senderName, String senderLocation,
                        String date) {
        this.recipientLocation = recipientLocation;
        this.senderLocation = senderLocation;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.date = date;
    }

    public String getRecipientLocation() {
        return recipientLocation;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getSenderLocation() {
        return senderLocation;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setRecipientLocation(String recipientLocation) {
        this.recipientLocation = recipientLocation;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setSenderLocation(String senderLocation) {
        this.senderLocation = senderLocation;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

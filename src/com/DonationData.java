package com;

import java.io.Serializable;

public class DonationData implements Serializable {
    private String donorName;
    private String donorEmail;
    private double amount;
    private String message;
    private long timestamp;
    
    // Additional fields for legacy support
    private String disasterType;
    private String location;
    private String donationType;
    private String amountOrDescription;
    private String additionalInfo;

    // New constructor (current usage)
    public DonationData(String donorName, String donorEmail, double amount, String message) {
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.amount = amount;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        
        // Set default values for legacy fields
        this.disasterType = "Umum";
        this.location = "Tidak ditentukan";
        this.donationType = "Uang";
        this.amountOrDescription = "Rp " + String.format("%.0f", amount);
        this.additionalInfo = "Transfer Bank";
    }

    // Legacy constructor (for DonationPage compatibility)
    public DonationData(String donorName, String donorEmail, String disasterType, String location, String donationType) {
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.disasterType = disasterType;
        this.location = location;
        this.donationType = donationType;
        this.timestamp = System.currentTimeMillis();
        
        // Set default values
        this.amount = 0.0;
        this.message = "Donasi " + donationType;
        this.amountOrDescription = donationType.equals("Uang") ? "Rp 0" : "Barang";
        this.additionalInfo = "Tidak ditentukan";
    }

    // Full constructor
    public DonationData(String donorName, String donorEmail, String disasterType, String location, String donationType, String amountOrDescription, String additionalInfo) {
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.disasterType = disasterType;
        this.location = location;
        this.donationType = donationType;
        this.amountOrDescription = amountOrDescription;
        this.additionalInfo = additionalInfo;
        this.timestamp = System.currentTimeMillis();
        
        // Try to parse amount if it's money
        if (donationType != null && donationType.equals("Uang")) {
            try {
                String cleanAmount = amountOrDescription.replaceAll("[^0-9.]", "");
                this.amount = Double.parseDouble(cleanAmount);
            } catch (NumberFormatException e) {
                this.amount = 0.0;
            }
        } else {
            this.amount = 0.0;
        }
        
        this.message = "Donasi " + donationType + ": " + amountOrDescription;
    }

    // Getters
    public String getDonorName() {
        return donorName;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public double getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Legacy getters
    public String getDisasterType() {
        return disasterType != null ? disasterType : "Umum";
    }

    public String getLocation() {
        return location != null ? location : "Tidak ditentukan";
    }

    public String getDonationType() {
        return donationType != null ? donationType : "Uang";
    }

    public String getAmountOrDescription() {
        return amountOrDescription != null ? amountOrDescription : ("Rp " + String.format("%.0f", amount));
    }

    public String getAdditionalInfo() {
        return additionalInfo != null ? additionalInfo : "Tidak ada info tambahan";
    }

    // Setters
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public void setAmountOrDescription(String amountOrDescription) {
        this.amountOrDescription = amountOrDescription;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return donorName + " - " + getDonationType() + ": " + getAmountOrDescription();
    }
}

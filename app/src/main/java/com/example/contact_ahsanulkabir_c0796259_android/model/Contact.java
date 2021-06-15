package com.example.contact_ahsanulkabir_c0796259_android.model;

public class Contact {
    int id;
    String firstName, lastName, emailID, phoneNumber,address;

    public Contact(int id, String firstName, String lastName, String emailID, String phoneNumber, String address ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}

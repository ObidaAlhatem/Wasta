package com.devneoxmy.wasta;

public class Service {

    private String Name;
    private String Details;
    private String Address;
    private Integer PhNumber;

    public Service() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getPhNumber() {
        return PhNumber;
    }

    public void setPhNumber(Integer phNumber) {
        PhNumber = phNumber;
    }
}

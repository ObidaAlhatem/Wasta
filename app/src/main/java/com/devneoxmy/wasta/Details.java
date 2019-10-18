package com.devneoxmy.wasta;

public class Details {
    String ServiceName;
    String ServiceDetails;
    String PhNumber;

    public Details(String serviceName, String serviceDetails, String phNumber) {
        this.ServiceName = serviceName;
       this.ServiceDetails = serviceDetails;
        this.PhNumber = phNumber;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        this.ServiceName = serviceName;
    }

    public String getServiceDetails() {
        return ServiceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.ServiceDetails = serviceDetails;
    }

    public String getPhNumber() {
        return PhNumber;
    }

    public void setPhNumber(String phNumber) {
       this.PhNumber = phNumber;
    }
}

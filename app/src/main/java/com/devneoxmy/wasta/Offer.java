package com.devneoxmy.wasta;

public class Offer {
    private String mServiceName;
    private String mServiceDetails;
    private int mRatingBar;

    public Offer(String serviceName, String serviceDetails, int ratingBar){
        mServiceName = serviceName;
        mServiceDetails = serviceDetails;
        mRatingBar = ratingBar;
    }

    public String getServiceName(){
        return mServiceName;
    }

    public String getServiceDetails(){
        return mServiceDetails;
    }

    public int getRatingBar(){
        return mRatingBar;
    }

}

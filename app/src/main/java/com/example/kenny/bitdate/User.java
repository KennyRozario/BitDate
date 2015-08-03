package com.example.kenny.bitdate;

import java.io.Serializable;

/**
 * Created by Kenny on 2015-07-26.
 */
public class User implements Serializable{

    private String mFirstName;
    private String mPictureURL;
    private String mID;
    private String mFacebookID;

    public String getLargePictureURL(){
        return "https://graph.facebook.com/v2.3/" + mFacebookID + "/picture?type=large";
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getPictureURL() {
        return mPictureURL;
    }

    public void setPictureURL(String pictureURL) {
        mPictureURL = pictureURL;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getFacebookID() {
        return mFacebookID;
    }

    public void setFacebookID(String facebookID) {
        mFacebookID = facebookID;
    }
}

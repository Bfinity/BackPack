package com.example.bfinerocks.backpack.models;

import android.os.Parcel;

import com.example.bfinerocks.backpack.constants.UserTypes;

/**
 * Created by BFineRocks on 12/3/14.
 */
public class UserModel implements android.os.Parcelable {

    private String userName;
    private String userEmail;
    private String userFullName;
    private String userType;
    private String userObjectID;
    private UserTypes userEnum;

    public UserModel(String userName, String userEmail, String userFullName, String userType){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserObjectID(String userObjectID){
        this.userObjectID = userObjectID;
    }

    public String getUserObjectID(){
        return userObjectID;
    }

    public void setUserEnum(String stringUserType){
        if(stringUserType.equalsIgnoreCase("teacher")){
            this.userEnum = UserTypes.TEACHER;
        }
        else if(stringUserType.equalsIgnoreCase("student")){
            this.userEnum =  UserTypes.STUDENT;
        }
        else if(stringUserType.equalsIgnoreCase("parent")){
            this.userEnum = UserTypes.PARENT;
        }
    }

    public UserTypes getUserEnum(){
        return userEnum;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userEmail);
        dest.writeString(this.userFullName);
        dest.writeString(this.userType);
        dest.writeString(this.userObjectID);
    }

    private UserModel(Parcel in) {
        this.userName = in.readString();
        this.userEmail = in.readString();
        this.userFullName = in.readString();
        this.userType = in.readString();
        this.userObjectID = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}

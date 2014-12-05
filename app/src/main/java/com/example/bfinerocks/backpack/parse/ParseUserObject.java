package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.UserModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/25/14.
 */
public class ParseUserObject {


    private ParseUser user;
    private Boolean loginResponse;
    public static final String USER_TYPE_KEY = "userType";
    public static final String USER_FULL_NAME = "fullName";
    private ArrayList<ParseUser> userArrayList;


    public void createNewParseUser(String userName, String password, String userType, String fullName, String emailAddress) throws ParseException{
        user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(emailAddress);
        user.put(USER_TYPE_KEY, userType);
        user.put(USER_FULL_NAME, fullName);

        user.signUp();
/*
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("signUp", "Success");
                    isLogInSuccessful(true);
                }
                else{
                   Log.i("signUp", e.getMessage());
                    isLogInSuccessful(false);
                }
            }
        });*/
    }

    public void signInExistingUser(String userName, String password) throws ParseException{

        ParseUser.logIn(userName, password);
/*        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(parseUser != null){
                    Log.i("signIn", "Success");
                    isLogInSuccessful(true);
                }
                else{
                    Log.i("signIn", e.getMessage());
                    isLogInSuccessful(false);
                }
            }
        });*/
    }

    public void isLogInSuccessful(Boolean logInResponse){
        this.loginResponse = logInResponse;
    }

    public Boolean getLoginResponse(){
        return loginResponse;
    }

    public ParseUser getCurrentUser(){
        return ParseUser.getCurrentUser();
    }


    public String getUserType(){
        return  ParseUser.getCurrentUser().getString(USER_TYPE_KEY);
    }

    public void updateListOfUsers(String userType, Classroom classroom) throws ParseException{
        ParseClassSectionObject classSection = new ParseClassSectionObject();
        classSection.queryClassroomByClassName(classroom);
        ParseObject parseClassObject = classSection.getQueriedClassroom();
        ParseQuery<ParseUser> parseUsers = ParseUser.getQuery();
        parseUsers.whereEqualTo(USER_TYPE_KEY, userType);
        parseUsers.whereEqualTo("classrooms", parseClassObject);

      //  setUserArrayList(parseUsers.find());

        parseUsers.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if(e == null) {
                    Log.i("parseUserArray", "Succes");
                    setUserArrayList(parseUsers);
                }
                else{
                    Log.i("parseUserArray", e.getMessage());
                }
            }
        });
    }

    public void updateListOfUsersBasedOnUserType(String userType){
        ParseQuery<ParseUser> parseUsers = ParseUser.getQuery();
        parseUsers.whereEqualTo(USER_TYPE_KEY, userType);
        parseUsers.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if(e == null) {
                    Log.i("parseUserArray", "Succes");
                    setUserArrayList(parseUsers);
                }
                else{
                    Log.i("parseUserArray", e.getMessage());
                }
            }
        });

    }

    public void setUserArrayList(List<ParseUser> list){
        userArrayList = new ArrayList<ParseUser>();
        userArrayList.addAll(list);
    }

    public ArrayList<ParseUser> getUserArrayList(){
        return userArrayList;
    }

    public UserModel convertParseUserIntoUserModel(ParseUser parseUser){
        UserModel userModel = new UserModel(parseUser.getUsername(), parseUser.getEmail(),
                parseUser.getString(USER_FULL_NAME), parseUser.getString(USER_TYPE_KEY));
        userModel.setUserObjectID(parseUser.getObjectId());
        return userModel;
    }

    public void searchForStudentUser(UserModel userModel) throws ParseException{
        ParseUser parseUser = new ParseUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo(USER_FULL_NAME, userModel.getUserFullName());
        query.whereEqualTo(parseUser.getEmail(), userModel.getUserEmail());
        setUserArrayList(query.find());
    }


}

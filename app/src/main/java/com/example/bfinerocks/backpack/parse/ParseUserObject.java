package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/25/14.
 */
public class ParseUserObject {


    private ParseUser user;
    private Boolean loginResponse;
    public static final String USER_TYPE_KEY = "userType";
    private ArrayList<ParseUser> userArrayList;


    public void createNewParseUser(String userName, String password, String userType){
        user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.put(USER_TYPE_KEY, userType);

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
        });
    }

    public void signInExistingUser(String userName, String password){
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
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
        });
    }

    public void isLogInSuccessful(Boolean logInResponse){
        this.loginResponse = logInResponse;
    }

    public Boolean getLoginResponse(){
        return loginResponse;
    }

    public ParseUser getCurrentUser(){
        user = ParseUser.getCurrentUser();
        return user;
    }


    public String getUserType(){
        getCurrentUser();
        return  user.getString(USER_TYPE_KEY);
    }

    public void updateListOfUsers(String userType){

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

}

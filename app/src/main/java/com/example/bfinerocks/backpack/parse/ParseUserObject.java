package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by BFineRocks on 11/25/14.
 */
public class ParseUserObject {

    private ParseUser user;
    private Boolean loginResponse;

    public void createNewParseUser(String userName, String password, String userType){
        user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.put("userType", userType);

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
}

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

    ParseUser user;

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
                }
                else{
                   Log.i("signUp", e.getMessage());
                }
            }
        });
    }

    public void signInExistingUser(String userName, String password){
        user.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(user != null){
                    Log.i("signIn", "Success");
                }
                else{
                    Log.i("signIn", e.getMessage());
                }
            }
        });
    }
}

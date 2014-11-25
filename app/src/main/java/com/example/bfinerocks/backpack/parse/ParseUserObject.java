package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by BFineRocks on 11/25/14.
 */
public class ParseUserObject {

    public void createNewParseUser(String userName, String password){
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);

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
}

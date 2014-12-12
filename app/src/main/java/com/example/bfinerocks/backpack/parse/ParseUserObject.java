package com.example.bfinerocks.backpack.parse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.bfinerocks.backpack.constants.ParseKeys;
import com.example.bfinerocks.backpack.constants.UserTypes;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.UserModel;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/25/14.
 */
public class ParseUserObject {

    ParseUserInterface parseUserInterface;
    private ParseUser user;
    private Boolean loginResponse;
    public static final String USER_TYPE_KEY = "userType";
    public static final String USER_FULL_NAME = "fullName";
    public static final String PARENT_RELATION = "studentsRelated";
    private ArrayList<ParseUser> userArrayList;
    private List<UserModel> listOfUserModels;
    private Handler mHandler;

    public ParseUserObject(){

    }

    public ParseUserObject(ParseUserInterface parseUserInterface){
        this.parseUserInterface = parseUserInterface;
    }

    public ParseUserObject(Handler handler){
        mHandler = handler;
    }


    public Runnable createNewParseUser(final String userName, final String password, final String userType,
                                   final String fullName, final String emailAddress){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                user = new ParseUser();
                user.setUsername(userName);
                user.setPassword(password);
                user.setEmail(emailAddress);
                user.put(USER_TYPE_KEY, userType);
                user.put(USER_FULL_NAME, fullName);
                try {
                    user.signUp();
                } catch (ParseException e) {
                    parseUserInterface.onLogInFailure(e.getMessage());
                }

            }
        };
        checkParseUserLoggedIn(ParseUser.getCurrentUser());
        return runnable;
    }

    public Runnable signInExistingUser(final String userName, final String password){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ParseUser.logIn(userName, password);
                } catch (ParseException e) {
                    parseUserInterface.onLogInFailure(e.getMessage());
                }
            }
        };
        checkParseUserLoggedIn(ParseUser.getCurrentUser());
        return runnable;
    }

    public void checkParseUserLoggedIn(ParseUser parseUser){
        if(parseUser != null){
           parseUserInterface.onLogInSuccess(convertParseUserIntoUserModel(parseUser));
           //sendUserModelDataBack(convertParseUserIntoUserModel(parseUser));
        }
    }

    public ParseUser getCurrentUser(){
        return ParseUser.getCurrentUser();
    }

    public String getUserType(){
        return  ParseUser.getCurrentUser().getString(USER_TYPE_KEY);
    }

    public UserTypes getUserTypeEnum(){
        if(getUserType().equalsIgnoreCase("teacher")){
            return UserTypes.TEACHER;
        }
        else if(getUserType().equalsIgnoreCase("student")){
            return UserTypes.STUDENT;
        }
        else if(getUserType().equalsIgnoreCase("parent")){
            return UserTypes.PARENT;
        }
       return null;
    }

    public Runnable updateListOfStudentsInClassroom(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseClassSectionObject classSection = new ParseClassSectionObject();
                ParseObject parseClassObject =  classSection.getParseClassroomObject(classroom);
                ParseQuery<ParseUser> parseUsers = ParseUser.getQuery();
             //   parseUsers.whereEqualTo(USER_TYPE_KEY, "Student");
                parseUsers.whereEqualTo(ParseKeys.CLASSROOM_RELATION_KEY, parseClassObject);
                try {
                    setUserArrayList(parseUsers.find());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        return runnable;
    }

    public Runnable updateListOfStudentUsersForOnParentUser() throws ParseException{
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation(PARENT_RELATION);
                ParseQuery<ParseUser> query = relation.getQuery();
                try {
                    setUserArrayList(query.find());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        return runnable;
    }

    public void setUserArrayList(List<ParseUser> list){
        listOfUserModels = new ArrayList<UserModel>();
        for(int i = 0; i < list.size(); i++){
            UserModel userModel = convertParseUserIntoUserModel(list.get(i));
            listOfUserModels.add(userModel);
        }
        parseUserInterface.listOfUsersReturned(listOfUserModels);
    }

    public ArrayList<ParseUser> getUserArrayList(){
        return userArrayList;
    }

    public UserModel convertParseUserIntoUserModel(ParseUser parseUser){
        UserModel userModel = new UserModel(parseUser.getUsername(), parseUser.getEmail(),
                parseUser.getString(USER_FULL_NAME), parseUser.getString(USER_TYPE_KEY));
        userModel.setUserEnum(parseUser.getString(USER_TYPE_KEY));
        userModel.setUserObjectID(parseUser.getObjectId());
        return userModel;
    }

    public Runnable searchForStudentUser(final String userFullName, final String userEmail) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo(USER_FULL_NAME, userFullName);
                query.whereEqualTo("email", userEmail);
                try {
                    setUserArrayList(query.find());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        return runnable;
/*        ParseUser parseUserFound = new ParseUser();

        setUserArrayList(query.find());
        for(ParseUser parseUser: getUserArrayList()){
            if(parseUser.getEmail().equals(userEmail)){
                parseUserFound = parseUser;
            }
        }

        return parseUserFound;*/
    }

    public void addParentStudentRelationship(UserModel userModel){
        ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation(PARENT_RELATION);
        relation.add(getUserByUID(userModel));
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    parseUserInterface.relationAddedOnParse(true);
                }
                else{
                    parseUserInterface.relationAddedOnParse(false);
                }
            }
        });
    }

    public ParseUser getUserByUID(UserModel userModel){
        ParseUser userFound = new ParseUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        try {
           userFound = query.get(userModel.getUserObjectID());
        }catch (ParseException e){

        }
        return userFound;
    }

    public void logOutCurrentUser(){
        ParseUser.logOut();
    }

    public void sendUserModelDataBack(UserModel userModel){
        Message userModelMessage = new Message();
        Bundle bundle = new Bundle();
        bundle.putParcelable("userModel", userModel);
        userModelMessage.setData(bundle);
        userModelMessage.setTarget(mHandler);
        userModelMessage.sendToTarget();
    }

    public interface ParseUserInterface{
        public void onLogInSuccess(UserModel userModel);
        public void onLogInFailure(String result);
        public void listOfUsersReturned(List<UserModel> listOfUsers);
        public void relationAddedOnParse(boolean relationSuccess);
    }





}

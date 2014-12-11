package com.example.bfinerocks.backpack.parse;

import com.example.bfinerocks.backpack.constants.UserTypes;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.UserModel;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
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
    public static final String PARENT_RELATION = "studentsRelated";
    private ArrayList<ParseUser> userArrayList;


    public void createNewParseUser(String userName, String password, String userType, String fullName, String emailAddress) throws ParseException{
        user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(emailAddress);
        user.put(USER_TYPE_KEY, userType);
        user.put(USER_FULL_NAME, fullName);

        user.signUp();

    }

    public void signInExistingUser(String userName, String password) throws ParseException{

        ParseUser.logIn(userName, password);

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

    public void updateListOfUsers(String userType, Classroom classroom) throws ParseException{
        ParseClassSectionObject classSection = new ParseClassSectionObject();
        classSection.getParseClassroomObject(classroom);
        ParseObject parseClassObject = classSection.getQueriedClassroom();
        ParseQuery<ParseUser> parseUsers = ParseUser.getQuery();
        parseUsers.whereEqualTo(USER_TYPE_KEY, userType);
        parseUsers.whereEqualTo("classrooms", parseClassObject);

        setUserArrayList(parseUsers.find());

/*        parseUsers.findInBackground(new FindCallback<ParseUser>() {
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
        });*/
    }

    public void updateListOfStudentUsersForOnParentUser() throws ParseException{
        ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation(PARENT_RELATION);
        ParseQuery<ParseUser> query = relation.getQuery();
        setUserArrayList(query.find());
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

    public ParseUser searchForStudentUser(String userFullName, String userEmail) throws ParseException{
        ParseUser parseUserFound = new ParseUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo(USER_FULL_NAME, userFullName);
        setUserArrayList(query.find());
        for(ParseUser parseUser: getUserArrayList()){
            if(parseUser.getEmail().equals(userEmail)){
                parseUserFound = parseUser;
            }
        }

        return parseUserFound;
    }

    public void addParentStudentRelationship(UserModel userModel) throws ParseException{
        ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation(PARENT_RELATION);
        relation.add(getUserByUID(userModel));
        ParseUser.getCurrentUser().save();
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





}

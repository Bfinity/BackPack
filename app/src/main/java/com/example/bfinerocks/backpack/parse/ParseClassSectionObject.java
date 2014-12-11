package com.example.bfinerocks.backpack.parse;

import android.os.Handler;
import android.util.Log;

import com.example.bfinerocks.backpack.models.Classroom;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class ParseClassSectionObject {

    private ParseClassObjectInterface parseClassInterface;
  //  public UIHandler uIHandler = new UIHandler(Looper.getMainLooper());

    ParseUserObject parseUserObject = new ParseUserObject();
    ParseObject parseClassroomObject;
    List<Classroom> myClasses = new ArrayList<Classroom>();

    public static String CLASSROOM_KEY = "Classroom";
    public static String CLASSROOM_RELATION_KEY = "classrooms";
    public static String CLASSROOM_TITLE_KEY = "classroomTitle";
    public static String CLASSROOM_SUBJECT_KEY = "classroomSubject";
    public static String CLASSROOM_GRADE_KEY = "classroomGradeLevel";

    public Handler parseClassHandler;

    private String classroomTitle;
    private String classroomSubject;
    private int classroomGradeLevel;

    public ParseClassSectionObject(){

    }

    public ParseClassSectionObject(ParseClassObjectInterface parseClassInterface){
        this.parseClassInterface = parseClassInterface;
    }

    public void createNewClassroom(Classroom classroom){
        ParseObject parseClassroomObject = new ParseObject(CLASSROOM_KEY);
        getClassroomInformation(classroom);
        parseClassroomObject.put(CLASSROOM_TITLE_KEY, classroomTitle);
        parseClassroomObject.put(CLASSROOM_SUBJECT_KEY, classroomSubject);
        parseClassroomObject.put(CLASSROOM_GRADE_KEY, classroomGradeLevel);
        ParseUser currentUser = ParseUser.getCurrentUser();
        parseClassroomObject.put("createdBy", currentUser);
        parseClassroomObject.saveInBackground();
    }


    public Runnable findClassroomOnParse(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
                if(classroom.getClassSectionName() != null) {
                    query.whereEqualTo(CLASSROOM_TITLE_KEY, classroom.getClassSectionName());
                }
                if(classroom.getClassSectionSubject() != null) {
                    query.whereEqualTo(CLASSROOM_SUBJECT_KEY, classroom.getClassSectionSubject());
                }
                if(classroom.getClassSectionGradeLevel() != 0){
                    query.whereEqualTo(CLASSROOM_GRADE_KEY, classroom.getClassSectionGradeLevel());
                }
                try {
                    addClasses(query.find());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
       return runnable;
    }


    public void getClassroomInformation(Classroom classroom){
        classroomTitle = classroom.getClassSectionName();
        classroomSubject = classroom.getClassSectionSubject();
        classroomGradeLevel = classroom.getClassSectionGradeLevel();
    }

    public Runnable updateListOfClassRooms() {
        Runnable runnable = new Runnable() {
            ParseUser currentUser = ParseUser.getCurrentUser();
            String userType = currentUser.getString("userType");
            ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
            @Override
            public void run() {
                if(userType.equalsIgnoreCase("teacher")) {
                    try {
                        addClasses(query.find());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else if(userType.equalsIgnoreCase("student")){
                    ParseRelation relation = parseUserObject.getCurrentUser().getRelation(CLASSROOM_RELATION_KEY);
                    ParseQuery relationQuery = relation.getQuery();
                    try {
                        addClasses(relationQuery.find());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return runnable;
    }

    public void addClasses(List<ParseObject> list){
        for(int i = 0; i < list.size(); i++){
            ParseObject classObject = list.get(i);
            Classroom classRoom = new Classroom(classObject.getString(CLASSROOM_TITLE_KEY),
                    classObject.getString(CLASSROOM_SUBJECT_KEY), classObject.getInt(CLASSROOM_GRADE_KEY));
            myClasses.add(classRoom);
            Log.i("addClassMethod", "success :" + myClasses.get(i).getClassSectionName());
        }
        parseClassInterface.classListReturned(myClasses);
    }

    public List<Classroom> getArrayListOfClassrooms(){
        return myClasses;
    }

    //Method for network call only used within Runnable

    public ParseObject getParseClassroomObject(Classroom classroomQueried) {
        ParseObject classObjectReturned = null;
                ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
                query.whereEqualTo(CLASSROOM_TITLE_KEY, classroomQueried.getClassSectionName());
                query.whereEqualTo(CLASSROOM_SUBJECT_KEY, classroomQueried.getClassSectionSubject());
                query.whereEqualTo(CLASSROOM_GRADE_KEY, classroomQueried.getClassSectionGradeLevel());
                try {
                    classObjectReturned = query.getFirst();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

        return classObjectReturned;
    }

    public void setParseClassroomObject(ParseObject parseObjectFound){
        this.parseClassroomObject = parseObjectFound;
    }

    public ParseObject getQueriedClassroom(){
        return parseClassroomObject;
    }

    public Runnable addStudentToClassRelation(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               ParseObject classObject = getParseClassroomObject(classroom);
                ParseUser currentUser = ParseUser.getCurrentUser();
               ParseRelation<ParseObject> relation = currentUser.getRelation("classrooms");
               relation.add(getQueriedClassroom());
                try {
                    currentUser.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        return runnable;

    }

    public boolean isThereAClassStudentRelation(Classroom classroom){
        Boolean classIsRelated = false;
        ParseRelation relation = ParseUser.getCurrentUser().getRelation(CLASSROOM_RELATION_KEY);
        ParseQuery relationQuery = relation.getQuery();
        try {
          if(relationQuery.count() > 0){
              classIsRelated = true;
          }
        }catch(ParseException e){

        }
       return classIsRelated;
    }


    public void setParseClassInterface(ParseClassObjectInterface classInterface){
        this.parseClassInterface = classInterface;
    }

    public interface ParseClassObjectInterface{
        public void classListReturned(List<Classroom> classroomList);
   //     public void classObjectReturned(Classroom classroom);
    }


}

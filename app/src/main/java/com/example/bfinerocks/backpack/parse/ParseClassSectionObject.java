package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.example.bfinerocks.backpack.models.Classroom;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class ParseClassSectionObject {

    ParseUserObject parseUserObject = new ParseUserObject();
    List<Classroom> myClasses = new ArrayList<Classroom>();

    public static String CLASSROOM_KEY = "Classroom";
    public static String CLASSROOM_RELATION_KEY = "classrooms";
    public static String CLASSROOM_TITLE_KEY = "classroomTitle";
    public static String CLASSROOM_SUBJECT_KEY = "classroomSubject";
    public static String CLASSROOM_GRADE_KEY = "classroomGradeLevel";

    private String classroomTitle;
    private String classroomSubject;
    private int classroomGradeLevel;

    public void addNewClassroom(Classroom classroom){
        ParseObject parseClassroomObject = new ParseObject(CLASSROOM_KEY);
        getClassroomInformation(classroom);
        parseClassroomObject.put(CLASSROOM_TITLE_KEY, classroomTitle);
        parseClassroomObject.put(CLASSROOM_SUBJECT_KEY, classroomSubject);
        parseClassroomObject.put(CLASSROOM_GRADE_KEY, classroomGradeLevel);

        ParseUser currentUser = ParseUser.getCurrentUser();
        parseClassroomObject.put("createdBy", currentUser);

        parseClassroomObject.saveInBackground();

/*        ParseRelation<ParseObject> relations = currentUser.getRelation(CLASSROOM_RELATION_KEY);
        relations.add(parseClassroomObject);
        currentUser.saveInBackground();*/

    }

    public void getClassroomInformation(Classroom classroom){
        classroomTitle = classroom.getClassSectionName();
        classroomSubject = classroom.getClassSectionSubject();
        classroomGradeLevel = classroom.getClassSectionGradeLevel();
    }

    public void updateListOfClassRooms(){

        ParseUser currentUser = parseUserObject.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
        query.whereEqualTo("createdBy", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null) {
                    Log.i("classList", "success");
                    addClasses(list);
                }else{
                    Log.i("classList", e.getMessage());
                }
            }
        });

    }

    public void addClasses(List<ParseObject> list){
      //  this.myClasses = new ArrayList<Classroom>();
        for(int i = 0; i < list.size(); i++){
            ParseObject classObject = list.get(i);
            Classroom classRoom = new Classroom(classObject.getString(CLASSROOM_TITLE_KEY),
                    classObject.getString(CLASSROOM_SUBJECT_KEY), classObject.getInt(CLASSROOM_GRADE_KEY));
            myClasses.add(classRoom);
            Log.i("addClassMethod", "success :" + myClasses.get(i).getClassSectionName());
        }
    }

    public List<Classroom> getArrayListOfClassrooms(){
//        Log.i("arrayLoaded", myClasses.get(0).getClassSectionName());
        return myClasses;
    }


}

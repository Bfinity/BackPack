package com.example.bfinerocks.backpack.parse;

import com.example.bfinerocks.backpack.models.Classroom;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class ParseClassSectionObject {

    ParseUserObject parseUserObject = new ParseUserObject();

    public static String CLASSROOM_KEY = "classroom";
    public static String CLASSROOM_TITLE_KEY = "classroomTitle";
    public static String CLASSROOM_SUBJECT_KEY = "classroomSubject";
    public static String CLASSROOM_GRADE_KEY = "classroomGradeLevel";

    private String classroomTitle;
    private String classroomSubject;
    private int classroomGradeLevel;

    public void addNewClassroom(String user, Classroom classroom){
        ParseObject parseClassroomObject = new ParseObject(CLASSROOM_KEY);
        getClassroomInformation(classroom);
        parseClassroomObject.put(CLASSROOM_TITLE_KEY, classroomTitle);
        parseClassroomObject.put(CLASSROOM_SUBJECT_KEY, classroomSubject);
        parseClassroomObject.put(CLASSROOM_GRADE_KEY, classroomGradeLevel);

        ParseUser currentUser = parseUserObject.getCurrentUser();

        ParseRelation<ParseObject> relations = currentUser.getRelation(CLASSROOM_TITLE_KEY);
        relations.add(parseClassroomObject);


    }

    public void getClassroomInformation(Classroom classroom){
        classroomTitle = classroom.getClassSectionName();
        classroomSubject = classroom.getClassSectionSubject();
        classroomGradeLevel = classroom.getClassSectionGradeLevel();
    }

}

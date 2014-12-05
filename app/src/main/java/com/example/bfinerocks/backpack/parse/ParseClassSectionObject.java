package com.example.bfinerocks.backpack.parse;

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

    ParseUserObject parseUserObject = new ParseUserObject();
    ParseObject parseClassroomObject;
    List<Classroom> myClasses = new ArrayList<Classroom>();

    public static String CLASSROOM_KEY = "Classroom";
    public static String CLASSROOM_RELATION_KEY = "classrooms";
    public static String CLASSROOM_TITLE_KEY = "classroomTitle";
    public static String CLASSROOM_SUBJECT_KEY = "classroomSubject";
    public static String CLASSROOM_GRADE_KEY = "classroomGradeLevel";

    private String classroomTitle;
    private String classroomSubject;
    private int classroomGradeLevel;

    public void createNewClassroom(Classroom classroom){
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


    public void findClassroomOnParse(Classroom classroom) throws ParseException{
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
/*        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    Log.i("queryClassrooms", "Success");
                    addClasses(parseObjects);
                }
                else{
                    Log.i("queryClassrooms", e.getMessage());
                }
            }
        });*/

       addClasses(query.find());
    }


    public void getClassroomInformation(Classroom classroom){
        classroomTitle = classroom.getClassSectionName();
        classroomSubject = classroom.getClassSectionSubject();
        classroomGradeLevel = classroom.getClassSectionGradeLevel();
    }

    public void updateListOfClassRooms() throws ParseException{

        ParseUser currentUser = parseUserObject.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
        if(parseUserObject.getUserType().equalsIgnoreCase("Teacher")) {
            addClasses(query.find());
/*            query.whereEqualTo("createdBy", currentUser);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        Log.i("classList", "success");
                        addClasses(list);
                    } else {
                        Log.i("classList", e.getMessage());
                    }
                }
            });*/
        }
        else if(parseUserObject.getUserType().equalsIgnoreCase("Student")){
            ParseRelation relation = parseUserObject.getCurrentUser().getRelation(CLASSROOM_RELATION_KEY);
            ParseQuery relationQuery = relation.getQuery();
            addClasses(relationQuery.find());
/*            relationQuery.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if(e == null){
                        Log.i("classList", "Success");
                        addClasses(list);
                    }
                    else{
                        Log.i("classList", e.getMessage());
                    }
                }
            });*/
        }

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

    public void queryClassroomByClassName(Classroom classroomQueried) throws ParseException {
        ParseObject queriedClassroom = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASSROOM_KEY);
        query.whereEqualTo(CLASSROOM_TITLE_KEY, classroomQueried.getClassSectionName());
        setParseClassroomObject(query.getFirst());


/*        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Log.i("classQuery", "Success");
                    setParseClassroomObject(parseObject);
                } else {
                    Log.i("classQuery", e.getMessage());
                }
            }
        });*/
    }

    public void setParseClassroomObject(ParseObject parseObjectFound){
        this.parseClassroomObject = parseObjectFound;
    }

    public ParseObject getQueriedClassroom(){
        return parseClassroomObject;
    }

    public void addStudentToClassRelation(ParseUser currentUser, Classroom classroom) throws ParseException{
        queryClassroomByClassName(classroom);
        ParseRelation<ParseObject> relation = currentUser.getRelation("classrooms");
        relation.add(getQueriedClassroom());
        currentUser.save();
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


}

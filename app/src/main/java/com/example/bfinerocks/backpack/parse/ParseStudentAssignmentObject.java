package com.example.bfinerocks.backpack.parse;

import android.os.Looper;
import android.util.Log;

import com.example.bfinerocks.backpack.constants.ParseKeys;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.UserModel;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BFineRocks on 12/2/14.
 */
public class ParseStudentAssignmentObject {

    private ParseStudentAssignmentInterface mParseStudentAssignmentInterface;
    public static final String STUDENT_ASSIGNMENT_OBJECT_KEY = "StudentAssignment";
    public static final String STUDENT_USER = "studentUser";
    public static final String ASSIGNMENT_OBJECT = "assignment";
    public static final String ASSIGNMENT_CLASSROOM_RELATION = "fromClassroom";
    public static final String ASSIGNMENT_REVIEWED_DATE_KEY = "dateReviewed";
    public static final String ASSIGNMENT_STATUS_KEY = "assignmentState";
    public static final String ASSIGNMENT_COMPLETED_DATE = "dateCompleted";
    public static final String ASSIGNMENT_NOTES = "notes";
    private List<ParseObject> listOfStudentAssignmentObjects = null;
    ParseClassSectionObject parseClassroom = null;

    public ParseStudentAssignmentObject(){

    }
    public ParseStudentAssignmentObject(ParseStudentAssignmentInterface parseStudentAssignmentInterface){
        this.mParseStudentAssignmentInterface = parseStudentAssignmentInterface;
    }

    public void addStudentAssignment(ParseObject parseAssignment, String classroomName){
        ParseObject assignmentJoinTable = new ParseObject(STUDENT_ASSIGNMENT_OBJECT_KEY);
        assignmentJoinTable.put(STUDENT_USER, ParseUser.getCurrentUser());
        assignmentJoinTable.put(ASSIGNMENT_OBJECT, parseAssignment);
        assignmentJoinTable.put(ASSIGNMENT_CLASSROOM_RELATION, classroomName);
        try {
            assignmentJoinTable.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Runnable addAssignmentsToStudentAssignments(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseClassSectionObject classSection = new ParseClassSectionObject();
                ParseObject classObject = classSection.getParseClassroomObject(classroom);
                ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseKeys.ASSIGNMENT_KEY);
                query.whereEqualTo(ParseKeys.ASSIGNMENT_CLASSROOM_ASSOCIATION, classObject);
                List<ParseObject> listOfParseAssignments = null;
                try {
                    listOfParseAssignments = query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i < listOfParseAssignments.size(); i++){
                    ParseObject holder = listOfParseAssignments.get(i);
                    if(!assignmentHasBeenAdded(holder)) {         //still on the background thread
                        addStudentAssignment(holder, classroom.getClassSectionName());
                    }
                }
            }
        };
        return runnable;
    }

    public boolean assignmentHasBeenAdded(ParseObject assignment){
        boolean isAdded = false;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(ASSIGNMENT_OBJECT, assignment);
        try {
            if (query.count() > 0) {
                isAdded = true;
            }
        }catch (ParseException e){

        }
        return isAdded;
    }

    public Runnable updateStudentAssignment(final Assignment assignment) {
/*        ParseObject assignmentToUpdate = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(ASSIGNMENT_OBJECT, parseAssignment);
        try {
            assignmentToUpdate = query.getFirst();
        } catch (ParseException e) {
            Log.i("updateAssignment", e.getMessage());
        }
        if (assignmentToUpdate != null) {
            assignmentToUpdate.put(ASSIGNMENT_STATUS_KEY, assignment.getAssignmentCompletionState());
            if (assignment.getAssignmentCompletionState()) {
                Date today = new Date();
                assignmentToUpdate.put(ASSIGNMENT_COMPLETED_DATE, today);
            }
            if (!assignment.getAssignmentNotes().equalsIgnoreCase(null)) {
                assignmentToUpdate.put(ASSIGNMENT_NOTES, assignment.getAssignmentNotes());
            }
            assignmentToUpdate.saveInBackground();
        }*/

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
                query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
                query.whereEqualTo(ASSIGNMENT_OBJECT, assignment.getAssignmentUniqueID());
                ParseObject assignmentToUpdate = null;
                try {
                    assignmentToUpdate = query.getFirst();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (assignmentToUpdate != null) {
                    assignmentToUpdate.put(ASSIGNMENT_STATUS_KEY, assignment.getAssignmentCompletionState());
                    if (assignment.getAssignmentCompletionState()) {
                        Date today = new Date();
                        assignmentToUpdate.put(ASSIGNMENT_COMPLETED_DATE, today);
                    }
                    if (!assignment.getAssignmentNotes().equalsIgnoreCase(null)) {
                        assignmentToUpdate.put(ASSIGNMENT_NOTES, assignment.getAssignmentNotes());
                    }
                    try {
                        assignmentToUpdate.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return runnable;
    }

    public Assignment queryStudentAssignmentObject(Assignment assignment){
        ParseAssignmentObject parseAssignmentObject = new ParseAssignmentObject();
        ParseObject parseAssignment = null;
        Assignment assignmentFound = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        try {
            parseAssignment = parseAssignmentObject.queryAssignmentBasedOnName(assignment);
            query.whereEqualTo(ASSIGNMENT_OBJECT, parseAssignment);
            ParseObject temp = query.getFirst();
            assignmentFound = parseAssignmentObject.convertParseAssignmentObjectToAssignmentModel(temp.getParseObject(ASSIGNMENT_OBJECT).fetch());
            assignmentFound = updateAssignmentWithDetailsFromStudent(temp, assignmentFound);
        }catch(ParseException e){

        }

        return assignmentFound;
    }

    public Assignment updateAssignmentWithDetailsFromStudent(ParseObject studentAssignment, Assignment assignment){
        try {
            ParseObject parseObject = studentAssignment.getParseUser(STUDENT_USER).fetch();
            assignment.setStudentName(parseObject.getString("fullName"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assignment.isAssignmentCompleted(studentAssignment.getBoolean(ASSIGNMENT_STATUS_KEY));
        assignment.setAssignmentNotes(studentAssignment.getString(ASSIGNMENT_NOTES));
        return assignment;
    }

    public Runnable createListOfStudentAssignmentObjectsForDisplay(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
                query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
                query.whereEqualTo(ASSIGNMENT_CLASSROOM_RELATION, classroom.getClassSectionName());
                try {
                    setListOfStudentAssignmentObjects(query.find());
                }catch (ParseException e){
                    Log.e("ParseException", e.getMessage());
                }
                setListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
            }
        };

        return runnable;
    }

    public Runnable createListOfStudentAssignmentObjectsForDisplay(final Classroom classroom, final UserModel student){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseUserObject object = new ParseUserObject();
                ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
                query.whereEqualTo(STUDENT_USER, object.getUserByUID(student));
                query.whereEqualTo(ASSIGNMENT_CLASSROOM_RELATION, classroom.getClassSectionName());
                try {
                    setListOfStudentAssignmentObjects(query.find());
                }catch (ParseException e){
                    Log.e("ParseException", e.getMessage());
                }
                setListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
            }
        };

        return runnable;
    }

    public Runnable createListOfStudentAssignmentObjectsForDisplay(final UserModel studentUser){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
                query.whereEqualTo(STUDENT_USER, studentUser.getUserObjectID());
                try {
                    setListOfStudentAssignmentObjects(query.find());
                }catch (ParseException e){
                    Log.e("ParseException", e.getMessage());
                }
                setListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
            }
        };

        return runnable;
    }

    public Runnable createListOfStudentResponses(final Assignment assignment) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
                ParseQuery<ParseObject> assignmentQ = ParseQuery.getQuery("Assignments");
                try{
                    assignmentQ.whereEqualTo("assignmentTitle", assignment.getAssignmentTitle());
                    ParseObject assignmentObject = assignmentQ.getFirst();
                    query.whereEqualTo(ASSIGNMENT_OBJECT, assignmentObject);
                    setListOfStudentAssignmentObjectsForDisplay(query.find());
                }catch (ParseException e){

                }
            }
        };
        return runnable;
    }

    public void setListOfStudentAssignmentObjectsForDisplay(List<ParseObject> listOfParseAssignments){
         ArrayList<Assignment> listOfAssignments = new ArrayList<Assignment>();
         ParseAssignmentObject parseAssignmentObject = new ParseAssignmentObject();
        for(int i = 0; i < listOfParseAssignments.size(); i ++){
            ParseObject parseObject = listOfParseAssignments.get(i);
                    try {
                        ParseObject assignmentObject = parseObject.getParseObject(ASSIGNMENT_OBJECT).fetch();
                        Assignment assignment = parseAssignmentObject.convertParseAssignmentObjectToAssignmentModel(assignmentObject);
                        assignment = updateAssignmentWithDetailsFromStudent(parseObject, assignment);
                        if(Looper.getMainLooper() == Looper.myLooper()){
                            Log.i("Thread", "On the main thread");
                        }
                        listOfAssignments.add(assignment);
                    }catch (ParseException e){
                        Log.e("Parse", e.getMessage());
                    }
        }
        mParseStudentAssignmentInterface.hasListOfAssignmentsUpdated(listOfAssignments);
    }



    public void setListOfStudentAssignmentObjects(List<ParseObject> listOfStudentAssignments){
        this.listOfStudentAssignmentObjects = listOfStudentAssignments;
    }


    public List<ParseObject> getListOfStudentAssignmentObjects(){
        return listOfStudentAssignmentObjects;
    }



    public interface ParseStudentAssignmentInterface{
        public void hasListOfAssignmentsUpdated(List<Assignment> listOfUpdatedAssignments);
    }





}

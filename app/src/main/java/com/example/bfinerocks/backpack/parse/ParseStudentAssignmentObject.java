package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
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

    public void addStudentAssignment(ParseObject parseAssignment, String classroomName){
        ParseObject assignmentJoinTable = new ParseObject(STUDENT_ASSIGNMENT_OBJECT_KEY);
        assignmentJoinTable.put(STUDENT_USER, ParseUser.getCurrentUser());
        assignmentJoinTable.put(ASSIGNMENT_OBJECT, parseAssignment);
        assignmentJoinTable.put(ASSIGNMENT_CLASSROOM_RELATION, classroomName);
/*        assignmentJoinTable.put(ASSIGNMENT_STATUS_KEY, false);
        if(assignment.getAssignmentCompletionState()){
            Date today = new Date();
            assignmentJoinTable.put(ASSIGNMENT_COMPLETED_DATE, today );
        }
        if(!assignment.getAssignmentNotes().isEmpty()) {
            assignmentJoinTable.put(ASSIGNMENT_NOTES, assignment.getAssignmentNotes());
        }*/
        assignmentJoinTable.saveInBackground();
    }

    public void addAssignmentsToStudentAssignments(Classroom classroom){

        ParseAssignmentObject parseAssignment = new ParseAssignmentObject();
        parseAssignment.createListOfAssignmentsAssociatedWithClassroom(classroom);
        List<ParseObject> listOfParseAssignments = parseAssignment.getListOfParseAssignmentObjects();
        for(int i = 0; i < listOfParseAssignments.size(); i++){
            ParseObject holder = listOfParseAssignments.get(i);
            if(!assignmentHasBeenAdded(holder)) {
                addStudentAssignment(holder, classroom.getClassSectionName());
            }
        }
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

    public void updateStudentAssignment(ParseObject parseAssignment, Assignment assignment) {
        ParseObject assignmentToUpdate = null;
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
        }
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
        assignment.isAssignmentCompleted(studentAssignment.getBoolean(ASSIGNMENT_STATUS_KEY));
        assignment.setAssignmentNotes(studentAssignment.getString(ASSIGNMENT_NOTES));
        return assignment;
    }

    public ArrayList<Assignment> createListOfStudentAssignmentObjectsForDisplay(Classroom classroom){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(ASSIGNMENT_CLASSROOM_RELATION, classroom.getClassSectionName());
        try {
            setListOfStudentAssignmentObjects(query.find());
        }catch (ParseException e){
            Log.e("ParseException", e.getMessage());
        }

        return getListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
    }

    public ArrayList<Assignment> createListOfStudentAssignmentObjectsForDisplay(ParseUser studentUser){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, studentUser);
        try{
            setListOfStudentAssignmentObjects(query.find());
        }catch (ParseException e){
            Log.e("ParseException", e.getMessage());
        }
        return getListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
    }

    public ArrayList<Assignment> createListOfStudentResponses(Assignment assignment){
        ParseAssignmentObject parseAssignmentObject = new ParseAssignmentObject();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        try{
            ParseObject assignmentObject = parseAssignmentObject.queryAssignmentBasedOnName(assignment);
            query.whereEqualTo(ASSIGNMENT_OBJECT, assignmentObject);
            setListOfStudentAssignmentObjects(query.find());
        }catch (ParseException e){

        }
      return  getListOfStudentAssignmentObjectsForDisplay(getListOfStudentAssignmentObjects());
    }

    public void createListOfStudentAssignmentObjectsForDisplay(Classroom classroom, ParseUser studentUser){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, studentUser);
        query.whereEqualTo(ASSIGNMENT_CLASSROOM_RELATION, classroom.getClassSectionName());
        try {
            setListOfStudentAssignmentObjects(query.find());
        }catch (ParseException e){
            Log.e("ParseException", e.getMessage());
        }

    }

    public ArrayList<Assignment> getListOfStudentAssignmentObjectsForDisplay(List<ParseObject> listOfParseAssignments){
        ArrayList<Assignment> listOfAssignments = new ArrayList<Assignment>();
        ParseAssignmentObject parseAssignmentObject = new ParseAssignmentObject();
        Assignment assignment = null;
        for(int i = 0; i < listOfParseAssignments.size(); i ++){
            ParseObject parseObject = listOfParseAssignments.get(i);
            try {
                assignment = parseAssignmentObject.convertParseAssignmentObjectToAssignmentModel(parseObject.getParseObject(ASSIGNMENT_OBJECT).fetch());
            }catch (ParseException e){
                Log.e("Parse", e.getMessage());
            }
            assignment = updateAssignmentWithDetailsFromStudent(parseObject, assignment);
            listOfAssignments.add(assignment);
        }
        return listOfAssignments;
    }

    public void setListOfStudentAssignmentObjects(List<ParseObject> listOfStudentAssignments){
        this.listOfStudentAssignmentObjects = listOfStudentAssignments;
    }


    public List<ParseObject> getListOfStudentAssignmentObjects(){
        return listOfStudentAssignmentObjects;
    }





}

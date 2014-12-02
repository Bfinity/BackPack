package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.example.bfinerocks.backpack.models.Assignment;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by BFineRocks on 12/2/14.
 */
public class ParseStudentAssignmentObject {

    public static final String STUDENT_ASSIGNMENT_OBJECT_KEY = "StudentAssignment";
    public static final String STUDENT_USER = "studentUser";
    public static final String ASSIGNMENT_OBJECT = "assignment";
    public static final String ASSIGNMENT_REVIEWED_DATE_KEY = "dateReviewed";
    public static final String ASSIGNMENT_STATUS_KEY = "assignmentState";
    public static final String ASSIGNMENT_COMPLETED_DATE = "dateCompleted";
    public static final String ASSIGNMENT_NOTES = "notes";

    public void addStudentAssignment(ParseObject parseAssignment, Assignment assignment){
        ParseObject assignmentJoinTable = new ParseObject(STUDENT_ASSIGNMENT_OBJECT_KEY);
        assignmentJoinTable.put(STUDENT_USER, ParseUser.getCurrentUser());
        assignmentJoinTable.put(ASSIGNMENT_OBJECT, parseAssignment);
        assignmentJoinTable.put(ASSIGNMENT_STATUS_KEY, assignment.getAssignmentCompletionState());
        if(assignment.getAssignmentCompletionState()){
            Date today = new Date();
            assignmentJoinTable.put(ASSIGNMENT_COMPLETED_DATE, today );
        }
        if(!assignment.getAssignmentNotes().isEmpty()) {
            assignmentJoinTable.put(ASSIGNMENT_NOTES, assignment.getAssignmentNotes());
        }
        assignmentJoinTable.saveInBackground();
    }

    public void updateStudentAssignment(ParseObject parseAssignment, Assignment assignment){
        ParseObject assignmentToUpdate = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(STUDENT_ASSIGNMENT_OBJECT_KEY);
        query.whereEqualTo(STUDENT_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(ASSIGNMENT_OBJECT, parseAssignment);
        try {
            assignmentToUpdate = query.getFirst();
        }catch (ParseException e){
            Log.i("updateAssignment", e.getMessage());
        }
        if(assignmentToUpdate != null) {
            assignmentToUpdate.put(ASSIGNMENT_STATUS_KEY, assignment.getAssignmentCompletionState());
            if (assignment.getAssignmentCompletionState()) {
                Date today = new Date();
                assignmentToUpdate.put(ASSIGNMENT_COMPLETED_DATE, today);
            }
            if (!assignment.getAssignmentNotes().isEmpty()) {
                assignmentToUpdate.put(ASSIGNMENT_NOTES, assignment.getAssignmentNotes());
            }
            assignmentToUpdate.saveInBackground();
        }

    }



}

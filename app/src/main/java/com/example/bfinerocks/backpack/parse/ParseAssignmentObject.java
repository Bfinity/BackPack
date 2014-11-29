package com.example.bfinerocks.backpack.parse;

import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.parse.ParseObject;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class ParseAssignmentObject {
    public static String ASSIGNMENT_KEY = "Assignments";
    public static String ASSIGNMENT_TITLE_KEY = "assignmentTitle";
    public static String ASSIGNMENT_ASSIGN_DATE_KEY = "assignedDate";
    public static String ASSIGNMENT_DUE_DATE_KEY = "dueDate";
    public static String ASSIGNMENT_DIRECTIONS_KEY = "assignmentDetails";
    public static String ASSIGNMENT_COMPLETION_STATE_KEY = "completionState";
    public static String ASSIGNMENT_CLASSROOM_ASSOCIATION = "classroom";
    private Assignment assignmentToAdd;
    private Classroom classToAssociate;
    private ParseClassSectionObject parseClassObject = new ParseClassSectionObject();


    public void addNewAssignment(Assignment assignmentToAdd, Classroom classToAssociate){
        this.assignmentToAdd = assignmentToAdd;
        ParseObject parseAssignment = new ParseObject(ASSIGNMENT_KEY);
        parseAssignment.put(ASSIGNMENT_TITLE_KEY, assignmentToAdd.getAssignmentTitle());
        parseAssignment.put(ASSIGNMENT_ASSIGN_DATE_KEY, assignmentToAdd.getAssignmentAssignedDate());
        parseAssignment.put(ASSIGNMENT_DUE_DATE_KEY, assignmentToAdd.getAssignmentDueDate());
        parseAssignment.put(ASSIGNMENT_DIRECTIONS_KEY, assignmentToAdd.getAssignmentDescription());
        parseAssignment.put(ASSIGNMENT_COMPLETION_STATE_KEY, assignmentToAdd.getAssignmentCompletionState());

        this.classToAssociate = classToAssociate;
        try {
            parseClassObject.getQueriedClassroomByClassName(classToAssociate);
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        parseAssignment.put(ASSIGNMENT_CLASSROOM_ASSOCIATION, parseClassObject.getQueriedClassroom());
        parseAssignment.saveInBackground();

    }



}

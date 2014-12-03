package com.example.bfinerocks.backpack.parse;

import android.util.Log;

import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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
    private List<Assignment> listOfAssignments = null;
    private List<ParseObject> listOfParseAssignmentObjects;
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
            parseClassObject.queryClassroomByClassName(classToAssociate);
        }
        catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        parseAssignment.put(ASSIGNMENT_CLASSROOM_ASSOCIATION, parseClassObject.getQueriedClassroom());
        parseAssignment.saveInBackground();

    }

    public void createListOfAssignmentsAssociatedWithClassroom(Classroom classroom){
     this.classToAssociate = classroom;
       try{
           parseClassObject.queryClassroomByClassName(classroom);
       }catch (ParseException e){
           e.printStackTrace();
       }
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ASSIGNMENT_KEY);
        query.whereEqualTo(ASSIGNMENT_CLASSROOM_ASSOCIATION, parseClassObject.getQueriedClassroom());
        try{
            setListOfParseAssignmentObjects(query.find());
            setListOfAssignments(query.find());
        }catch(ParseException e){
            Log.e("ParseAssignmentParseExeption", e.getMessage());
        }
/*        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    Log.i("assignmentList", "Success");
                    setListOfAssignments(parseObjects);
                    setListOfParseAssignmentObjects(parseObjects);
                }
                else{
                    Log.i("assignmentList", e.getMessage());
                }
            }
        });*/

    }

    public Assignment convertParseAssignmentObjectToAssignmentModel(ParseObject assignmentObject){
        Assignment convertedAssignment = new Assignment(assignmentObject.getString(ASSIGNMENT_TITLE_KEY),
                assignmentObject.getString(ASSIGNMENT_ASSIGN_DATE_KEY), assignmentObject.getString(ASSIGNMENT_DUE_DATE_KEY),
                assignmentObject.getString(ASSIGNMENT_DIRECTIONS_KEY));
        return convertedAssignment;
    }

    public void setListOfParseAssignmentObjects(List<ParseObject> listOfParseAssignmentObjects){
        this.listOfParseAssignmentObjects = listOfParseAssignmentObjects;
    }

    public List<ParseObject> getListOfParseAssignmentObjects(){
        return listOfParseAssignmentObjects;
    }

    public void setListOfAssignments(List<ParseObject> parseObjectsFound){
        listOfAssignments = new ArrayList<Assignment>();
        for(int i = 0; i < parseObjectsFound.size(); i++){
            ParseObject assignmentObject = parseObjectsFound.get(i);
            Assignment assignmentToAddToList = convertParseAssignmentObjectToAssignmentModel(assignmentObject);
            listOfAssignments.add(assignmentToAddToList);
        }
    }

    public List<Assignment> getListOfAssignments(){
        return listOfAssignments;
    }

    public void updateAssignment(Assignment assignment) throws ParseException{
        ParseObject assignmentToUpdate = queryAssignmentBasedOnName(assignment);
        assignmentToUpdate.put(ASSIGNMENT_COMPLETION_STATE_KEY, assignment.getAssignmentCompletionState());
        assignmentToUpdate.saveInBackground();
    }

    public ParseObject queryAssignmentBasedOnName(Assignment assignmentToFind) throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ASSIGNMENT_KEY);
        query.whereEqualTo(ASSIGNMENT_DIRECTIONS_KEY, assignmentToFind.getAssignmentDescription());
        return query.getFirst();
    }




}

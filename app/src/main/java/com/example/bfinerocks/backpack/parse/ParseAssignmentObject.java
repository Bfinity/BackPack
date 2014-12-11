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
    public static String ASSIGNMENT_CLASSROOM_ASSOCIATION = "classroom";
    private List<Assignment> listOfAssignments = null;
    private List<ParseObject> listOfParseAssignmentObjects;
    private ParseClassSectionObject parseClassObject = new ParseClassSectionObject();
    private ParseAssignmentInterface mParseAssignmentInterface;

    public ParseAssignmentObject() {

    }

    public ParseAssignmentObject(ParseAssignmentInterface parseAssignmentInterface) {
        mParseAssignmentInterface = parseAssignmentInterface;
    }

    public Runnable createNewAssignmentToPost(final Assignment assignmentToAdd, final Classroom classToAssociate){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseObject parseAssignment = new ParseObject(ASSIGNMENT_KEY);
                parseAssignment.put(ASSIGNMENT_TITLE_KEY, assignmentToAdd.getAssignmentTitle());
                parseAssignment.put(ASSIGNMENT_ASSIGN_DATE_KEY, assignmentToAdd.getAssignmentAssignedDate());
                parseAssignment.put(ASSIGNMENT_DUE_DATE_KEY, assignmentToAdd.getAssignmentDueDate());
                parseAssignment.put(ASSIGNMENT_DIRECTIONS_KEY, assignmentToAdd.getAssignmentDescription());
                ParseObject classObject = parseClassObject.getParseClassroomObject(classToAssociate);
                parseAssignment.put(ASSIGNMENT_CLASSROOM_ASSOCIATION, classObject);
                try {
                    parseAssignment.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        return runnable;

    }

    public Runnable createListOfAssignmentsAssociatedWithClassroom(final Classroom classroom){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ParseClassSectionObject classSection = new ParseClassSectionObject();
                ParseObject classObject = classSection.getParseClassroomObject(classroom);
                ParseQuery<ParseObject> query = ParseQuery.getQuery(ASSIGNMENT_KEY);
                query.whereEqualTo(ASSIGNMENT_CLASSROOM_ASSOCIATION, classObject);
                try{
                    setListOfParseAssignmentObjects(query.find());
                    setListOfAssignments(query.find());
                }catch(ParseException e){
                    Log.e("ParseAssignmentParseExeption", e.getMessage());
                }
            }
        };
        return runnable;
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
        if (mParseAssignmentInterface != null) {
            mParseAssignmentInterface.hasListUpdated(listOfAssignments);
        }
    }

    public List<Assignment> getListOfAssignments(){
        return listOfAssignments;
    }


    public ParseObject queryAssignmentBasedOnName(Assignment assignmentToFind) throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ASSIGNMENT_KEY);
        query.whereEqualTo(ASSIGNMENT_DIRECTIONS_KEY, assignmentToFind.getAssignmentDescription());
        return query.getFirst();
    }

    public interface ParseAssignmentInterface{
        public void hasListUpdated(List<Assignment> listOfAssignments);
    }




}

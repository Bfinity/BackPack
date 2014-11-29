package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 */
public class Assignment {
    private String assignmentTitle;
    private String assignmentAssignedDate;
    private String assignmentDueDate;
    private String assignmentDescription; // Possibly use instead of assignment task objects
    private Boolean assignmentCompletionState; // Possibly use instead of assignment task objects
    private ArrayList<AssignmentTask> assignmentTasks;

    public Assignment(String assignmentTitle, String assignmentAssignedDate, String assignmentDueDate, String assignmentDescription){
        this.assignmentTitle = assignmentTitle;
        this.assignmentAssignedDate = assignmentAssignedDate;
        this.assignmentDueDate = assignmentDueDate;
        this.assignmentDescription = assignmentDescription;
        this.assignmentCompletionState = false;
    }

    public String getAssignmentTitle(){
        return assignmentTitle;
    }

    public String getAssignmentAssignedDate() {
        return assignmentAssignedDate;
    }

    public void setAssignmentAssignedDate(String assignmentAssignedDate) {
        this.assignmentAssignedDate = assignmentAssignedDate;
    }

    public String getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public void setAssignmentDueDate(String assignmentDueDate) {
        this.assignmentDueDate = assignmentDueDate;
    }

    public void addAssignmentTasksToArray(AssignmentTask assignmentTask){
        assignmentTasks.add(assignmentTask);
    }

    public AssignmentTask getAssignmentTasksFromArray(int requestedTask){
        return assignmentTasks.get(requestedTask);
    }

    //This method will add a description to the assignment if assignment task object isn't used.
    public void addAssignmentDescription(String assignmentDescription){
        this.assignmentDescription = assignmentDescription;
    }

    //A holder if the task object isn't used
    public String getAssignmentDescription(){
        return assignmentDescription;
    }

    //This method will assign assignment completion state done true or false if the task object isn't used.
    public void isAssignmentCompleted(Boolean assignmentCompletionState){
        this.assignmentCompletionState = assignmentCompletionState;
    }

    //A holder if the task object isn't used.
    public Boolean getAssignmentCompletionState(){
        return assignmentCompletionState;
    }

}

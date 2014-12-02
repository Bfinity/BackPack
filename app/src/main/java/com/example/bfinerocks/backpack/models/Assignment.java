package com.example.bfinerocks.backpack.models;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 */
public class Assignment implements android.os.Parcelable {
    private String assignmentTitle;
    private String assignmentAssignedDate;
    private String assignmentDueDate;
    private String assignmentDescription; // Possibly use instead of assignment task objects
    private Boolean assignmentCompletionState; // Possibly use instead of assignment task objects
    private String assignmentNotes;
    private ArrayList<AssignmentTask> assignmentTasks;

    public Assignment(String assignmentTitle, String assignmentAssignedDate, String assignmentDueDate, String assignmentDescription){
        this.assignmentTitle = assignmentTitle;
        this.assignmentAssignedDate = assignmentAssignedDate;
        this.assignmentDueDate = assignmentDueDate;
        this.assignmentDescription = assignmentDescription;
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

    public void setAssignmentNotes(String assignmentNotes){
        this.assignmentNotes = assignmentNotes;
    }

    public String getAssignmentNotes(){
        return assignmentNotes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.assignmentTitle);
        dest.writeString(this.assignmentAssignedDate);
        dest.writeString(this.assignmentDueDate);
        dest.writeString(this.assignmentDescription);
        dest.writeValue(this.assignmentCompletionState);
        dest.writeString(this.assignmentNotes);
        dest.writeSerializable(this.assignmentTasks);
    }

    private Assignment(Parcel in) {
        this.assignmentTitle = in.readString();
        this.assignmentAssignedDate = in.readString();
        this.assignmentDueDate = in.readString();
        this.assignmentDescription = in.readString();
        this.assignmentCompletionState = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.assignmentNotes = in.readString();
        this.assignmentTasks = (ArrayList<AssignmentTask>) in.readSerializable();
    }

    public static final Creator<Assignment> CREATOR = new Creator<Assignment>() {
        public Assignment createFromParcel(Parcel source) {
            return new Assignment(source);
        }

        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };
}

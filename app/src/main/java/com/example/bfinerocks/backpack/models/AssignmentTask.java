package com.example.bfinerocks.backpack.models;

/**
 * Created by BFineRocks on 11/14/14.
 *
 * This class creates an Assignment Task. Might not use if this information is added to the
 * Assignment Object instead.
 */
public class AssignmentTask {
    private int taskNumber;
    private String taskDescription;
    private Boolean isTaskComplete;

    public AssignmentTask(int taskNumber, String taskDescription){
        this.taskNumber = taskNumber;
        this.taskDescription = taskDescription;
        isTaskComplete = false;
    }

    public int getTaskNumber(){
        return taskNumber;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public void setIsTaskComplete(Boolean isTaskComplete){
        this.isTaskComplete = isTaskComplete;
    }

    public Boolean getIsTaskComplete(){
        return isTaskComplete;
    }

}

package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 */
public class LessonPlan {
    private String lessonPlanTitle;
    ArrayList<Assignment> lessonAssignments;

    public LessonPlan(String lessonPlanTitle){
        this.lessonPlanTitle = lessonPlanTitle;
    }

    public String getLessonPlanTitle(){
        return lessonPlanTitle;
    }

    public void addNewAssignmentToArray(Assignment assignment){
     lessonAssignments.add(assignment);
    }

    public Assignment getAssignmentFromArray(String assignmentTitle){
        Assignment assignment = null;
        for(int i = 0; i < lessonAssignments.size(); i++){
            if(lessonAssignments.get(i).getAssignmentTitle().equals(assignmentTitle)){
                assignment = lessonAssignments.get(i);
            }
        }
        return assignment;
    }
}

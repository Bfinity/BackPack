package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/20/14.
 */
public class ClassSection {
    private String classSectionName;
    private ArrayList<LessonPlan> sectionLessonPlans;

    public ClassSection(String classSectionName){
        this.classSectionName = classSectionName;
    }

    public String getClassSectionName(){
        return classSectionName;
    }

    public void addNewLessonPlanToArrayList(LessonPlan lessonPlan){
        sectionLessonPlans.add(lessonPlan);
    }

    public LessonPlan getLessonPlanFromArrayList(String lessonPlanTitle){
       LessonPlan lessonPlan = null;
        for(int i = 0; i < sectionLessonPlans.size(); i++){
            if(sectionLessonPlans.get(i).getLessonPlanTitle().equals(lessonPlanTitle)){
                lessonPlan = sectionLessonPlans.get(i);
            }
        }
        return lessonPlan;
    }
}

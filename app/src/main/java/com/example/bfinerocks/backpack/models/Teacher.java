package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 *
 *
 */
public class Teacher  {
    private String userName;
    private ArrayList<ClassSection> myClassSections;

    public Teacher(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void addNewClassRoom(ClassSection classSection){
        myClassSections.add(classSection);
    }

    public ClassSection getClassSectionFromArray(String classSectionTitle){
        ClassSection classSection = null;
        for(int i = 0; i < myClassSections.size(); i++){
            if(myClassSections.get(i).getClassSectionName().equals(classSectionTitle)){
                classSection = myClassSections.get(i);
            }

        }
        return classSection;
    }

}

package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 *
 *
 */
public class Teacher  {
    private String userName;
    private ArrayList<Classroom> mMyClassrooms;

    public Teacher(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void addNewClassRoom(Classroom classroom){
        mMyClassrooms.add(classroom);
    }

    public Classroom getClassSectionFromArray(String classSectionTitle){
        Classroom classroom = null;
        for(int i = 0; i < mMyClassrooms.size(); i++){
            if(mMyClassrooms.get(i).getClassSectionName().equals(classSectionTitle)){
                classroom = mMyClassrooms.get(i);
            }

        }
        return classroom;
    }

}

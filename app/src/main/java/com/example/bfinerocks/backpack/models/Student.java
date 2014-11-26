package com.example.bfinerocks.backpack.models;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 *
 * Might not need this class at all
 */
public class Student {
    private String userName;
    private ArrayList<Classroom> myClassList;

    public Student(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void addNewClassSection(Classroom classroom){
        myClassList.add(classroom);
    }

    public Classroom getClassFromArrayList(String classTitle){
        Classroom myClass = null;
        for(int i = 0; i < myClassList.size(); i++){
            if(myClassList.get(i).getClassSectionName().equals(classTitle)){
                myClass = myClassList.get(i);
            }
        }
        return myClass;
    }
}

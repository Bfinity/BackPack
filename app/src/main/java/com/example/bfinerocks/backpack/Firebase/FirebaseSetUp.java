package com.example.bfinerocks.backpack.Firebase;

import com.firebase.client.Firebase;

import java.util.Map;


/**
 * Created by BFineRocks on 11/16/14.
 */
public class FirebaseSetUp {

    int classroomNumber = 0;

    public FirebaseSetUp(){

    }

    public void addTeacherToFirebase(Map<String, String> lessonPlan){
        Firebase teacherRef = new Firebase("https://dazzling-heat-5107.firebaseio.com/classroom/one");
        teacherRef.setValue(lessonPlan);

    }
}

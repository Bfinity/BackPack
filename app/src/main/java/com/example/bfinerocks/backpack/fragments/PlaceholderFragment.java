package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bfinerocks.backpack.Firebase.FirebaseSetUp;
import com.example.bfinerocks.backpack.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BFineRocks on 11/14/14.
 */
public class PlaceholderFragment extends Fragment {
    EditText userName;
    EditText lessonPlanTitle;
    EditText task1;
    EditText task2;
    EditText task3;
    Button addButton;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        userName = (EditText) rootView.findViewById(R.id.user_name_txt);
        lessonPlanTitle = (EditText) rootView.findViewById(R.id.lesson_plan_title);
        task1 = (EditText) rootView.findViewById(R.id.task_txt1);
        task2 = (EditText) rootView.findViewById(R.id.task_txt2);
        task3 = (EditText) rootView.findViewById(R.id.task_txt3);
        addButton = (Button) rootView.findViewById(R.id.start_button);

        Map<String, String> lessonPlan = new HashMap<String, String>();
        lessonPlan.put("user", userName.getText().toString());
        lessonPlan.put("title", lessonPlanTitle.getText().toString());
        lessonPlan.put("task1", task1.getText().toString());
        lessonPlan.put("task2", task2.getText().toString());
        lessonPlan.put("task3", task3.getText().toString());

        FirebaseSetUp fb = new FirebaseSetUp();
        fb.addTeacherToFirebase(lessonPlan);

        return rootView;
    }
}

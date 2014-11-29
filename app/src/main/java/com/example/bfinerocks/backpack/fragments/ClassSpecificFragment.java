package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Classroom;

/**
 * Created by BFineRocks on 11/28/14.
 */
public class ClassSpecificFragment extends Fragment {
    TextView classTitle;
    TextView classSubject;
    TextView classGradeLevel;
    ListView assignmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom_specific, container, false);

        Classroom classRoom = getArguments().getParcelable("class");
        classTitle = (TextView) rootView.findViewById(R.id.class_specific_title);
        classSubject = (TextView) rootView.findViewById(R.id.class_specific_subject);
        classGradeLevel = (TextView) rootView.findViewById(R.id.class_specific_grade);
        classTitle.setText(classRoom.getClassSectionName());
        classSubject.setText(classRoom.getClassSectionSubject());
        String classGrade = String.valueOf(classRoom.getClassSectionGradeLevel());
        classGradeLevel.setText(classGrade);

        return rootView;
    }
}

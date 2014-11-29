package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.AssignmentListViewAdapter;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/28/14.
 */
public class ClassSpecificFragment extends Fragment {
    TextView classTitle;
    TextView classSubject;
    TextView classGradeLevel;
    ListView assignmentList;
    TextView addAssignment;
    Classroom classroomDetail;
    AssignmentListViewAdapter assignmentListViewAdapter;
    ParseAssignmentObject parseAssignmentObject;
    List<Assignment> listOfAssignments;

    @Override
    public void onResume() {
        super.onResume();
        parseAssignmentObject = new ParseAssignmentObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom_specific, container, false);
        Classroom classRoom = getArguments().getParcelable("class");
        classroomDetail = classRoom;
        assignmentList = (ListView) rootView.findViewById(R.id.assignment_list_view);
        classTitle = (TextView) rootView.findViewById(R.id.class_specific_title);
        classSubject = (TextView) rootView.findViewById(R.id.class_specific_subject);
        classGradeLevel = (TextView) rootView.findViewById(R.id.class_specific_grade);
        classTitle.setText(classRoom.getClassSectionName());
        classSubject.setText(classRoom.getClassSectionSubject());
        String classGrade = String.valueOf(classRoom.getClassSectionGradeLevel());
        classGradeLevel.setText(classGrade);
        addAssignment = (TextView) rootView.findViewById(R.id.add_assignment);

        addAssignment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAssignment createNewAssignment = new CreateNewAssignment();
                createNewAssignment.setArguments(getArguments());
                getFragmentManager().beginTransaction().replace(R.id.container, createNewAssignment)
                        .addToBackStack("createAssignment").commit();

            }
        });

        return rootView;
    }

    public void updateAssignmentListView(){
        listOfAssignments = new ArrayList<Assignment>();
        parseAssignmentObject

    }
}

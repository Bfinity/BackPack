package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.AssignmentListViewAdapter;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;

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
    Button addToMyClasses;
    AssignmentListViewAdapter assignmentListViewAdapter;
    ParseAssignmentObject parseAssignmentObject;
    List<Assignment> listOfAssignments;
    ParseUserObject parseUserObject;
    ParseClassSectionObject parseClassSection;

    @Override
    public void onResume() {
        super.onResume();
        parseAssignmentObject = new ParseAssignmentObject();
        parseUserObject = new ParseUserObject();
        parseAssignmentObject.createListOfAssignmentsAssociatedWithClassroom(classroomDetail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom_specific, container, false);
        Classroom classRoom = getArguments().getParcelable("class");
        classroomDetail = classRoom;
        listOfAssignments = new ArrayList<Assignment>();
        assignmentList = (ListView) rootView.findViewById(R.id.assignment_list_view);
        classTitle = (TextView) rootView.findViewById(R.id.class_specific_title);
        classTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAssignmentListView();
            }
        });
        classSubject = (TextView) rootView.findViewById(R.id.class_specific_subject);
        classGradeLevel = (TextView) rootView.findViewById(R.id.class_specific_grade);
        classTitle.setText(classRoom.getClassSectionName());
        classSubject.setText(classRoom.getClassSectionSubject());
        String classGrade = String.valueOf(classRoom.getClassSectionGradeLevel());
        classGradeLevel.setText(classGrade);
        addToMyClasses = (Button) rootView.findViewById(R.id.btn_add_class);
        addToMyClasses.setVisibility(View.GONE);
        updateViewForStudent();

        assignmentListViewAdapter = new AssignmentListViewAdapter(getActivity(), R.layout.list_item_assignment, listOfAssignments);
        assignmentList.setAdapter(assignmentListViewAdapter);

        assignmentList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Assignment assignmentSelected = (Assignment) adapterView.getItemAtPosition(i);
                FragmentAssignmentDetail fragmentAssignmentDetail = new FragmentAssignmentDetail();
                Bundle bundle = new Bundle();
                bundle.putParcelable("assignment", assignmentSelected);
                fragmentAssignmentDetail.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, fragmentAssignmentDetail).addToBackStack("assgnDetail").commit();
            }
        });


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
        listOfAssignments = parseAssignmentObject.getListOfAssignments();
        assignmentListViewAdapter.addAll(listOfAssignments);
        assignmentListViewAdapter.notifyDataSetChanged();

    }

    public void updateViewForStudent(){
        parseUserObject = new ParseUserObject();
        if(parseUserObject.getUserType().equalsIgnoreCase("student")){
            addToMyClasses.setVisibility(View.VISIBLE);
            addToMyClasses.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                parseClassSection = new ParseClassSectionObject();
                    try {
                        parseClassSection.addStudentToClassRelation(parseUserObject.getCurrentUser(), classroomDetail);
                    }catch(ParseException e){
                        Log.i("addToList", e.getMessage());
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, new ClassListFragment())
                            .addToBackStack("myClassList")
                            .commit();
                }
            });
        }
    }
}

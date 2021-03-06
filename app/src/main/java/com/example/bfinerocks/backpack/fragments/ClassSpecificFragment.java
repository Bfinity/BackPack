package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.example.bfinerocks.backpack.constants.UserTypes;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject.ParseAssignmentInterface;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject.ParseStudentAssignmentInterface;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

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
    TextView viewStudentsInClass;
    Classroom classroomDetail;
    Button addToMyClasses;
    AssignmentListViewAdapter assignmentListViewAdapter;
    ParseAssignmentObject parseAssignmentObject;
    List<Assignment> listOfAssignments;
    ParseUserObject parseUserObject;
    ParseClassSectionObject parseClassSection;
    ParseStudentAssignmentObject studentAssignment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom_specific, container, false);
        parseAssignmentObject = new ParseAssignmentObject(new ParseAssignmentInterface() {
            @Override
            public void hasListUpdated(final List<Assignment> listOfAssignments) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateAssignmentListView(listOfAssignments);
                    }
                });
            }
        });
        parseUserObject = new ParseUserObject();
        studentAssignment = new ParseStudentAssignmentObject(new ParseStudentAssignmentInterface() {
            @Override
            public void hasListOfAssignmentsUpdated(final List<Assignment> listOfUpdatedAssignments) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateAssignmentListView(listOfUpdatedAssignments);
                    }
                });
            }
        });
        Classroom classRoom = getArguments().getParcelable("class");

        classroomDetail = classRoom;
        listOfAssignments = new ArrayList<Assignment>();
        assignmentList = (ListView) rootView.findViewById(R.id.assignment_list_view);
        classTitle = (TextView) rootView.findViewById(R.id.class_specific_title);
        classSubject = (TextView) rootView.findViewById(R.id.class_specific_subject);
        classGradeLevel = (TextView) rootView.findViewById(R.id.class_specific_grade);
        classTitle.setText(classRoom.getClassSectionName());
        classSubject.setText(classRoom.getClassSectionSubject());
        String classGrade = String.valueOf(classRoom.getClassSectionGradeLevel());
        classGradeLevel.setText(classGrade);
        addToMyClasses = (Button) rootView.findViewById(R.id.btn_add_class);
        addToMyClasses.setVisibility(View.GONE);
        addAssignment = (TextView) rootView.findViewById(R.id.add_assignment);
        assignmentListViewAdapter = new AssignmentListViewAdapter(getActivity(), R.layout.list_item_assignment, listOfAssignments);
        assignmentList.setAdapter(assignmentListViewAdapter);
        assignmentList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Assignment assignmentSelected = (Assignment) adapterView.getItemAtPosition(i);
                AssignmentDetailFragment assignmentDetailFragment = new AssignmentDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("assignment", assignmentSelected);
                bundle.putParcelable("class", classroomDetail);
                assignmentDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, assignmentDetailFragment).addToBackStack("assgnDetail").commit();
            }
        });
        addAssignment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAssignmentFragment createNewAssignmentFragment = new CreateNewAssignmentFragment();
                createNewAssignmentFragment.setArguments(getArguments());
                getFragmentManager().beginTransaction().replace(R.id.container, createNewAssignmentFragment)
                        .addToBackStack("createAssignment").commit();

            }
        });

        viewStudentsInClass = (TextView) rootView.findViewById(R.id.view_students);
        viewStudentsInClass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentListViewFragment studentListViewFragment = new StudentListViewFragment();
                studentListViewFragment.setArguments(getArguments());
                getFragmentManager().beginTransaction().replace(R.id.container, studentListViewFragment)
                        .addToBackStack("studentList")
                        .commit();
            }
        });
        updateViewForStudent();
        ParseThreadPool parseThreadPool = new ParseThreadPool();
        if(parseUserObject.getUserTypeEnum().equals(UserTypes.STUDENT)){
            parseThreadPool.execute(studentAssignment.createListOfStudentAssignmentObjectsForDisplay(classRoom));
        }
        else if(parseUserObject.getUserTypeEnum().equals(UserTypes.TEACHER)) {
            parseThreadPool.execute(parseAssignmentObject.createListOfAssignmentsAssociatedWithClassroom(classRoom));
        }

        return rootView;
    }

    public void updateAssignmentListView(List<Assignment> assignments){
        assignmentListViewAdapter.clear();
        listOfAssignments = assignments;
        assignmentListViewAdapter.addAll(listOfAssignments);
        assignmentListViewAdapter.notifyDataSetChanged();

    }

    public void updateViewForStudent(){
        parseUserObject = new ParseUserObject();
        if(parseUserObject.getUserType().equalsIgnoreCase("student")){
            ParseThreadPool parseThreadPool = new ParseThreadPool();
            parseThreadPool.execute(studentAssignment.addAssignmentsToStudentAssignments(classroomDetail));
                addToMyClasses.setVisibility(View.VISIBLE);
                addToMyClasses.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parseClassSection = new ParseClassSectionObject();
                        ParseThreadPool parseThreadPool = new ParseThreadPool();
                        parseThreadPool.execute(parseClassSection.addStudentToClassRelation(classroomDetail));
                        getFragmentManager().beginTransaction().replace(R.id.container, new ClassListFragment())
                                .addToBackStack("myClassList")
                                .commit();
                    }
                });

            addAssignment.setVisibility(View.GONE);
            viewStudentsInClass.setVisibility(View.GONE);
        }
    }
}

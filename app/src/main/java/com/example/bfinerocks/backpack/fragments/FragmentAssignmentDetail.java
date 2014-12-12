package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.AssignmentResponseTeacherListViewAdapter;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject.ParseStudentAssignmentInterface;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/29/14.
 */
public class FragmentAssignmentDetail extends Fragment {

    private TextView assignmentTitle;
    private TextView assigmentAssgnDate;
    private TextView assignmentDueDate;
    private TextView assignmentDetails;
    private EditText assignmentNotes;
    private ListView assignmentResponses;
    private Assignment assignment;
    private Boolean assignmentIsComplete;
    private CheckBox assignmentStateBox;
    private Button saveChanges;
    private ParseUserObject currentUser;
    private ParseAssignmentObject parseAssignmentObject;
    private ParseStudentAssignmentObject studentAssignment;
    private AssignmentResponseTeacherListViewAdapter responseAdapter;
    private ParseClassSectionObject classSectionObject;
    private List<Assignment> listOfResponses;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_detail, container, false);
        parseAssignmentObject = new ParseAssignmentObject();
        studentAssignment = new ParseStudentAssignmentObject(new ParseStudentAssignmentInterface() {
            @Override
            public void hasListOfAssignmentsUpdated(final List<Assignment> listOfUpdatedAssignments) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseAdapter.addAll(listOfUpdatedAssignments);
                        responseAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        currentUser = new ParseUserObject();
        listOfResponses = new ArrayList<Assignment>();
        assignment = getArguments().getParcelable("assignment");
        assignmentTitle = (TextView) rootView.findViewById(R.id.detail_assignment_title);
        assigmentAssgnDate = (TextView) rootView.findViewById(R.id.detail_assignment_assgn_date);
        assignmentDueDate = (TextView) rootView.findViewById(R.id.detail_assignment_due_date);
        assignmentDetails = (TextView) rootView.findViewById(R.id.detail_assignment_details);
        assignmentStateBox = (CheckBox) rootView.findViewById(R.id.checkBox_state);
        saveChanges = (Button) rootView.findViewById(R.id.btn_save_changes);
        assignmentNotes = (EditText) rootView.findViewById(R.id.assignment_notes);
        if(currentUser.getUserType().equalsIgnoreCase("teacher")){
            saveChanges.setVisibility(View.GONE);
            assignmentStateBox.setVisibility(View.GONE);
            assignmentNotes.setVisibility(View.GONE);
        }
        saveChanges.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(assignmentStateBox.isChecked()){
                    assignment.isAssignmentCompleted(true);
                }
                    assignment.setAssignmentNotes(assignmentNotes.getText().toString());
                ParseThreadPool parseThreadPool = new ParseThreadPool();
                    parseThreadPool.execute(studentAssignment.updateStudentAssignment(assignment));
                getFragmentManager().popBackStackImmediate();
            }
        });

        assignmentTitle.setText(assignment.getAssignmentTitle());
        assigmentAssgnDate.setText(assignment.getAssignmentAssignedDate());
        assignmentDueDate.setText(assignment.getAssignmentDueDate());
        assignmentDetails.setText(assignment.getAssignmentDescription());
        assignmentIsComplete = assignment.getAssignmentCompletionState();
        if(currentUser.getUserType().equalsIgnoreCase("Student")) {
            if (assignmentIsComplete) {
                assignmentStateBox.setChecked(true);
            } else {
                assignmentStateBox.setChecked(false);
            }
            assignmentNotes.setText(assignment.getAssignmentNotes());
        }
        if(currentUser.getUserType().equalsIgnoreCase("Teacher")) {
            assignmentResponses = (ListView) rootView.findViewById(R.id.student_response_list);
            responseAdapter = new AssignmentResponseTeacherListViewAdapter(getActivity(), R.layout.list_item_assignment_response, listOfResponses);
            assignmentResponses.setAdapter(responseAdapter);
            ParseThreadPool parseThreadPool = new ParseThreadPool();
            parseThreadPool.execute(studentAssignment.createListOfStudentResponses(assignment));
        }

        return rootView;
    }



}

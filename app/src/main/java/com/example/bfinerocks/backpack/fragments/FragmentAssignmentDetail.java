package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;

/**
 * Created by BFineRocks on 11/29/14.
 */
public class FragmentAssignmentDetail extends Fragment {

    private TextView assignmentTitle;
    private TextView assigmentAssgnDate;
    private TextView assignmentDueDate;
    private TextView assignmentDetails;
    private TextView assignmentState;
    private Assignment assignment;
    private String completionState;
    private CheckBox assignmentStateBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_detail, container, false);

        assignment = getArguments().getParcelable("assignment");
        assignmentTitle = (TextView) rootView.findViewById(R.id.detail_assignment_title);
        assigmentAssgnDate = (TextView) rootView.findViewById(R.id.detail_assignment_assgn_date);
        assignmentDueDate = (TextView) rootView.findViewById(R.id.detail_assignment_due_date);
        assignmentDetails = (TextView) rootView.findViewById(R.id.detail_assignment_details);
 //       assignmentState = (TextView) rootView.findViewById(R.id.detail_assignment_state);
        assignmentStateBox = (CheckBox) rootView.findViewById(R.id.checkBox_state);

        assignmentTitle.setText(assignment.getAssignmentTitle());
        assigmentAssgnDate.setText(assignment.getAssignmentAssignedDate());
        assignmentDueDate.setText(assignment.getAssignmentDueDate());
        assignmentDetails.setText(assignment.getAssignmentDescription());
        if(assignment.getAssignmentCompletionState()){
  //          assignmentState.setText("Done");
            assignmentStateBox.setChecked(true);
            assignmentStateBox.setText("Done!");
        }
        else{
   //         assignmentState.setText("Not Done");
        }
        return rootView;
    }
}

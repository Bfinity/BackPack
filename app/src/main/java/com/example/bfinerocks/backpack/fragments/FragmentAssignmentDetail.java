package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;

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
    private Boolean assignmentIsComplete;
    private CheckBox assignmentStateBox;
    private Button saveChanges;
    private ParseUserObject currentUser;
    private ParseAssignmentObject parseAssignmentObject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_detail, container, false);

        currentUser = new ParseUserObject();
        parseAssignmentObject = new ParseAssignmentObject();

        assignment = getArguments().getParcelable("assignment");
        assignmentTitle = (TextView) rootView.findViewById(R.id.detail_assignment_title);
        assigmentAssgnDate = (TextView) rootView.findViewById(R.id.detail_assignment_assgn_date);
        assignmentDueDate = (TextView) rootView.findViewById(R.id.detail_assignment_due_date);
        assignmentDetails = (TextView) rootView.findViewById(R.id.detail_assignment_details);
 //       assignmentState = (TextView) rootView.findViewById(R.id.detail_assignment_state);
        assignmentStateBox = (CheckBox) rootView.findViewById(R.id.checkBox_state);
        saveChanges = (Button) rootView.findViewById(R.id.btn_save_changes);
        if(currentUser.getUserType().equalsIgnoreCase("teacher")){
            saveChanges.setVisibility(View.GONE);
        }
        saveChanges.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(assignmentStateBox.isChecked()){
                    assignment.isAssignmentCompleted(true);
                }
                try {
                    parseAssignmentObject.updateAssignment(assignment);
                }catch (ParseException e){
                    Log.i("assignmentUpdate", e.getMessage());
                }
            }
        });

        assignmentTitle.setText(assignment.getAssignmentTitle());
        assigmentAssgnDate.setText(assignment.getAssignmentAssignedDate());
        assignmentDueDate.setText(assignment.getAssignmentDueDate());
        assignmentDetails.setText(assignment.getAssignmentDescription());
        assignmentIsComplete = assignment.getAssignmentCompletionState();
        if(assignmentIsComplete){
  //          assignmentState.setText("Done");
            assignmentStateBox.setChecked(true);
            assignmentStateBox.setText("Done!");
        }
        else{
   //         assignmentState.setText("Not Done");
            assignmentStateBox.setChecked(false);
        }
        return rootView;
    }

}

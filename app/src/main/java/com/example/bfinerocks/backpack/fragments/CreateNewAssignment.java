package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseAssignmentObject;

/**
 * Created by BFineRocks on 11/28/14.
 */
public class CreateNewAssignment extends Fragment {
    private TextView assignmentTitle;
    private TextView assignmentAssignedDate;
    private TextView assignmentDueDate;
    private TextView assignmentDirections;
    private Button addAssignment;
    private Assignment assignment;
    private ParseAssignmentObject parseAssignmentObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_assignment, container, false);
        assignmentTitle = (TextView) rootView.findViewById(R.id.enter_assignment_name);
        assignmentAssignedDate = (TextView) rootView.findViewById(R.id.enter_assgn_date);
        assignmentDueDate = (TextView) rootView.findViewById(R.id.enter_due_date);
        assignmentDirections = (TextView) rootView.findViewById(R.id.enter_assignment_directions);
        addAssignment = (Button) rootView.findViewById(R.id.btn_create_assignment);
        addAssignment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Classroom classroomToAssociate = getArguments().getParcelable("class");
                assignment = new Assignment(assignmentTitle.getText().toString(), assignmentAssignedDate.getText().toString(),
                        assignmentDueDate.getText().toString(), assignmentDirections.getText().toString());
                parseAssignmentObject = new ParseAssignmentObject();
                parseAssignmentObject.createNewAssignmentToPost(assignment, classroomToAssociate);
                Toast.makeText(getActivity(), "Assignment Added", Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }

     
}

package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;

/**
 * Created by BFineRocks on 11/28/14.
 */
public class CreateNewAssignment extends Fragment {
    TextView assignmentTitle;
    TextView assignmentAssignedDate;
    TextView assignmentDueDate;
    TextView assignmentDirections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_assignment, container, false);
        assignmentTitle = (TextView) rootView.findViewById(R.id.enter_assignment_name);
        assignmentAssignedDate = (TextView) rootView.findViewById(R.id.enter_assgn_date);
        assignmentDueDate = (TextView) rootView.findViewById(R.id.enter_due_date);
        assignmentDirections = (TextView) rootView.findViewById(R.id.enter_assignment_directions);

        return rootView;
    }
}

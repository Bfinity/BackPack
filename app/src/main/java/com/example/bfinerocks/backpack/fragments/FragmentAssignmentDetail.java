package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;

/**
 * Created by BFineRocks on 11/29/14.
 */
public class FragmentAssignmentDetail extends Fragment {

    private TextView assignmentTitle;
    private TextView assigmentAssgnDate;
    private TextView assignmentDueDate;
    private TextView assignmentDetails;
    private TextView assignmentState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assignment_detail, container, false);

        return rootView;
    }
}

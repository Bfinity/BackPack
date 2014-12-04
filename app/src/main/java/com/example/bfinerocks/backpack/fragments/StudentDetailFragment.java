package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject;

/**
 * Created by BFineRocks on 12/3/14.
 */
public class StudentDetailFragment extends Fragment {

    private TextView studentName;
    private TextView studentEmail;
    private ListView listOfStudentAssignments;
    private ParseStudentAssignmentObject studentAssignmentObjects;
    List

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);

        studentName = (TextView) rootView.findViewById(R.id.student_name);
        studentEmail = (TextView) rootView.findViewById(R.id.student_email_address);
        listOfStudentAssignments = (ListView) rootView.findViewById(R.id.student_assignment_list);

        UserModel userModel = getArguments().getParcelable("UserModel");

        studentName.setText(userModel.getUserFullName());
        studentEmail.setText(userModel.getUserEmail());

        return rootView;
    }
}

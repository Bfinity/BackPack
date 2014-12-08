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
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.AssignmentResponseListViewAdapter;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 12/3/14.
 */
public class StudentDetailFragment extends Fragment{

    private TextView studentName;
    private TextView studentEmail;
    private Button addStudentButton;
    private ListView listOfStudentAssignments;
    private ParseStudentAssignmentObject studentAssignmentObjects;
    private ArrayList<Assignment> listofAssignments;
    private AssignmentResponseListViewAdapter responseAdapter;
    private UserModel userModel;
    private ParseUserObject userObject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);

        userObject = new ParseUserObject();
        studentAssignmentObjects = new ParseStudentAssignmentObject();
        studentName = (TextView) rootView.findViewById(R.id.student_name);
        studentName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateView();
            }
        });
        studentEmail = (TextView) rootView.findViewById(R.id.student_email_address);
        addStudentButton = (Button) rootView.findViewById(R.id.add_student);
        listOfStudentAssignments = (ListView) rootView.findViewById(R.id.student_assignment_list);
        listofAssignments = new ArrayList<Assignment>();
        responseAdapter = new AssignmentResponseListViewAdapter(getActivity(), R.layout.list_item_assignment_response, listofAssignments);
        listOfStudentAssignments.setAdapter(responseAdapter);
        userModel = getArguments().getParcelable("UserModel");

        studentName.setText(userModel.getUserFullName());
        studentEmail.setText(userModel.getUserEmail());

        addStudentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
       //         userObject = new ParseUserObject();
                try {
                    userObject.addParentStudentRelationship(userModel);
                }catch (ParseException e){
                    Toast.makeText(getActivity(), "Unable to add student", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listOfStudentAssignments.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("list", "Clicked");
                Assignment assignmentSelected = (Assignment) adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable("assignment", assignmentSelected);
                FragmentAssignmentDetail fragmentAssignmentDetail = new FragmentAssignmentDetail();
                fragmentAssignmentDetail.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentAssignmentDetail)
                        .addToBackStack("assignmentDetail")
                        .commit();
            }

        });


        return rootView;
    }

    public void updateView(){
        listofAssignments = studentAssignmentObjects.createListOfStudentAssignmentObjectsForDisplay(
                    userObject.getUserByUID(userModel));
        responseAdapter.addAll(listofAssignments);
        responseAdapter.notifyDataSetChanged();
    }

}

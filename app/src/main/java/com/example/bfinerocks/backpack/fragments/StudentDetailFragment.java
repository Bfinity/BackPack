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
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject;
import com.example.bfinerocks.backpack.parse.ParseStudentAssignmentObject.ParseStudentAssignmentInterface;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject.ParseUserInterface;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
    private ParseThreadPool parseThreadPool;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);
        parseThreadPool = new ParseThreadPool();
        studentAssignmentObjects = new ParseStudentAssignmentObject(new ParseStudentAssignmentInterface() {
            @Override
            public void hasListOfAssignmentsUpdated(final List<Assignment> listOfUpdatedAssignments) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateView(listOfUpdatedAssignments);
                    }
                });
            }
        });
        studentName = (TextView) rootView.findViewById(R.id.student_name);
        studentEmail = (TextView) rootView.findViewById(R.id.student_email_address);
        addStudentButton = (Button) rootView.findViewById(R.id.add_student);
        listOfStudentAssignments = (ListView) rootView.findViewById(R.id.student_assignment_list);
        listofAssignments = new ArrayList<Assignment>();
        responseAdapter = new AssignmentResponseListViewAdapter(getActivity(), R.layout.list_item_assignment_response, listofAssignments);
        listOfStudentAssignments.setAdapter(responseAdapter);
        userModel = getArguments().getParcelable("UserModel");
        studentName.setText(userModel.getUserFullName());
        studentEmail.setText(userModel.getUserEmail());

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
        final ParseUserObject currentUser = new ParseUserObject(new ParseUserInterface() {
            @Override
            public void onLogInSuccess(UserModel userModel) {

            }

            @Override
            public void onLogInFailure(String result) {

            }

            @Override
            public void listOfUsersReturned(List<UserModel> listOfUsers) {

            }

            @Override
            public void relationAddedOnParse(final boolean relationSuccess) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(relationSuccess){
                            Toast.makeText(getActivity(), R.string.student_add_success, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), R.string.student_add_failure, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        addStudentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               currentUser.addParentStudentRelationship(userModel);
            }
        });
        updateViewOnUserType(currentUser.convertParseUserIntoUserModel(ParseUser.getCurrentUser()));
        return rootView;
    }

    public void updateViewOnUserType(UserModel currentUser){
        switch(currentUser.getUserEnum()){
            case PARENT:
                parseThreadPool.execute(studentAssignmentObjects.createListOfStudentAssignmentObjectsForDisplay(userModel));
                break;

            case TEACHER:
                Classroom classroom = getArguments().getParcelable("classroom");
                parseThreadPool.execute(studentAssignmentObjects.createListOfStudentAssignmentObjectsForDisplay(classroom, userModel));
                break;
        }
    }

    public void updateView(List<Assignment> assignmentList){
        listofAssignments = (ArrayList<Assignment>) assignmentList;
        responseAdapter.addAll(listofAssignments);
        responseAdapter.notifyDataSetChanged();
    }

}

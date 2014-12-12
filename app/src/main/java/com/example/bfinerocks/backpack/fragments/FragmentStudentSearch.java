package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject.ParseUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/5/14.
 */
public class FragmentStudentSearch extends Fragment {

    EditText studentNameEntry;
    EditText studentEmailEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_search, container, false);
        studentNameEntry = (EditText) rootView.findViewById(R.id.student_search_student_name);
        studentEmailEntry = (EditText) rootView.findViewById(R.id.student_search_student_email);
        Button search = (Button) rootView.findViewById(R.id.btn_student_search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUserObject parseUserObject = new ParseUserObject(new ParseUserInterface() {
                    @Override
                    public void onLogInSuccess(UserModel userModel) {

                    }

                    @Override
                    public void relationAddedOnParse(boolean relationSuccess) {

                    }

                    @Override
                    public void onLogInFailure(String result) {

                    }

                    @Override
                    public void listOfUsersReturned(final List<UserModel> listOfUsers) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<UserModel> studentSearchReturn = (ArrayList<UserModel>) listOfUsers;
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("listOfStudents", studentSearchReturn);
                                StudentSearchResults studentSearchResults = new StudentSearchResults();
                                studentSearchResults.setArguments(bundle);
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.container, studentSearchResults)
                                        .addToBackStack("studentSearchResults")
                                        .commit();

                            }
                        });
                    }
                });

                    ParseThreadPool parseThreadPool = new ParseThreadPool();
                    parseThreadPool.execute(parseUserObject.searchForStudentUser(studentNameEntry.getText().toString(),
                             studentEmailEntry.getText().toString()));
          /*          UserModel userModel = parseUserObject.convertParseUserIntoUserModel(studentUser);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("UserModel", userModel);
                    StudentDetailFragment studentDetailFragment = new StudentDetailFragment();
                    studentDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, studentDetailFragment).addToBackStack("studentDetail").commit();
                }catch(ParseException e){*/
    //                Toast.makeText(getActivity(), "No Student Found Matching Description", Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }
}

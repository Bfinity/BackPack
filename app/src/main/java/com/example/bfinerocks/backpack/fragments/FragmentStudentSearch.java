package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by BFineRocks on 12/5/14.
 */
public class FragmentStudentSearch extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_search, container, false);
        EditText studentNameEntry = (EditText) rootView.findViewById(R.id.student_search_student_name);
        EditText studentEmailEntry = (EditText) rootView.findViewById(R.id.student_search_student_name);
        UserModel userModel = new UserModel(null, studentEmailEntry.getText().toString(), studentNameEntry.getText().toString(), null);

        ParseUserObject parseUserObject = new ParseUserObject();
        try {
            ParseUser studentUser = parseUserObject.searchForStudentUser(userModel);
            userModel = parseUserObject.convertParseUserIntoUserModel(studentUser);
            Bundle bundle = new Bundle();
            bundle.putParcelable("student", userModel);
            StudentDetailFragment studentDetailFragment = new StudentDetailFragment();
            studentDetailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.container, studentDetailFragment).addToBackStack("studentDetail").commit();
        }catch(ParseException e){
            Toast.makeText(getActivity(), "No Student Found Matching Description", Toast.LENGTH_SHORT);
        }

        return rootView;
    }
}

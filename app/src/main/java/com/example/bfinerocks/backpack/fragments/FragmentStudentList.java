package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.StudentListViewAdapter;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/1/14.
 */
public class FragmentStudentList extends Fragment {
    ListView studentListView;
    StudentListViewAdapter studentListAdapter;
    List<ParseUser> listOfStudentUsers;
    ParseUserObject parseUserObject;
    TextView studentListHeader;

    @Override
    public void onResume() {
        super.onResume();
        Classroom classroom = getArguments().getParcelable("class");
        try {
            parseUserObject = new ParseUserObject();
            parseUserObject.updateListOfUsers("Student", classroom);
        }catch (ParseException e) {
            Log.i("studentView", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_list_view, container, false);

        listOfStudentUsers = new ArrayList<ParseUser>();
        parseUserObject = new ParseUserObject();
        studentListView = (ListView) rootView.findViewById(R.id.student_list);
        studentListAdapter = new StudentListViewAdapter(getActivity(), R.layout.list_item_student, listOfStudentUsers);
        studentListHeader = (TextView) rootView.findViewById(R.id.student_list_header);
        studentListView.setAdapter(studentListAdapter);
        studentListAdapter.addAll(listOfStudentUsers);

        studentListHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfStudentUsers = parseUserObject.getUserArrayList();
                studentListAdapter.addAll(listOfStudentUsers);
                studentListAdapter.notifyDataSetChanged();
            }
        });




        return rootView;
    }
}

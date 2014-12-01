package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.StudentListViewAdapter;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_list_view, container, false);

        listOfStudentUsers = new ArrayList<ParseUser>();
        parseUserObject = new ParseUserObject();
        studentListView = (ListView) rootView.findViewById(R.id.student_list);
        studentListAdapter = new StudentListViewAdapter(getActivity(), R.layout.list_item_student, listOfStudentUsers);
        parseUserObject.updateListOfUsers("Student");
        listOfStudentUsers = parseUserObject.getUserArrayList();
        studentListView.setAdapter(studentListAdapter);
        studentListAdapter.addAll(listOfStudentUsers);

        return rootView;
    }
}

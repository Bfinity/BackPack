package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.StudentListViewAdapter;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

import java.util.List;

/**
 * Created by BFineRocks on 12/11/14.
 */
public class StudentSearchResultsFragment extends Fragment{
        ListView studentListView;
        StudentListViewAdapter studentListAdapter;
        List<UserModel> listOfStudentUsers;
        ParseUserObject parseUserObject;
        TextView studentListHeader;
        TextView linkToSearchStudent;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_student_list_view, container, false);
            listOfStudentUsers = getArguments().getParcelableArrayList("listOfStudents");
            studentListView = (ListView) rootView.findViewById(R.id.student_list);
            studentListAdapter = new StudentListViewAdapter(getActivity(), R.layout.list_item_student, listOfStudentUsers);
            studentListHeader = (TextView) rootView.findViewById(R.id.student_list_header);
            linkToSearchStudent = (TextView) rootView.findViewById(R.id.link_search_students);
            studentListView.setAdapter(studentListAdapter);
            studentListAdapter.addAll(listOfStudentUsers);
            studentListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    UserModel  userModel = (UserModel) adapterView.getItemAtPosition(i);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("UserModel", userModel);
                    StudentDetailFragment studentDetail = new StudentDetailFragment();
                    studentDetail.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, studentDetail)
                            .addToBackStack("StudentDetail")
                            .commit();
                }

            });

            return rootView;
        }
}

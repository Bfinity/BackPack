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
import com.example.bfinerocks.backpack.adapters.ClassroomListViewAdapter;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class ClassListFragment extends Fragment{
    TextView classListLabel;
    ListView classListView;
    TextView addClassText;
    List<Classroom> myClassList;
    ClassroomListViewAdapter classroomListAdapter;
    ParseClassSectionObject classRooms;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_classroom_list, container, false);
            classRooms = new ParseClassSectionObject();
            classListView = (ListView) rootView.findViewById(R.id.class_list_view);
            addClassText = (TextView) rootView.findViewById(R.id.add_class);
            myClassList = new ArrayList<Classroom>();
            classroomListAdapter = new ClassroomListViewAdapter(getActivity(), R.layout.list_item_classroom, myClassList);
            classListView.setAdapter(classroomListAdapter);
            classListLabel = (TextView) rootView.findViewById(R.id.class_label);
            classRooms.updateListOfClassRooms();
            classListLabel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateView();
                }
            });
            return rootView;
        }

        public void updateView(){


            try{
            Thread.sleep(10000);}
            catch (InterruptedException e){
                Log.i("Exception", e.getMessage());
            }
            myClassList = classRooms.getArrayListOfClassrooms();
            classroomListAdapter.addAll(myClassList);
            classroomListAdapter.notifyDataSetChanged();
        }


}

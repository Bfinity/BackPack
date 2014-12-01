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
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.adapters.ClassroomListViewAdapter;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/1/14.
 */
public class FragmentClassSearchResults extends Fragment {
    TextView classSearchResultLabel;
    ListView classListView;
    TextView addClassText;
    List<Classroom> myClassList;
    ClassroomListViewAdapter classroomListAdapter;
    ParseUserObject parseUserObject;
    ParseClassSectionObject classRooms;


    @Override
    public void onResume() {
        super.onResume();
        classRooms = new ParseClassSectionObject();
      //  myClassList = new ArrayList<Classroom>();
  //      myClassList = getArguments().getParcelableArrayList("listOfReturnedClasses");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classroom_list, container, false);

        parseUserObject = new ParseUserObject();

        classListView = (ListView) rootView.findViewById(R.id.class_list_view);

        addClassText = (TextView) rootView.findViewById(R.id.add_class);


        myClassList = new ArrayList<Classroom>();
        classroomListAdapter = new ClassroomListViewAdapter(getActivity(), R.layout.list_item_classroom, myClassList);
       classroomListAdapter.addAll(myClassList);

        classListView.setAdapter(classroomListAdapter);
        classSearchResultLabel = (TextView) rootView.findViewById(R.id.class_list_label);
        classSearchResultLabel.setText(R.string.search_results);

        classSearchResultLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateView();
            }
        });

        classListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Classroom classRoom = (Classroom) adapterView.getItemAtPosition(i);
                ClassSpecificFragment classSpecificFragment = new ClassSpecificFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("class", classRoom);
                classSpecificFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, classSpecificFragment)
                        .addToBackStack("ClassListView")
                        .commit();

            }
        });

        return rootView;
    }

    public void updateView(){


        try{
            Thread.sleep(1000);}
        catch (InterruptedException e){
            Log.i("Exception", e.getMessage());
        }
    //    myClassList = classRooms.getArrayListOfClassrooms();
        myClassList = getArguments().getParcelableArrayList("listOfClassesReturned");
        classroomListAdapter.addAll(myClassList);
        classroomListAdapter.notifyDataSetChanged();
    }
}
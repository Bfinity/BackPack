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

import java.util.List;

/**
 * Created by BFineRocks on 12/5/14.
 */
public class FragmentStudentSearchResults extends Fragment {

    TextView searchHeader;
    ListView searchResults;
    List<UserModel> listOfStudentsFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results_student, container, false);


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

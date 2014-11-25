package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bfinerocks.backpack.R;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class SignUpFragment extends Fragment {
        EditText userName;
        EditText userPassword;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return rootView;
        }
}



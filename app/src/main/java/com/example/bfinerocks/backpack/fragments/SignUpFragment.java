package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bfinerocks.backpack.R;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class SignUpFragment extends Fragment {
    EditText edtUserName;
    EditText edtUserPassword;
    Spinner userTypeSelector;
    Button btnFinished;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            edtUserName = (EditText) rootView.findViewById(R.id.sign_up_user_name);
            edtUserPassword = (EditText) rootView.findViewById(R.id.sign_up_password);
            btnFinished = (Button) rootView.findViewById(R.id.btn_done);
            userTypeSelector = (Spinner) rootView.findViewById(R.id.spnr_user_type);
            ArrayAdapter<CharSequence> userTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_user_type_values,
                    android.R.layout.simple_spinner_item);
            userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userTypeSelector.setAdapter(userTypeAdapter);

        return rootView;
        }
}



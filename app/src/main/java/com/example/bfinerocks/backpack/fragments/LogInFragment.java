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
import com.example.bfinerocks.backpack.parse.ParseUserObject;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class LogInFragment extends Fragment {

    EditText userName;
    EditText password;
    Button btnFinished;



            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_login, container, false);

                userName = (EditText) rootView.findViewById(R.id.sign_up_user_name);
                password = (EditText) rootView.findViewById(R.id.sign_up_password);
                btnFinished = (Button) rootView.findViewById(R.id.btn_done);

                btnFinished.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParseUserObject user = new ParseUserObject();
                        user.signInExistingUser(userName.getText().toString(), password.getText().toString());
                    }
                });

                return rootView;
            }
}

package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.ParseException;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class SignUpFragment extends Fragment implements OnItemSelectedListener {
    private EditText edtUserName;
    private EditText edtUserPassword;
    private EditText edtUserFullName;
    private EditText edtUserEmail;
    private String userType;




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            edtUserName = (EditText) rootView.findViewById(R.id.sign_up_user_name);
            edtUserPassword = (EditText) rootView.findViewById(R.id.sign_up_password);
            edtUserFullName = (EditText) rootView.findViewById(R.id.enter_full_name);
            edtUserEmail = (EditText) rootView.findViewById(R.id.enter_email_address);
            Button btnFinished = (Button) rootView.findViewById(R.id.btn_done);
            Spinner userTypeSelector = (Spinner) rootView.findViewById(R.id.spnr_user_type);
            ArrayAdapter<CharSequence> userTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_user_type_values,
                    android.R.layout.simple_spinner_item);
            userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userTypeSelector.setAdapter(userTypeAdapter);
            userTypeSelector.setOnItemSelectedListener(this);
            btnFinished.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUserObject user = new ParseUserObject();
                    Boolean signUpSuccess = true;
                    try {
                        user.createNewParseUser(edtUserName.getText().toString(), edtUserPassword.getText().toString(), userType,
                             edtUserFullName.getText().toString(), edtUserEmail.getText().toString()   );
                    }catch(ParseException e){
                        Toast.makeText(getActivity(), "Sign Up Failed" +"\n"+ e.getMessage().toUpperCase(), Toast.LENGTH_SHORT).show();
                        signUpSuccess = false;
                    }
                    if(signUpSuccess) {
                        getFragmentManager().beginTransaction().replace(R.id.container, new ClassListFragment())
                                .addToBackStack("classList")
                                .commit();
                    }
                }
            });

        return rootView;
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        userType = (String) adapterView.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        userType = "Teacher";
    }
}



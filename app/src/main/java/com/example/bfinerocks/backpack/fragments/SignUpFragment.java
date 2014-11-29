package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

/**
 * Created by BFineRocks on 11/21/14.
 */
public class SignUpFragment extends Fragment implements OnItemSelectedListener {
    EditText edtUserName;
    EditText edtUserPassword;
    Spinner userTypeSelector;
    Button btnFinished;
    private String userType;
    public static String USER_NAME_KEY = "userName";
    public static String USER_TYPE_KEY = "userType";



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final SharedPreferences.Editor editor = sharedPref.edit();
            edtUserName = (EditText) rootView.findViewById(R.id.sign_up_user_name);
            edtUserPassword = (EditText) rootView.findViewById(R.id.sign_up_password);
            btnFinished = (Button) rootView.findViewById(R.id.btn_done);
            userTypeSelector = (Spinner) rootView.findViewById(R.id.spnr_user_type);
            ArrayAdapter<CharSequence> userTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_user_type_values,
                    android.R.layout.simple_spinner_item);
            userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userTypeSelector.setAdapter(userTypeAdapter);
            userTypeSelector.setOnItemSelectedListener(this);
            btnFinished.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUserObject user = new ParseUserObject();
                    user.createNewParseUser(edtUserName.getText().toString(), edtUserPassword.getText().toString(), userType);
                        editor.putString(USER_NAME_KEY, edtUserName.getText().toString());
                        editor.putString(USER_TYPE_KEY, userType);
                        editor.commit();
                    getFragmentManager().beginTransaction().replace(R.id.container, new CreateNewClassroom()).commit();
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



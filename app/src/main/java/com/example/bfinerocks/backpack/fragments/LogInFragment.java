package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.UserModel;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.example.bfinerocks.backpack.parse.ParseUserObject.ParseUserInterface;

import java.util.List;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class LogInFragment extends Fragment implements OnClickListener{

    private EditText userName;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        userName = (EditText) rootView.findViewById(R.id.sign_up_user_name);
        password = (EditText) rootView.findViewById(R.id.sign_up_password);
        Button btnFinished = (Button) rootView.findViewById(R.id.btn_done);
        TextView signUpLink = (TextView) rootView.findViewById(R.id.signUp_link);

        btnFinished.setOnClickListener(this);
        signUpLink.setOnClickListener(this);

        return rootView;
    }


    public void changeOnUserType(UserModel userModel){
        switch (userModel.getUserEnum()){
            case TEACHER:
                transitionToClassFragment();
                break;

            case STUDENT:
                transitionToClassFragment();
                break;

            case PARENT:
                transitionToStudentFragment();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_done:
                final ParseUserObject user = new ParseUserObject(new ParseUserInterface() {
                    @Override
                    public void onLogInSuccess(final UserModel userModel) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(userModel != null){
                                    changeOnUserType(userModel);
                                }
                            }
                        });

                    }

                    @Override
                    public void onLogInFailure(String result) {
                        Toast.makeText(getActivity(), "Log In Failed" + "\n" + result.toUpperCase(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void listOfUsersReturned(List<UserModel> listOfUsers) {

                    }
                });
/*                Handler handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                       // super.handleMessage(msg);
                        Log.i("message", msg.toString());
                        Bundle bundle = msg.getData();
                        UserModel userModel = bundle.getParcelable("userModel");
                        changeOnUserType(userModel);
                    }
                };*/

               // ParseUserObject user = new ParseUserObject(handler);
                ParseThreadPool parseThreadPool = new ParseThreadPool();
                parseThreadPool.execute(user.signInExistingUser(userName.getText().toString(), password.getText().toString()));

                break;

            case R.id.signUp_link:

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SignUpFragment())
                        .addToBackStack("signUpFragment")
                        .commit();

                break;
        }
    }

    public void transitionToClassFragment(){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ClassListFragment())
                    .addToBackStack("classList")
                    .commit();
    }

    public void transitionToStudentFragment(){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentStudentList())
                    .addToBackStack("studentList")
                    .commit();
    }
}

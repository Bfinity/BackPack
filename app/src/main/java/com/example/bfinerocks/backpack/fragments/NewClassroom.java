package com.example.bfinerocks.backpack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject;
import com.example.bfinerocks.backpack.parse.ParseClassSectionObject.ParseClassObjectInterface;
import com.example.bfinerocks.backpack.parse.ParseThreadPool;
import com.example.bfinerocks.backpack.parse.ParseUserObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class NewClassroom extends Fragment implements OnValueChangeListener{

    private EditText enterClassName;
    private EditText enterClassSubject;
    private NumberPicker enterClassGrade;
    private Button createClassButton;
    private int gradeLevel;
    ParseClassSectionObject classParseObject;
    ParseUserObject currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_class, container, false);
        currentUser = new ParseUserObject();
        enterClassName = (EditText) rootView.findViewById(R.id.enter_class_title);
        enterClassSubject = (EditText) rootView.findViewById(R.id.enter_class_subject);
        enterClassGrade = (NumberPicker) rootView.findViewById(R.id.enter_class_grade);
        createClassButton = (Button) rootView.findViewById(R.id.btn_create_class);

        enterClassGrade.setMaxValue(12);
        enterClassGrade.setMinValue(1);
    //    enterClassGrade.setValue(0); //todo create conditional for null value
        enterClassGrade.setOnValueChangedListener(this);

        createClassButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Classroom classroom = new Classroom(enterClassName.getText().toString(),
                        enterClassSubject.getText().toString(), gradeLevel);

                classParseObject = new ParseClassSectionObject(new ParseClassObjectInterface() {
                    @Override
                    public void classListReturned(List<Classroom> classroomList) {

                        if(classroomList.size() > 0) {
                            ArrayList<Classroom> foundClasses = (ArrayList<Classroom>) classroomList;
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("listOfClassesReturned", foundClasses);
                            FragmentClassSearchResults classSearchFragment = new FragmentClassSearchResults();
                            classSearchFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.container, classSearchFragment).commit();
                        }
                        else{
                            Toast.makeText(getActivity(), "Classroom Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if(currentUser.getUserType().equalsIgnoreCase("teacher")) {
                    classParseObject.createNewClassroom(classroom);
                    Toast.makeText(getActivity(), "Classroom Successfully Added", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.container, new ClassListFragment()).commit();
                }
                else if(currentUser.getUserType().equalsIgnoreCase("student")){
                    ParseThreadPool threadPool = new ParseThreadPool();
                    threadPool.execute(classParseObject.findClassroomOnParse(classroom));
                }

            }
        });

        return rootView;

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        gradeLevel = i2;
    }
}

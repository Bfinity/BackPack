package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;

import java.util.List;

/**
 * Created by BFineRocks on 12/4/14.
 */
public class AssignmentResponseTeacherListViewAdapter extends ArrayAdapter<Assignment> {

    public AssignmentResponseTeacherListViewAdapter(Context context, int resource, List<Assignment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("ViewCalled", "View Called");
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View assignmentResponse = inflater.inflate(R.layout.list_item_assignment_response, parent, false);
        Assignment assignment = getItem(position);
        TextView studentName = (TextView) assignmentResponse.findViewById(R.id.assignment_response_studentName);
        CheckBox noteCheckBox = (CheckBox) assignmentResponse.findViewById(R.id.chkbx_assignment_notes);

        studentName.setText(assignment.getStudentName());
        if(assignment.getAssignmentNotes() != null){
            noteCheckBox.setChecked(true);
        }

        if(assignment.getAssignmentCompletionState()){
            assignmentResponse.setBackgroundColor(getContext().getResources().getColor( R.color.green));
        }

        return assignmentResponse;
    }
}


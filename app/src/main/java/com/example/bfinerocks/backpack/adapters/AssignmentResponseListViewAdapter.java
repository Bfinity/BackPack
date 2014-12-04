package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by BFineRocks on 12/4/14.
 */
public class AssignmentResponseListViewAdapter extends ArrayAdapter<Assignment> {

    public AssignmentResponseListViewAdapter(Context context, int resource, List<Assignment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View assignmentResponse = inflater.inflate(R.layout.list_item_assignment_response, parent, false);
        Assignment assignment = getItem(position);
        TextView studentName = (TextView) assignmentResponse.findViewById(R.id.assignment_response_studentName);
        CheckBox noteCheckBox = (CheckBox) assignmentResponse.findViewById(R.id.chkbx_assignment_notes);

        studentName.setText(assignment.getStudentName());
        if(assignment.getAssignmentCompletionState()){
            noteCheckBox.setChecked(true);
        }
    }
}


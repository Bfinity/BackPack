package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;

import java.util.List;

/**
 * Created by BFineRocks on 11/29/14.
 */
public class AssignmentListViewAdapter extends ArrayAdapter<Assignment> {


        public AssignmentListViewAdapter(Context context, int resource, List<Assignment> objects) {
            super(context, resource, objects);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View assignmentItem = inflater.inflate(R.layout.list_item_assignment, parent, false);
            Assignment assignment = getItem(position);

            TextView assignmentDate = (TextView) assignmentItem.findViewById(R.id.assgned_date);
            assignmentDate.setText(assignment.getAssignmentAssignedDate());

            TextView assignmentTitle = (TextView) assignmentItem.findViewById(R.id.assignment_title);
            assignmentTitle.setText(assignment.getAssignmentTitle());

            if(assignment.getAssignmentCompletionState() != null){
                if(assignment.getAssignmentCompletionState()){
                    assignmentItem.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                }
            }

            return assignmentItem;
        }
    }



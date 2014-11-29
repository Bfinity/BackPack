package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Assignment;
import com.example.bfinerocks.backpack.models.Classroom;

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

            TextView titleText = (TextView) assignmentItem.findViewById(R.id.assgned_date);
            titleText.setText(assignment.getAssignmentAssignedDate());

            TextView gradeLevelText = (TextView) assignmentItem.findViewById(R.id.assignment_title);
            gradeLevelText.setText(assignment.getAssignmentTitle());

            return assignmentItem;
        }
    }

}

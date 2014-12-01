package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.Classroom;
import com.example.bfinerocks.backpack.models.User;

import java.util.List;

/**
 * Created by BFineRocks on 12/1/14.
 */
public class StudentListViewAdapter extends ArrayAdapter<User> {

    public StudentListViewAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View classRoomItem = inflater.inflate(R.layout.list_item_classroom, parent, false);
        Classroom classroom = getItem(position);

        TextView titleText = (TextView) classRoomItem.findViewById(R.id.class_title);
        titleText.setText(classroom.getClassSectionName());

        TextView gradeLevelText = (TextView) classRoomItem.findViewById(R.id.class_gradeLevel);
        gradeLevelText.setText(String.valueOf(classroom.getClassSectionGradeLevel()));

        return classRoomItem;
    }
}

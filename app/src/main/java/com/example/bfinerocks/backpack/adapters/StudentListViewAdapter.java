package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by BFineRocks on 12/1/14.
 */
public class StudentListViewAdapter extends ArrayAdapter<ParseUser> {

    public StudentListViewAdapter(Context context, int resource, List<ParseUser> objects) {
        super(context, resource, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View studentListItem = inflater.inflate(R.layout.list_item_student, parent, false);
        ParseUser parseUser = getItem(position);

        TextView titleText = (TextView) studentListItem.findViewById(R.id.student_name);
        titleText.setText(parseUser.getString("fullName"));

        return studentListItem;
    }
}

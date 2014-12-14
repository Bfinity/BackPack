package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.models.UserModel;

import java.util.List;

/**
 * Created by BFineRocks on 12/1/14.
 */
public class StudentListViewAdapter extends ArrayAdapter<UserModel> {

    public StudentListViewAdapter(Context context, int resource, List<UserModel> listOfUsers) {
        super(context, resource, listOfUsers);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View studentListItem = inflater.inflate(R.layout.list_item_student, parent, false);
        UserModel user = getItem(position);

        TextView titleText = (TextView) studentListItem.findViewById(R.id.student_name);
        titleText.setText(user.getUserFullName());

        return studentListItem;
    }
}

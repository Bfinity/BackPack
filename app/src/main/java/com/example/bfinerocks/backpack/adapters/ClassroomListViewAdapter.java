package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class ClassroomListViewAdapter extends ArrayAdapter<> {

    public ClassroomListViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }
}

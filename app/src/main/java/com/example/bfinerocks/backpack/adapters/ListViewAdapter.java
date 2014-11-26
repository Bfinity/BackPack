package com.example.bfinerocks.backpack.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by BFineRocks on 11/26/14.
 */
public class ListViewAdapter extends ArrayAdapter {
    public ListViewAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }
}

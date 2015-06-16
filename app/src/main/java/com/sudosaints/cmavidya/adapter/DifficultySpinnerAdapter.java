package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inni on 19/11/14.
 */
public class DifficultySpinnerAdapter extends ArrayAdapter {

    private Activity activity;
    private List<String> diffLevels;
    private LayoutInflater inflater;

    public DifficultySpinnerAdapter(Activity activity, List objects) {
        super(activity,  R.layout.spinner_row, objects);
        this.activity = activity;
        this.diffLevels = objects;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public String getItem(int position) {
        return (String) super.getItem(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent, boolean isDropdown) {

/********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        /***** Get each Model object from Arraylist ********/
        String value = getItem(position);
        TextView label = (TextView) row.findViewById(R.id.spinnerTextView);
        // Set values for spinner each row
        label.setText(value);
        if (isDropdown) label.setPadding(12, 16, 12, 16);
        return row;
    }
}

package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;

import java.util.List;

/**
 * Created by inni on 26/7/14.
 */
public class KeynotesTopicAdapter extends BaseAdapter {

    Activity activity;
    List<String> stringList;

    public KeynotesTopicAdapter(Activity activity, List<String> stringList) {
        this.activity = activity;
        this.stringList = stringList;

    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public String getItem(int i) {
        return stringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String title = stringList.get(i);

        if (view == null) {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.keynote_text_row, viewGroup, false);
        }
        TextView titleTextView;
        titleTextView = (TextView) view.findViewById(R.id.keynotesListRowTextView);

        if ((i % 2) == 1) {
            titleTextView.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_odd));
        } else {
            titleTextView.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_even));
        }


        titleTextView.setText(title);


        return view;
    }
}

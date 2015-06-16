package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Course;

import java.util.List;

/**
 * Created by inni on 15/8/14.
 */
public class CourseDropdownListAdapter extends BaseAdapter {

    List<Course> courses;
    Activity activity;

    public CourseDropdownListAdapter(Activity activity, List<Course> courses) {
        this.activity = activity;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Course getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Course course = getItem(i);

        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.course_dropdown_textview, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.courseDropdownTextView);

        textView.setText(course.getCourseName());


        return view;
    }
}

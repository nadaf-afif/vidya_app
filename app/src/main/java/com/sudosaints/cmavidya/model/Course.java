package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by inni on 11/8/14.
 */
@DatabaseTable
public class Course {


    @DatabaseField(id = true)
    private int courseId;
    @DatabaseField
    private String courseName;

    public Course() {

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}

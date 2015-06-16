package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.sudosaints.cmavidya.adapter.CourseDropdownListAdapter;
import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Course;
import com.sudosaints.cmavidya.model.User;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.CommonUtil;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by inni on 25/7/14.
 */
public class RegisterActivity extends Activity {
    private User user;
    private CMAVidyaApp cmaVidyaApp;
    private DBUtils dbUtils;
    private UIHelper uiHelper;
    private int courseSelectedInt = 0;
    private View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private CommonTaskExecutor.OnPostExecute onPostExecuteGetCourse = new CommonTaskExecutor.OnPostExecute() {
        @Override
        public void onPostExecute(Object object) {

        }
    };

    @InjectView(R.id.registerFirstNameEditText)
    EditText firstnameEditText;
    @InjectView(R.id.registerLastNameEditText)
    EditText lastnameEditText;
    @InjectView(R.id.registerUserNameEditText)
    EditText emailIdEditText;
    @InjectView(R.id.registerPasswordEditText)
    EditText passwordEditText;
    @InjectView(R.id.registerConfirmPasswordEditText)
    EditText confirmPwdEditText;
    @InjectView(R.id.registerSelectCourseTextView)
    TextView courseTextView;
    @InjectView(R.id.registerContinueButton)
    Button continueButton;

    @OnClick(R.id.registerContinueButton)
    void onContinueClick() {

        if (firstnameEditText.getText().toString().length() > 0) {
            user.setFirstname(firstnameEditText.getText().toString());
            if (lastnameEditText.getText().toString().length() > 0) {
                user.setLastname(lastnameEditText.getText().toString());
                if (emailIdEditText.getText().toString().length() > 0) {
                    if (CommonUtil.validateEmail(emailIdEditText.getText().toString())) {
                        user.setUsername(emailIdEditText.getText().toString());
                        if (passwordEditText.getText().toString().length() > 0) {
                            user.setPassword(passwordEditText.getText().toString());
                            if (passwordEditText.getText().toString().equals(confirmPwdEditText.getText().toString())) {
                                if (courseSelectedInt != 0) {
                                    user.setCourseId(courseSelectedInt);
                                    CommonTaskExecutor.registerUser(cmaVidyaApp, new CommonTaskExecutor.OnPostExecute() {
                                        @Override
                                        public void onPostExecute(Object object) {
                                            ApiResponse apiResponse = (ApiResponse) object;
                                            if (apiResponse.isSuccess()) {

                                                cmaVidyaApp.showToast(((Map<String, Object>) apiResponse.getData()).get("message").toString());
                                                finish();
                                            } else {

                                                cmaVidyaApp.showToast(apiResponse.getError().getMessage());
                                            }

                                        }
                                    }, user);

                                } else {
                                    cmaVidyaApp.showToast("Select Couser");
                                }
                            } else {
                                cmaVidyaApp.showToast("Password and ConfirmPassword does not match");
                            }
                        } else {
                            cmaVidyaApp.showToast("Enter Passwprd");
                        }

                    } else {
                        cmaVidyaApp.showToast("Enter proper Emailid");
                    }
                } else {
                    cmaVidyaApp.showToast("Enter Emailid");
                }
            } else {
                cmaVidyaApp.showToast("Enter Lasttname");
            }
        } else {
            cmaVidyaApp.showToast("Enter Firstname");
        }

/*
        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();*/
    }

    @OnClick(R.id.registerSelectCourseTextView)
    void onSelectCourse() {
        RuntimeExceptionDao<Course, Integer> courseDao = dbUtils.getDatabaseHelper().getCourseRuntimeExceptionDao();
        final List<Course> courseList = courseDao.queryForAll();

        if (courseList.size() > 0) {
            final Dialog dialog = new Dialog(RegisterActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.course_dropdown_list);
            dialog.setCancelable(true);

            ListView listView = (ListView) dialog.findViewById(R.id.courseDropdownListView);

            CourseDropdownListAdapter listAdapter = new CourseDropdownListAdapter(RegisterActivity.this, courseList);

            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    dialog.dismiss();
                    courseTextView.setText(courseList.get(i).getCourseName());
                    courseSelectedInt = courseList.get(i).getCourseId();
                }
            });


            dialog.show();

        } else {
            cmaVidyaApp.showToast("Feaching data from server ");

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cmaVidyaApp = (CMAVidyaApp) getApplication();
        dbUtils = new DBUtils(RegisterActivity.this);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        user = new User();

        uiHelper = new UIHelper(this);
        uiHelper.setActionBar(Constants.ActivityABarAction.REGISTER, actionBarLeftOnClickListener, null);

        CommonTaskExecutor.updateCourses(cmaVidyaApp, onPostExecuteGetCourse, dbUtils.getDatabaseHelper());

        // courseTextView;
    }

}

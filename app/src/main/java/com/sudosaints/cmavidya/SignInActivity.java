package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.model.User;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.CommonUtil;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignInActivity extends Activity {

    @InjectView(R.id.loginEmailEditText)
    EditText emailIdEditText;
    @InjectView(R.id.loginPasswordEditText)
    EditText passwordEditText;

    private CMAVidyaApp cmaVidyaApp;
    private User user;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        cmaVidyaApp = (CMAVidyaApp) getApplication();
        preferences = cmaVidyaApp.getPreferences();

        // Below code show login activity once when application first time installed
//        if (preferences.getUserName() != null) {
//            Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        user = new User();

        if (null != CommonTaskExecutor.getUserPreferance(preferences)) {
            Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnEditorAction(R.id.loginPasswordEditText)
    boolean OnEditorAction(int actionId) {
        if (EditorInfo.IME_ACTION_GO == actionId) {
            onLoginClick();
            return true;
        }
        return false;
    }

    @OnClick(R.id.loginSigninButton)
    public void onLoginClick() {
        if (emailIdEditText.getText().toString().length() > 0) {
            if (CommonUtil.validateEmail(emailIdEditText.getText().toString()) || true) {
                user.setEmail(emailIdEditText.getText().toString());
                if (passwordEditText.getText().toString().length() > 0) {
                    user.setPassword(passwordEditText.getText().toString());

                    CommonTaskExecutor.loginUser(cmaVidyaApp, new CommonTaskExecutor.OnPostExecute() {
                        @Override
                        public void onPostExecute(Object object) {
                            ApiResponse apiResponse = (ApiResponse) object;
                            if (apiResponse.isSuccess()) {
                                Map<String, Object> userObjectMap = (Map<String, Object>) apiResponse.getData();
                                user.setDob(userObjectMap.get("DateOfBirth") + "");
                                user.setFirstname(userObjectMap.get("FirstName") + "");
                                user.setIsmale(Boolean.parseBoolean(userObjectMap.get("Gender").toString()));
                                user.setCourseId(Integer.parseInt(userObjectMap.get("IdCourseMaster").toString()));
                                user.setLastname(userObjectMap.get("LastName") + "");
                                user.setMobile(userObjectMap.get("Mobile") + "");
                                user.setUsername(userObjectMap.get("UserName") + "");

                                CommonTaskExecutor.setUserPreferance(preferences, user);

                                Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                cmaVidyaApp.showToast(apiResponse.getError().getMessage());
                            }

                        }
                    }, user);
                } else {
                    cmaVidyaApp.showToast("Enter Password");
                }
            } else {
                cmaVidyaApp.showToast("Enter proper Email ID");
            }
        } else {
            cmaVidyaApp.showToast("Enter Email ID");
        }

    }

    @OnClick(R.id.loginRegisterButton)
    public void onRegisterClick(View view) {
        Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.loginGuestButton)
    public void onLoginGuestClick(View view) {
        Intent intent = new Intent(SignInActivity.this, GuestUserLoginActivity.class);
        startActivity(intent);
    }

}
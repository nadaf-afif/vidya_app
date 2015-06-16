package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

/**
 * Created by inni on 25/7/14.
 */
public class GuestUserLoginActivity extends Activity {

    Button loginButton;
   UIHelper uiHelper;
   View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           finish();
       }
   };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        uiHelper= new UIHelper(this);

        uiHelper.setActionBar(Constants.ActivityABarAction.GUEST_USER,actionBarLeftOnClickListener,null);
        loginButton=(Button)findViewById(R.id.guestLoginContinueButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GuestUserLoginActivity.this, ComingSoonActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

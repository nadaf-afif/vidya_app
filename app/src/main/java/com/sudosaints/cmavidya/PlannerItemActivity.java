package com.sudosaints.cmavidya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

/**
 * Created by inni on 25/7/14.
 */
public class PlannerItemActivity extends Activity {

    UIHelper uiHelper;
    View.OnClickListener actionBarLeftOnClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    View.OnClickListener actionBarRightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_item);
        uiHelper= new UIHelper(this);
        uiHelper.setActionBar(Constants.ActivityABarAction.PLANNER_ITEM,actionBarLeftOnClickListener,actionBarRightOnClickListener);

    }
}


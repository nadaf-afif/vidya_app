package com.sudosaints.cmavidya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by inni on 29/7/14.
 */
public class KeynotesAddNoteActivity extends Activity {

    @Bind(R.id.addNoteDeleteButton)
    Button deleteButton;

    @OnClick(R.id.addNoteDeleteButton) void sayHello() {
        Toast.makeText(this, "Hello, views!", Toast.LENGTH_LONG).show();

    }


    UIHelper uiHelper;

    View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keynotes_add_note);

        ButterKnife.bind(this);
        uiHelper = new UIHelper(KeynotesAddNoteActivity.this);
        uiHelper.setActionBar(Constants.ActivityABarAction.KEYNOTES_ADD_NOTE, actionBarLeftOnClickListener, null);
        /*addNoteEditText.setText("hello indraneel ");*/


    }
}

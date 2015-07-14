package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Notification;

import java.util.ArrayList;

/**
 * Created by Akshay M on 14/7/2015.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {

    private final Activity activity;

    private static class ViewHolder {
        TextView tv_note,tv_description;
    }

    public NotificationAdapter(Activity activity, ArrayList<Notification> commentReplyList) {
        super(activity, R.layout.notification_item_row, commentReplyList);
        this.activity=activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Notification item = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item_row, parent, false);
            holder.tv_note = (TextView) convertView.findViewById(R.id.tv_note);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(!item.getDescription().isEmpty() && !item.getNote().isEmpty())
        {
            Spanned description = Html.fromHtml(item.getDescription());
            holder.tv_note.setText(item.getNote());
            holder.tv_description.setText(description);
        }
        else
        {
            holder.tv_note.setText("No Data found");
        }


        return convertView;
    }
}

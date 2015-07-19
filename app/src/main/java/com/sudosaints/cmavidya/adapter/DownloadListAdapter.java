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
import com.sudosaints.cmavidya.model.Downloads;
import com.sudosaints.cmavidya.model.Notification;

import java.util.ArrayList;

/**
 * Created by Akshay M on 14/7/2015.
 */
public class DownloadListAdapter extends ArrayAdapter<Downloads> {

    private final Activity activity;

    private static class ViewHolder {
        TextView tv_subjectName,tv_description,tv_filename,tv_topicName;
    }

    public DownloadListAdapter(Activity activity, ArrayList<Downloads> commentReplyList) {
        super(activity, R.layout.downloadlist_item_row, commentReplyList);
        this.activity=activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Downloads item = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.downloadlist_item_row, parent, false);
            holder.tv_subjectName = (TextView) convertView.findViewById(R.id.tv_subjectName);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.tv_filename = (TextView) convertView.findViewById(R.id.tv_filename);
            holder.tv_topicName = (TextView) convertView.findViewById(R.id.tv_topicName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_subjectName.setText(item.getSubjectName());
        Spanned description = Html.fromHtml(item.getDescription());
        holder.tv_description.setText(description);
        holder.tv_filename.setText("File Name: "+item.getFileName());
        holder.tv_topicName.setText(item.getTopicName());

        return convertView;
    }
}

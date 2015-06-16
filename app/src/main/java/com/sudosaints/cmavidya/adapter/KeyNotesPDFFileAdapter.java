package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.KeyNotes;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by salt on 8/11/14.
 */
public class KeyNotesPDFFileAdapter extends BaseAdapter {

    private Activity activity;
    private List<KeyNotes> keyNoteses;
    private LayoutInflater layoutInflater;

    public KeyNotesPDFFileAdapter(Activity activity, List<KeyNotes> keyNoteses) {
        this.activity = activity;
        this.keyNoteses = keyNoteses;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return keyNoteses.size();
    }

    @Override
    public KeyNotes getItem(int position) {
        return keyNoteses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        KeyNotes keyNotes = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_keynotes, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = ButterKnife.findById(convertView, R.id.row_keynote_thumbnil);
            viewHolder.progressBar = ButterKnife.findById(convertView, R.id.progressBar);
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        //String path="http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png";
        Picasso.with(activity).load(keyNotes.getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}

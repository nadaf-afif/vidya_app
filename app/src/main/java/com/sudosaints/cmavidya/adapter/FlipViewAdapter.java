package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.KeyNotes;

import java.util.List;

/**
 * Created by inni on 25/11/14.
 */
public class FlipViewAdapter extends BaseAdapter {

    public interface OnClickListner {
        public void onKeynoteClick(KeyNotes keyNotes);
    }

    private List<List<KeyNotes>> gridKeyNotesList;
    private Activity activity;
    private LayoutInflater inflater;
    private OnClickListner onClickListner;


    public FlipViewAdapter(Activity activity, List<List<KeyNotes>> views, OnClickListner onClickListner) {
        this.activity = activity;
        this.gridKeyNotesList = views;
        this.onClickListner = onClickListner;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gridKeyNotesList.size();
    }

    @Override
    public List<KeyNotes> getItem(int i) {
        return gridKeyNotesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //if (null == view) {
        view = (View) inflater.inflate(R.layout.flip_layout, null);
        // }
        FrameLayout frameLayout6 = (FrameLayout) view.findViewById(R.id.book6FL);
        FrameLayout frameLayout5 = (FrameLayout) view.findViewById(R.id.book5FL);
        FrameLayout frameLayout4 = (FrameLayout) view.findViewById(R.id.book4FL);
        FrameLayout frameLayout3 = (FrameLayout) view.findViewById(R.id.book3FL);
        FrameLayout frameLayout2 = (FrameLayout) view.findViewById(R.id.book2FL);
        FrameLayout frameLayout1 = (FrameLayout) view.findViewById(R.id.book1FL);
        frameLayout1.setVisibility(View.GONE);
        frameLayout2.setVisibility(View.GONE);
        frameLayout3.setVisibility(View.GONE);
        frameLayout4.setVisibility(View.GONE);
        frameLayout5.setVisibility(View.GONE);
        frameLayout6.setVisibility(View.GONE);


        switch (getItem(i).size()) {
            case 6:
                ImageView imageView6 = (ImageView) view.findViewById(R.id.book6IV);
                TextView textView6 = (TextView) view.findViewById(R.id.book6TV);
                frameLayout6.setVisibility(View.VISIBLE);
                Picasso.with(activity).load(getItem(i).get(5).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(imageView6);
                textView6.setText(getItem(i).get(5).getDescription());
                frameLayout6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(5));
                    }
                });
            case 5:
                ImageView imageView5 = (ImageView) view.findViewById(R.id.book5IV);
                TextView textView5 = (TextView) view.findViewById(R.id.book5TV);
                frameLayout5.setVisibility(View.VISIBLE);
                // Log.d("url : ", getItem(i).get(4).getThumbnailUrl().replace(" ", "%20"));
                Picasso.with(activity).load(getItem(i).get(4).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).fit().into(imageView5);
                textView5.setText(getItem(i).get(4).getDescription());
                frameLayout5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(4));
                    }
                });
            case 4:
                ImageView imageView4 = (ImageView) view.findViewById(R.id.book4IV);
                TextView textView4 = (TextView) view.findViewById(R.id.book4TV);
                frameLayout4.setVisibility(View.VISIBLE);
                Picasso.with(activity).load(getItem(i).get(3).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(imageView4);
                textView4.setText(getItem(i).get(3).getDescription());
                frameLayout4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(3));
                    }
                });
            case 3:
                ImageView imageView3 = (ImageView) view.findViewById(R.id.book3IV);
                TextView textView3 = (TextView) view.findViewById(R.id.book3TV);
                frameLayout3.setVisibility(View.VISIBLE);
                Picasso.with(activity).load(getItem(i).get(2).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(imageView3);
                textView3.setText(getItem(i).get(2).getDescription());
                frameLayout3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(2));
                    }
                });
            case 2:
                ImageView imageView2 = (ImageView) view.findViewById(R.id.book2IV);
                TextView textView2 = (TextView) view.findViewById(R.id.book2TV);
                frameLayout2.setVisibility(View.VISIBLE);
                Picasso.with(activity).load(getItem(i).get(1).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(imageView2);
                textView2.setText(getItem(i).get(1).getDescription());
                frameLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(1));
                    }
                });
            case 1:
                ImageView imageView1 = (ImageView) view.findViewById(R.id.book1IV);
                TextView textView1 = (TextView) view.findViewById(R.id.book1TV);
                frameLayout1.setVisibility(View.VISIBLE);
                Picasso.with(activity).load(getItem(i).get(0).getThumbnailUrl().replace(" ", "%20")).placeholder(R.drawable.notepink).into(imageView1);
                textView1.setText(getItem(i).get(0).getDescription());
                frameLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListner.onKeynoteClick(getItem(i).get(0));
                    }
                });


        }


        return view;
    }
}

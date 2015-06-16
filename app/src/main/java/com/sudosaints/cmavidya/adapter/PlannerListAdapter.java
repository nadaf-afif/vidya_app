package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.PlanEvents;

import java.util.List;

/**
 * Created by inni on 25/7/14.
 */
public class PlannerListAdapter extends BaseAdapter {

	public interface PlannerListOptionOnClickListener {
		public void longClick(PlanEvents planEvent);
	}

	private List<PlanEvents> PlanEventss;
	private Activity activity;
	private PlannerListOptionOnClickListener listener;


	public PlannerListAdapter(Activity activity, List<PlanEvents> PlanEventss, PlannerListOptionOnClickListener listener) {
		this.activity = activity;
		this.PlanEventss = PlanEventss;
		this.listener = listener;

	}

	@Override
	public int getCount() {
		return PlanEventss.size();
	}

	@Override
	public PlanEvents getItem(int i) {
		return PlanEventss.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		final PlanEvents planEvents = PlanEventss.get(position);

		if (view == null) {
			LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.swipe_row, viewGroup, false);
		}

		LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.front);
		if ((position % 2) == 1) {
			linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_odd));
		} else {
			linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_even));
		}
		TextView titleTextView, descriptionTextView, startTimeTextView, endTimeTextView;

		titleTextView = (TextView) view.findViewById(R.id.plannerRowHeadlineTextView);
		descriptionTextView = (TextView) view.findViewById(R.id.plannerRowDescTextView);
		startTimeTextView = (TextView) view.findViewById(R.id.plannerRowStartTimeTextView);
		endTimeTextView = (TextView) view.findViewById(R.id.plannerRowEndTimeTextView);

		if (getItem(position).getDescription().equalsIgnoreCase("UserDefinedEvents")) {
			titleTextView.setText(planEvents.getTopicName());
			descriptionTextView.setText("");
			startTimeTextView.setText("");
			endTimeTextView.setText("");
			if ((position % 2) == 1) {
				linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.user_event_odd));
			} else {
				linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.user_event));
			}

		} else {
			titleTextView.setText(planEvents.getSubject());
			descriptionTextView.setText(planEvents.getTopicName());
			startTimeTextView.setText(planEvents.getTopicTime());
			endTimeTextView.setText("Revision: " + planEvents.getRevisionNumber());
		}


		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				listener.longClick(planEvents);
				return true;
			}
		});

		return view;
	}
}

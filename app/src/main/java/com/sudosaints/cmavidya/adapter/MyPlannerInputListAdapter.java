package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.MyplanUnderstandingDetails;
import com.sudosaints.cmavidya.model.PlanEvents;

import java.util.List;

/**
 * Created by inni on 7/2/15.
 */
public class MyPlannerInputListAdapter extends BaseAdapter {

	private List<PlanEvents> PlanEventss;
	private Activity activity;
	private List<Integer> understanding;
	private List<Boolean> isSelected;
	private List<Integer> understandingSelectedList;


	public MyPlannerInputListAdapter(Activity activity, MyplanUnderstandingDetails myplanUnderstandingDetails) {
		this.activity = activity;
		this.PlanEventss = myplanUnderstandingDetails.getPlanEvents();
		this.understanding = myplanUnderstandingDetails.getUnderstandingsList();
		this.isSelected = myplanUnderstandingDetails.getIsSelectedList();
		this.understandingSelectedList = myplanUnderstandingDetails.getUnderstandingSelectedList();
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
		final UnderstandingSpinnerAdapter understandingSpinnerAdapter = new UnderstandingSpinnerAdapter(activity, understanding);

		if (view == null) {
			LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.list_item_my_plan_input, viewGroup, false);
		}

		LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.front);
		if ((position % 2) == 1) {
			linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_odd));
		} else {
			linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_even));
		}


		TextView subjectTextView, topicTextView, revisionTextView;
		Spinner spinner;

		subjectTextView = (TextView) view.findViewById(R.id.myPlanEventSubjectTV);
		topicTextView = (TextView) view.findViewById(R.id.myPlanEventTopicTV);
		revisionTextView = (TextView) view.findViewById(R.id.myPlanEventRevisionTV);
		spinner = (Spinner) view.findViewById(R.id.myPlanEventPSP);


		//titleTextView = (TextView) view.findViewById(R.id.plannerRowHeadlineTextView);
		//	descriptionTextView = (TextView) view.findViewById(R.id.plannerRowDescTextView);
		//	startTimeTextView = (TextView) view.findViewById(R.id.plannerRowStartTimeTextView);
		//	endTimeTextView = (TextView) view.findViewById(R.id.plannerRowEndTimeTextView);

		/*if (getItem(position).getDescription().equalsIgnoreCase("UserDefinedEvents")) {
			titleTextView.setText(planEvents.getTopicName());
			descriptionTextView.setText("");
			startTimeTextView.setText("");
			endTimeTextView.setText("");
			if ((position % 2) == 1) {
				linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.user_event_odd));
			} else {
				linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.user_event));
			}

		} else {*/
		/*titleTextView.setText(planEvents.getSubject());
		descriptionTextView.setText(planEvents.getTopicName());
		startTimeTextView.setText(planEvents.getTopicTime());
		endTimeTextView.setText("Revision: " + planEvents.getRevisionNumber());*/
//		}
		return view;
	}
}

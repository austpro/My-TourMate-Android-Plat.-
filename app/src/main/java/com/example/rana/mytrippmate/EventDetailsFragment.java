package com.example.rana.mytrippmate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.rana.mytrippmate.pojoclasses.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

    private Context context;
    private TextView eventNameTV,eventDestinationTV,startingLocationTV,departuretimeTV,eventBudgetTV;
    private ExpandableListView expandableListView;

    private HashMap<String,List<String>> expandableListDetail;
    private List<String> expandableListTitle;


    private CustomExpandableListAdapter customExpandableListAdapter;

    public EventDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_event_details, container, false);
         prepareExpandableListViewData();
         eventNameTV=view.findViewById(R.id.eventNameTv);
         //eventDestinationTV=view.findViewById(R.id.eventDestinationTv);
         //startingLocationTV=view.findViewById(R.id.eventStartingLocationTV);
         //departuretimeTV=view.findViewById(R.id.departureTimeTV);
         eventBudgetTV=view.findViewById(R.id.eventBudgetTV);
         Bundle bundle=getArguments();
         Event event= (Event) bundle.getSerializable("event");

         eventNameTV.setText(event.getEventName());
         eventBudgetTV.setText("Budget Status ( "+0+"/"+event.getBudget()+" )");
         //eventDestinationTV.setText(event.getDestination());
         //startingLocationTV.setText(event.getStartingLocation());
         //departuretimeTV.setText(event.getDepartureTime());
        expandableListView=view.findViewById(R.id.expandableListView_id);

        expandableListTitle=new ArrayList<String>(expandableListDetail.keySet());
        customExpandableListAdapter=new CustomExpandableListAdapter(context,expandableListDetail,expandableListTitle);
        expandableListView.setAdapter(customExpandableListAdapter);

         return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private void prepareExpandableListViewData() {

        expandableListDetail=new HashMap<String, List<String>>();

        List<String> moreOnEvents=new ArrayList<>();
        moreOnEvents.add("Edit Event");
        moreOnEvents.add("Delete Event");

        List<String> moments=new ArrayList<>();
        moments.add("Take a Photo");
        moments.add("View Gallery");
        moments.add("View All Moments");

        List<String> expenditures=new ArrayList<>();
        expenditures.add("Add New Expense");
        expenditures.add("View All Expense");
        expenditures.add("Add More Budget");




        expandableListDetail.put("More On Event",moreOnEvents);
        expandableListDetail.put("Moments",moments);
        expandableListDetail.put("Expenditure",expenditures);

    }

}

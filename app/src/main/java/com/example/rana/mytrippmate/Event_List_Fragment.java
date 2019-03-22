package com.example.rana.mytrippmate;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rana.mytrippmate.pojoclasses.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event_List_Fragment extends Fragment implements EventAdapter.MenuActionListener {

    private RecyclerView recyclerView;

    private FloatingActionButton fab;
    private Button logoutButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Context context;
    private FabButtonActionListener fabButtonActionListener;

    private LinearLayoutManager layoutManager;
    private EventAdapter adapter;
    private List<Event> eventList=new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference rootReference;
    private DatabaseReference userReference;
    private DatabaseReference eventReference;



    public Event_List_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        rootReference= FirebaseDatabase.getInstance().getReference();
        userReference=rootReference.child("Users").child(user.getUid());
        eventReference=userReference.child("Events");

        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    Event event=data.getValue(Event.class);
                    eventList.add(event);
                }
                adapter.updateAdapter(eventList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_event__list_, container, false);

        recyclerView=view.findViewById(R.id.event_list_Rview_id);
        fab=view.findViewById(R.id.fab_button_id);
        logoutButton=view.findViewById(R.id.logoutButton_Id);
        adapter=new EventAdapter(context,eventList,this);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fabButtonActionListener.onFabButtonClick();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth!=null)
                {
                    auth.signOut();
                }
                fabButtonActionListener.onLogoutButtonClick();
            }
        });

        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        fabButtonActionListener= (FabButtonActionListener) context;

    }

    @Override
    public void onEventEdit(String eventId) {
         Event event=new Event(eventId,"Sylhet Tour","Asad gate","Sylhet","24/12/2018","6000");
         eventReference.child(eventId).setValue(event);
    }

    @Override
    public void onEventDelete(String eventId) {
        eventReference.child(eventId).removeValue();
    }

    @Override
    public void onClickRecyclerViewItem(Event event) {

        fabButtonActionListener.onItemClick(event);
    }

    public interface FabButtonActionListener{
        void onFabButtonClick();
        void onLogoutButtonClick();
        void onItemClick(Event event);
    }

}

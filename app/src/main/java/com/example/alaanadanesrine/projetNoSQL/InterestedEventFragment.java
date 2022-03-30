package com.example.alaanadanesrine.projetNoSQL;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alaanadanesrine.projetNoSQL.Events.DatabaseHelper;
import com.example.alaanadanesrine.projetNoSQL.Events.Event;
import com.example.alaanadanesrine.projetNoSQL.Events.EventAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterestedEventFragment extends Fragment {

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Event> eventList;
    private RecyclerView.Adapter adapter;
    private DatabaseHelper db;

    public InterestedEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View view = inflater.inflate(R.layout.fragment_interested_event, container, false);

        mList = view.findViewById(R.id.main_list);
        eventList = new ArrayList<>();
        db = new DatabaseHelper(getContext());
        eventList=db.getAllEvents();
        adapter = new EventAdapter(this.getContext(),eventList);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        return view;
    }

}

package com.example.alaanadanesrine.projetNoSQL;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClubFragment extends Fragment {
    private String str_ip;

    private String url;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Clubs> clubList;
    private RecyclerView.Adapter adapter;
    public LinearLayout linearLayout;


    SwipeRefreshLayout swipeRefreshLayout;


    public ClubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        str_ip = getActivity().getResources().getString(R.string.ip_php);
        url = "http://"+str_ip+"/min/getClub.php";
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View view = inflater.inflate(R.layout.fragment_club, container, false);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        linearLayout=view.findViewById((R.id.liniarlayout));


        mList = view.findViewById(R.id.main_list);

       clubList = new ArrayList<>();
        adapter = new ClubsAdapter(this.getContext(),clubList);

        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshItems();
            }
        });


        return view;
    }


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for (int i = response.length(); i > -1; i--) {
                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
                    try {
                        jsonObject = response.getJSONObject(i);
                        Clubs club = new Clubs();

                        club.setClubname(jsonObject.getString("club_name"));
                        club.setCreationdate(jsonObject.getString("club_creation"));
                        club.setDescription(jsonObject.getString("club_description"));
                        String str = jsonObject.getString("club_description");
                        if(str.length()>30)
                            str=str.substring(0,25)+"...";
                        club.setDescription(str);
                        clubList.add(club);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }



    void refreshItems() {
        // Load items
        // ...
        //Toast.makeText(getContext(),"Work",Toast.LENGTH_LONG).show();
        mList = getView().findViewById(R.id.main_list);

        clubList = new ArrayList<>();
        adapter = new ClubsAdapter(getContext(),clubList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        getData();
        if(clubList.size()==1)
            mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        onItemsLoadComplete();
    }


    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }





}

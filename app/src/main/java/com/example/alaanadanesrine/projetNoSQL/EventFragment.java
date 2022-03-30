package com.example.alaanadanesrine.projetNoSQL;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.alaanadanesrine.projetNoSQL.Events.Event;
import com.example.alaanadanesrine.projetNoSQL.Events.EventAdapter;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventFragment extends Fragment {
    private String str_ip;

    private String url;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Event> eventList;
    private RecyclerView.Adapter adapter;


    SwipeRefreshLayout swipeRefreshLayout;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        str_ip = getActivity().getResources().getString(R.string.ip_php);
        url = "http://"+str_ip+"/min/getEvent.php";
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);

        mList = view.findViewById(R.id.main_list);

        eventList = new ArrayList<>();
        adapter = new EventAdapter(this.getContext(),eventList);

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
                // Refresh items
                refreshItems();
            }
        });

        //swipeRefreshLayout.setEnabled(true);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mList = getView().findViewById(R.id.main_list);

                eventList = new ArrayList<>();
                adapter = new EventAdapter(getContext(),eventList);

                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
                mList.setHasFixedSize(true);
                mList.setLayoutManager(linearLayoutManager);
                getData(query);
                if(eventList.size()==1)
                    mList.addItemDecoration(dividerItemDecoration);
                mList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mList = getView().findViewById(R.id.main_list);

                eventList = new ArrayList<>();
                adapter = new EventAdapter(getContext(),eventList);

                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
                mList.setHasFixedSize(true);
                mList.setLayoutManager(linearLayoutManager);
                getData(query);
                if(eventList.size()==1)
                    mList.addItemDecoration(dividerItemDecoration);
                mList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        Event event = new Event();

                        event.setEventName(jsonObject.getString("name"));
                        event.setBeginDate(jsonObject.getString("begin_date"));
                        event.setEndDate(jsonObject.getString("end_date"));
                        event.setEventPlace(jsonObject.getString("place"));
                        String str = jsonObject.getString("description");
                        if(str.length()>30)
                            str=str.substring(0,25)+"...";
                        event.setEventDescription(str);
                        eventList.add(event);
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

    private void getData(final String titre) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for (int i = response.length(); i > -1; i--) {
                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
                    try {
                        jsonObject = response.getJSONObject(i);
                        int longSubEventName = titre.length();
                        if((jsonObject.getString("name").length())>=longSubEventName){
                        String subEventName = jsonObject.getString("name").substring(0,longSubEventName);
                        if(titre.equals(subEventName)){
                            Event event = new Event();

                            event.setEventName(jsonObject.getString("name"));
                            event.setBeginDate(jsonObject.getString("begin_date"));
                            event.setEndDate(jsonObject.getString("end_date"));
                            event.setEventPlace(jsonObject.getString("place"));
                            String str = jsonObject.getString("description");
                            if(str.length()>30)
                                str=str.substring(0,25)+"...";
                            event.setEventDescription(str);
                            eventList.add(event);}}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
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

        eventList = new ArrayList<>();
        adapter = new EventAdapter(getContext(),eventList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        getData();
        if(eventList.size()==1)
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

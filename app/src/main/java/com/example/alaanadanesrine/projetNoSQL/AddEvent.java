package com.example.alaanadanesrine.projetNoSQL;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alaanadanesrine.projetNoSQL.register_login.InputValidation;
import com.example.alaanadanesrine.projetNoSQL.register_login.Register_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddEvent extends Fragment implements View.OnClickListener {


    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEventNameJ;
    private TextInputLayout textInputLayoutBeginDateJ;
    private TextInputLayout textInputLayoutEndDateJ;
    private TextInputLayout textInputLayoutPlaceJ;
    private TextInputLayout textInputLayoutDescriptionJ;

    private TextInputEditText textInputEditTextEventNameJ;
    private TextInputEditText textInputEditTextBeginDateJ;
    private TextInputEditText textInputEditTextEndDateJ;
    private TextInputEditText textInputEditTextPlaceJ;
    private TextInputEditText textInputEditTextDescriptionJ;

    private AppCompatButton appCompatButtonAdd;

    private InputValidation inputValidation;


    public AddEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view =inflater.inflate(R.layout.fragment_add_event, container, false);
        initViews(view);
        initListeners();
        initObjects();
        return view;
    }

    private void initViews(View view) {
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        textInputLayoutEventNameJ =  view.findViewById(R.id.textInputLayoutEventName);
        textInputLayoutBeginDateJ =  view.findViewById(R.id.textInputLayoutBeginDate);
        textInputLayoutEndDateJ =  view.findViewById(R.id.textInputLayoutEndDate);
        textInputLayoutPlaceJ =  view.findViewById(R.id.textInputLayoutPlace);
        textInputLayoutDescriptionJ =  view.findViewById(R.id.textInputLayoutDescription);

        textInputEditTextEventNameJ = view.findViewById(R.id.textInputEditTextEventName);
        textInputEditTextBeginDateJ =  view.findViewById(R.id.textInputEditTextBeginDate);
        textInputEditTextEndDateJ =  view.findViewById(R.id.textInputEditTextEndDate);
        textInputEditTextPlaceJ =  view.findViewById(R.id.textInputEditTextPlace);
        textInputEditTextDescriptionJ =  view.findViewById(R.id.textInputEditTextDescription);

        appCompatButtonAdd =  view.findViewById(R.id.appCompatButtonAdd);

    }

    private void initListeners(){
        appCompatButtonAdd.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(getActivity().getBaseContext());
    }


    @Override
    public void onClick(View v) {
        postData();
    }

    private void postData() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEventNameJ, textInputLayoutEventNameJ, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextBeginDateJ, textInputLayoutBeginDateJ, "Empty Begin Date")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEndDateJ, textInputLayoutEndDateJ, "Empty End Date")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPlaceJ, textInputLayoutPlaceJ, "Empty Place")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDescriptionJ, textInputLayoutDescriptionJ, "Empty Description")) {
            return;
        }

        String str_ip = getResources().getString(R.string.ip_php);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://"+str_ip+"/min/addEvent.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject =new JSONObject(response);
                    String succes=jsonobject.getString("success");
                    if (succes.equals("1")){
                        //Toast.makeText(getContext(), "Register Success", Toast.LENGTH_LONG).show();
                        Fragment fragmentSelected=new EventFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragmentSelected).commit();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",textInputEditTextEventNameJ.getText().toString().trim());
                params.put("begin_date",textInputEditTextBeginDateJ.getText().toString().trim());
                params.put("end_date",textInputEditTextEndDateJ.getText().toString().trim());
                params.put("place",textInputEditTextPlaceJ.getText().toString().trim());
                params.put("description",textInputEditTextDescriptionJ.getText().toString().trim());
                return params;
            }

        };
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

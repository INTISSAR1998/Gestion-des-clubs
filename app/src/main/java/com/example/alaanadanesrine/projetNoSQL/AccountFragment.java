package com.example.alaanadanesrine.projetNoSQL;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alaanadanesrine.projetNoSQL.Events.Event;
import com.example.alaanadanesrine.projetNoSQL.register_login.InputValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private String str_ip;
    private String url,url_update;

    SessionManager session;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutCurrentPassword;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextCurrentPassword;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonSave;
    private AppCompatButton appCompatButtonDisconnect;

    private InputValidation inputValidation;

    String email;
    String password;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        str_ip = getActivity().getResources().getString(R.string.ip_php);
        url = "http://"+str_ip+"/min/getInfo.php";
        url_update = "http://"+str_ip+"/min/updateEtudiant.php";
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        View view = inflater.inflate(R.layout.fragment_account, container, false);

        session=new SessionManager(this.getActivity());
        HashMap<String, String> user = session.getUserDetails();

        password = user.get(SessionManager.KEY_PASSWORD);

        email = user.get(SessionManager.KEY_EMAIL);


        //Toast.makeText(getContext(), email+" "+password,Toast.LENGTH_LONG).show();
        initViews(view);
        initListeners();
        initObjects();
        setInputEditText();


        return view;
    }


    private void initViews(View view) {
        textInputLayoutName=view.findViewById(R.id.textInputLayoutNameA);
        textInputLayoutEmail=view.findViewById(R.id.textInputLayoutEmailA);
        textInputLayoutCurrentPassword=view.findViewById(R.id.textInputLayoutCurrentPasswordA);
        textInputLayoutPassword=view.findViewById(R.id.textInputLayoutPasswordA);
        textInputLayoutConfirmPassword=view.findViewById(R.id.textInputLayoutConfirmPasswordA);

        textInputEditTextName=view.findViewById(R.id.textInputEditTextNameA);
        textInputEditTextEmail=view.findViewById(R.id.textInputEditTextEmailA);
        textInputEditTextCurrentPassword=view.findViewById(R.id.textInputEditTextCurrentPasswordA);
        textInputEditTextPassword=view.findViewById(R.id.textInputEditTextPasswordA);
        textInputEditTextConfirmPassword=view.findViewById(R.id.textInputEditTextConfirmPasswordA);

        appCompatButtonSave=view.findViewById(R.id.appCompatButtonSave);
        appCompatButtonDisconnect=view.findViewById(R.id.appCompatButtondisconnect);
    }

    private void initObjects(){
        inputValidation = new InputValidation(getActivity().getBaseContext());
    }

    private void initListeners(){
        appCompatButtonDisconnect.setOnClickListener(this);
        appCompatButtonSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.appCompatButtondisconnect : disconnect();break;
            case R.id.appCompatButtonSave:save();break;
        }
    }

    private void save() {
        String testpass=textInputEditTextCurrentPassword.getText().toString();
        if (!password.equals(testpass)) {
            textInputLayoutCurrentPassword.setError("Enter Current Password");
            inputValidation.hideKeyboardFrom(textInputEditTextCurrentPassword);
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password)))
            return;
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_match)))
            return;
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name)))
            return;
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email)))
            return;


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonobject =new JSONObject(response);
                                String succes=jsonobject.getString("succes");
                                if (succes.equals("2")){
                                    textInputLayoutEmail.setError("Email Already Exist");
                                }
                                if (succes.equals("1")){
                                    session.createLoginSession(textInputEditTextPassword.getText().toString().trim(),textInputEditTextEmail.getText().toString().trim());
                                    HashMap<String, String> user = session.getUserDetails();

                                    password = user.get(SessionManager.KEY_PASSWORD);

                                    email = user.get(SessionManager.KEY_EMAIL);
                                    //Toast.makeText(getContext(), email+" "+password, Toast.LENGTH_LONG).show();
                                    //Snackbar.make(nestedScrollView, "Register Success", Snackbar.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("login", textInputEditTextName.getText().toString());
                            parameters.put("email", email);
                            parameters.put("pass", textInputEditTextPassword.getText().toString());
                            parameters.put("new_email", textInputEditTextEmail.getText().toString());
                            return parameters;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
    }

    private void disconnect() {
        session.logoutUser();
    }

    private void setInputEditText(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //JSONArray jsonarray = jsonobject.getJSONArray("info");
                    //JSONObject data = jsonarray.getJSONObject(0);
                    String emailJ = jsonObject.getString("Email");
                    String login = jsonObject.getString("login");
                    textInputEditTextName.setText(login, TextView.BufferType.EDITABLE);
                    textInputEditTextEmail.setText(emailJ, TextView.BufferType.EDITABLE);
                    //Toast.makeText(getContext(), login+email,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("email", email);
                parameters.put("pass", password);
                return parameters;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

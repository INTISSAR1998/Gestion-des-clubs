package com.example.alaanadanesrine.projetNoSQL;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView BottomNav=findViewById(R.id.NavBottom);
        BottomNav.setOnNavigationItemSelectedListener(navListenner);
        Fragment fragmentSelected=new EventFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragmentSelected).commit();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListenner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragmentSelected;
            switch (item.getItemId()){
                case R.id.events: fragmentSelected=new EventFragment();break;
                case R.id.add: fragmentSelected= new AddEvent();break;
                case R.id.account: fragmentSelected= new AccountFragment();break;
                case R.id.clubs : fragmentSelected=new ClubFragment();break;
                case R.id.interest : fragmentSelected= new InterestedEventFragment();break;
                default : /*Toast.makeText(MainActivity2.this, "On cour de Construction", Toast.LENGTH_SHORT).show();*/fragmentSelected=new EventFragment();break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragmentSelected).commit();
            return true;
        }
    };
}
package com.roledojorgeapp.roledojorgeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.widget.ImageButton;

import com.roledojorgeapp.roledojorgeapp.placeholder.filterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment listEvent = new eventListFragment();
        Fragment filterEvent = new filterFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragmentEventList,listEvent);
        transaction.commit();


        final Button listButton = findViewById(R.id.listViewOn);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragmentEventList,listEvent);
                transaction.commit();
            }
        });


        final Button filterViewOn = findViewById(R.id.filterViewOn);
        filterViewOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragmentEventList,filterEvent);
                transaction.commit();
            }
        });





    }

    public void clickToOpenButton (View v){
        Intent clickToOpen = new Intent(this,MainActivity2.class);
        startActivity(clickToOpen);
    }
}